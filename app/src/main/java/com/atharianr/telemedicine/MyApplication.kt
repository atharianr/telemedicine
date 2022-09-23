package com.atharianr.telemedicine

import android.app.Application
import com.atharianr.telemedicine.di.networkModule
import com.atharianr.telemedicine.di.remoteDataSourceModule
import com.atharianr.telemedicine.di.serviceModule
import com.atharianr.telemedicine.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    remoteDataSourceModule,
                    serviceModule,
                    viewModelModule
                )
            )
        }
    }
}