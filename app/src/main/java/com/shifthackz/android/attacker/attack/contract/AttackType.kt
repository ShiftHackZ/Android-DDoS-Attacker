package com.shifthackz.android.attacker.attack.contract

enum class AttackType(val attackName: String) {
    HTTP_FLOOD("HTTP Flood"),
    TCP_FLOOD("TCP SYN Flood"),
    UDP_FLOOD("UDP Flood");

    companion object {
        fun byName(name: String): AttackType = values().findLast { it.attackName == name } ?: HTTP_FLOOD
        fun byIndex(index: Int): AttackType = values().getOrElse(index) { HTTP_FLOOD }
    }
}