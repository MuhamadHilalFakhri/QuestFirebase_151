package com.example.firebasefirestore

import android.app.Application
import com.example.firebasefirestore.di.AppContainer
import com.example.firebasefirestore.di.MahasiswaContainer

class MahasiswaApplications: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}