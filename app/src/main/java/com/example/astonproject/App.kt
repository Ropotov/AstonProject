package com.example.astonproject

import android.app.Application
import com.example.astonproject.di.DaggerAppComponent

class App : Application(){

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}