package com.github.naixx.bookshelf.network

import com.github.naixx.bookshelf.app.AuthManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val authManager: AuthManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .header("Authorization", "Bearer ${authManager.token}")
                .build()
        )
    }
}
