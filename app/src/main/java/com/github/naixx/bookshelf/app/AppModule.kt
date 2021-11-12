package com.github.naixx.bookshelf.app

import android.content.Context
import com.github.naixx.bookshelf.BuildConfig
import com.github.naixx.bookshelf.network.AuthApi
import com.github.naixx.bookshelf.network.AuthInterceptor
import com.github.naixx.bookshelf.network.BookshelfApi
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Auth

@Module
@InstallIn(SingletonComponent::class)
class AppModule() {

    private val CACHE_SIZE = (50 * 1024 * 1024).toLong() // 50 MB

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    @Auth
    internal fun provideOkhttp(cache: Cache, authInterceptor: AuthInterceptor): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {

            builder.addInterceptor(
                HttpLoggingInterceptor(/*PrettyPrintLogger()*/).setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
        }
        builder.cache(cache)
        return builder
    }

    @Provides
    @Singleton
    fun provideCache(context: Context): Cache {
        return Cache(context.cacheDir, CACHE_SIZE)
    }

    @Provides
    @Singleton
    @Auth
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("http://104.248.26.141:3000/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(Wrapped.ADAPTER_FACTORY).build()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
    }

    @Provides
    @Singleton
    @Auth
    fun provideAuthRetrofit(@Auth okHttpClient: OkHttpClient.Builder, @Auth b: Retrofit.Builder): Retrofit =
        b.client(okHttpClient.build()).build()

    @Provides
    @Singleton
    fun provideRetrofit(@Auth okHttpClient: OkHttpClient.Builder, @Auth b: Retrofit.Builder, authInterceptor: AuthInterceptor): Retrofit =
        b.client(okHttpClient.addInterceptor(authInterceptor).build()).build()

    @Provides
    @Singleton
    fun provideApi(@Auth retrofit: Retrofit) = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideBookshelfApi(retrofit: Retrofit) = retrofit.create(BookshelfApi::class.java)
}
