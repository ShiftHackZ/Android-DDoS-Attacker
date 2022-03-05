package com.shifthackz.android.attacker.di

import com.shifthackz.android.attacker.logging.LogCollector
import com.shifthackz.android.attacker.logging.LogCollectorImpl
import com.shifthackz.android.attacker.validator.ParamsValidator
import com.shifthackz.android.attacker.validator.Validation
import org.koin.dsl.module

val statisticsModule = module {
    single<LogCollector> { LogCollectorImpl(get()) }
    factory<Validation> { ParamsValidator() }
}