package com.shifthackz.android.attacker.attack.contract

enum class AttackProtocol(val protocol: String) {
    HTTP("http"),
    HTTPS("https");

    companion object {
        fun byName(name: String): AttackProtocol = values().findLast { it.protocol == name } ?: HTTP
        fun byIndex(index: Int): AttackProtocol = values().getOrElse(index) { HTTP }
    }
}