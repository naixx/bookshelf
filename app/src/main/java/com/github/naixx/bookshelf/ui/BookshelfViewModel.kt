package com.github.naixx.bookshelf.ui

import androidx.lifecycle.ViewModel
import com.github.naixx.bookshelf.app.AuthManager
import com.github.naixx.bookshelf.network.AuthApi
import com.github.naixx.bookshelf.network.AuthBody
import com.github.naixx.bookshelf.network.BookshelfApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookshelfViewModel @Inject constructor(
    private val api: AuthApi,
    private val bookshelfApi: BookshelfApi,
    private val authManager: AuthManager
) : ViewModel() {

    fun login(body: AuthBody) = api.login(body).doOnNext {
        authManager.token = it.token
    }

    fun register(body: AuthBody) = api.register(body).doOnNext {
        authManager.token = it.token
    }

    fun books(q:String? = null) = bookshelfApi.books(q)
}
