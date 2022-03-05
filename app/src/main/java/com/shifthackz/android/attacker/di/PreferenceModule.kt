package com.shifthackz.android.attacker.di

import com.shifthackz.android.attacker.preference.CryptoPreferenceProvider
import com.shifthackz.android.attacker.preference.PreferenceManager
import com.shifthackz.android.attacker.preference.PreferenceManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
    single<PreferenceManager> {
        val prefs = CryptoPreferenceProvider.getPrefs(androidContext())
        PreferenceManagerImpl(prefs)
    }
}