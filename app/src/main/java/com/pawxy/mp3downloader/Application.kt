package com.pawxy.mp3downloader

import android.app.Application
import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import dagger.hilt.android.HiltAndroidApp

lateinit var application: Application

val py by lazy {
    Python.start(AndroidPlatform(application))
    Python.getInstance()
}

fun mainModule() = py.getModule("script")

@HiltAndroidApp
class Application : Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this
    }
}