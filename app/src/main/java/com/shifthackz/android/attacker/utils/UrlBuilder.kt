package com.shifthackz.android.attacker.utils

import com.shifthackz.android.attacker.attack.contract.AttackParam

class UrlBuilder(private val params: Map<AttackParam, Any>) {

    fun getUrl(): String {
        val protocol = params[AttackParam.PROTOCOL]
        val target = params[AttackParam.TARGET]
        val port = params[AttackParam.PORT]
        return "$protocol://$target:$port/"
    }
}