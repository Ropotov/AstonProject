package com.example.astonproject.app

import android.app.Application
import com.example.astonproject.app.di.DaggerAppComponent

class App : Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}