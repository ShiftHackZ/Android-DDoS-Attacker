package com.shifthackz.android.attacker.attack.factory

import com.shifthackz.android.attacker.attack.http_flood.HttpFloodAttack
import com.shifthackz.android.attacker.attack.contract.Attack
import com.shifthackz.android.attacker.attack.contract.AttackParam
import com.shifthackz.android.attacker.attack.contract.AttackType
import com.shifthackz.android.attacker.attack.tcp_flood.TcpSynFloodAttack
import com.shifthackz.android.attacker.attack.udp_flood.UdpFloodAttack

class AttackerFactory {

    fun get(
        type: AttackType,
        params: Map<AttackParam, Any>
    ): Attack = when (type) {
        AttackType.HTTP_FLOOD -> HttpFloodAttack(params)
        AttackType.TCP_FLOOD -> TcpSynFloodAttack(params)
        AttackType.UDP_FLOOD -> UdpFloodAttack(params)
    }
}