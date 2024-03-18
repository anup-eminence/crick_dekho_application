package com.example.cricdekho.app

import android.app.Application

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(this)
    }
}