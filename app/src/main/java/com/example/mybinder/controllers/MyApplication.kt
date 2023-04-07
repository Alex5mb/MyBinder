package com.example.mybinder.controllers

import android.app.Application

class MyApplication : Application() {
    companion object {
        lateinit var dbHelper: DatabaseHelper
    }

    override fun onCreate() {
        super.onCreate()
        dbHelper = DatabaseHelper(applicationContext)
    }
}