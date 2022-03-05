package com.shifthackz.android.attacker.di

import com.shifthackz.android.attacker.presentation.activity.MainViewModel
import com.shifthackz.android.attacker.presentation.fragment.ddos.DdosViewModel
import com.shifthackz.android.attacker.presentation.fragment.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { DdosViewModel(get(), get(), get(), get()) }
}