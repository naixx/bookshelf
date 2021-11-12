package com.github.naixx.bookshelf.network

import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.JsonClass
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@JsonClass(generateAdapter = true)
data class AuthBody(
    var username: String,
    var password: String
)

@JsonClass(generateAdapter = true)
data class User(
    val id: String,
    val username: String,
    val token: String
)

@JsonClass(generateAdapter = true)
data class Book(
    val title: String,
    val author: String,
    val coverImageUrl: String,
    val id: String,
    val pageCount: Long,
    val publisher: String,
    val synopsis: String,
)

interface AuthApi {

    @POST("auth/register")
    @Wrapped(path = ["user"])
    fun register(@Body body: AuthBody): Observable<User>

    @POST("auth/login")
    @Wrapped(path = ["user"])
    fun login(@Body body: AuthBody): Observable<User>
}

interface BookshelfApi {
    @GET("books")
    @Wrapped(path = ["books"])
    fun books(@Query("q") q: String?): Observable<List<Book>>
}
