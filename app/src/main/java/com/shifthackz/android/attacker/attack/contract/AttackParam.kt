package com.shifthackz.android.attacker.attack.contract

enum class AttackParam(val key: String) {
    TARGET("target"),
    PORT("port"),
    PROTOCOL("protocol"),
    TIMEOUT("timeout"),
    FLOOD_MSG("flood_msg"),
    THREADS("threads"),
}