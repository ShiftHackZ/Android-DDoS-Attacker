package com.shifthackz.android.attacker

import android.app.Application
import com.shifthackz.android.attacker.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                databaseModule,
                statisticsModule,
                preferenceModule,
                attackModule,
                viewModelModule
            )
        }
    }
}