package com.shifthackz.android.attacker.di

import com.shifthackz.android.attacker.attack.coordinator.AttackCoordinator
import com.shifthackz.android.attacker.attack.coordinator.AttackCoordinatorImpl
import com.shifthackz.android.attacker.attack.factory.AttackerFactory
import org.koin.dsl.module

val attackModule = module {
    factory { AttackerFactory() }

    single<AttackCoordinator> { AttackCoordinatorImpl(get(), get()) }
}