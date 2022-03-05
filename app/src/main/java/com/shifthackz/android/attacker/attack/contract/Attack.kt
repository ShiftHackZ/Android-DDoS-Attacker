package com.shifthackz.android.attacker.attack.contract

abstract class Attack {
    abstract val type: AttackType
    abstract val runner: () -> Unit
}