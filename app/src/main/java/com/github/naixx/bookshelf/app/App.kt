package com.github.naixx.bookshelf.app

import android.app.Application
import android.util.Log
import common.errorLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        errorLogger = { throwable -> Log.e("error", throwable?.message, throwable) }
    }
}
