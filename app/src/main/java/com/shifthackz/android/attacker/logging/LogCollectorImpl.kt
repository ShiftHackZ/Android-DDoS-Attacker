package com.shifthackz.android.attacker.logging

import com.shifthackz.android.attacker.R
import com.shifthackz.android.attacker.database.dao.LogDao
import com.shifthackz.android.attacker.database.entity.LogEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.Executors
import kotlin.math.abs

class LogCollectorImpl(private val dao: LogDao) : LogCollector {

    private val executor = Executors.newSingleThreadExecutor()

    private val packetCountSubject: PublishSubject<Int> = PublishSubject.create()

    private val ppsSubject: PublishSubject<Int> = PublishSubject.create()

    private var ppsTimestamp = 0L

    private var ppsCount: Int = 0

    private var packetTimestamp = 0L

    private var packetCount: Int = 0

    override fun observeLog(): Flowable<List<LogEntity>> = dao.observe()

    override fun observePackets(): Observable<Int> = packetCountSubject

    override fun observePps(): Observable<Int> = ppsSubject

    override fun collectPacketMessage(message: String) {
        updatePpsPacketsCounter()
        packetCount++
        ppsCount++
    }

    override fun collectMessage(message: String) {
        updatePpsPacketsCounter()
        postLogEntry(LogEntity(System.currentTimeMillis(), message, R.color.colorHint))
    }

    override fun collectError(t: Throwable) {
        updatePpsPacketsCounter()
        postLogEntry(LogEntity(System.currentTimeMillis(), t.message.toString(), R.color.colorLogError))
    }

    override fun reset(): Completable = dao.clear()
        .doOnComplete { packetCount = 0 }

    private fun postLogEntry(logEntity: LogEntity) {
        runCatching {
            dao.insert(logEntity)
        }.onFailure {
            executor.execute { dao.insert(logEntity) }
        }
    }

    private fun updatePpsPacketsCounter() {
        if (ppsTimestamp == 0L) ppsTimestamp = System.currentTimeMillis()
        if (packetTimestamp == 0L) packetTimestamp = System.currentTimeMillis()
        val timestamp = System.currentTimeMillis()
        val diffPps = abs(timestamp - ppsTimestamp)
        if (diffPps > 1000L) {
            ppsSubject.onNext(ppsCount)
            postLogEntry(LogEntity(System.currentTimeMillis(), "Sent: $packetCount, Pps: $ppsCount", R.color.colorLogMessage))
            ppsCount = 0
            ppsTimestamp = System.currentTimeMillis()
        }
        val diffPacket = abs(timestamp - packetTimestamp)
        if (diffPacket > 100L) {
            packetCountSubject.onNext(packetCount)
            packetTimestamp = System.currentTimeMillis()
        }
    }
}