package com.shifthackz.android.attacker.attack.coordinator

import com.shifthackz.android.attacker.attack.contract.AttackProtocol
import com.shifthackz.android.attacker.attack.contract.AttackType
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

interface AttackCoordinator {
    fun start()
    fun stop()

    fun setTarget(target: String)
    fun setPort(port: String)
    fun setType(type: AttackType)
    fun setThreadsCount(count: Int)
    fun setProtocol(protocol: AttackProtocol)
    fun setConnectTimeout(timeout: Long)
    fun setFloodMessage(message: String)

    fun attack(): Observable<Int>
    fun observeState(): Observable<Boolean>
}