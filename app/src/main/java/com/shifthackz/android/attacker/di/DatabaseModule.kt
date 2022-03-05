package com.shifthackz.android.attacker.di

import androidx.room.Room
import com.shifthackz.android.attacker.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "d")
            .fallbackToDestructiveMigration()
            .build()
    }

    factory { get<AppDatabase>().logDao() }
}