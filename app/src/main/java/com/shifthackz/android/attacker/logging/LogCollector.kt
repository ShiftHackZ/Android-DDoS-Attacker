package com.shifthackz.android.attacker.logging

import com.shifthackz.android.attacker.database.entity.LogEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

interface LogCollector {
    fun observeLog(): Flowable<List<LogEntity>>
    fun observePackets(): Observable<Int>
    fun observePps(): Observable<Int>
    fun collectPacketMessage(message: String = "")
    fun collectMessage(message: String = "")
    fun collectError(t: Throwable)
    fun reset(): Completable
}