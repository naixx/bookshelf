package com.github.naixx.bookshelf.app

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(private val c: Context) {
    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(c) }

    var token: String?
        get() = prefs.getString("token", null)
        set(value) {
            prefs.edit {
                putString("token", value)
            }
        }

    val isLogged
        get() = prefs.getString("token", null)?.isNotBlank() ?: false
}
