package com.grigor.nearme

import android.app.Application
import com.grigor.nearme.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(getKoinModules())
        }
    }
    private fun getKoinModules(): List<Module> {
        return listOf(appModule)
    }
}