package com.shifthackz.android.attacker.preference

import android.content.SharedPreferences
import com.shifthackz.android.attacker.attack.contract.AttackType

class PreferenceManagerImpl(
    private val preferences: SharedPreferences
) : PreferenceManager {

    override var target: String
        get() = preferences.getString(KEY_TARGET, "") ?: ""
        set(value) = preferences.edit().putString(KEY_TARGET, value).apply()

    override var port: String
        get() = preferences.getString(KEY_PORT, Defaults.PORT) ?: Defaults.PORT
        set(value) = preferences.edit().putString(KEY_PORT, value).apply()

    override var threads: String
        get() = preferences.getString(KEY_THREADS, Defaults.THREADS) ?: Defaults.THREADS
        set(value) = preferences.edit().putString(KEY_THREADS, value).apply()

    override var attack: String
        get() = preferences.getString(KEY_ATTACK, AttackType.HTTP_FLOOD.attackName) ?: AttackType.HTTP_FLOOD.attackName
        set(value) = preferences.edit().putString(KEY_ATTACK, value).apply()

    override var protocol: String
        get() = preferences.getString(KEY_PROTOCOL, "") ?: ""
        set(value) = preferences.edit().putString(KEY_PROTOCOL, value).apply()

    override var timeout: Long
        get() = preferences.getLong(KEY_TIMEOUT, Defaults.TIMEOUT)
        set(value) = preferences.edit().putLong(KEY_TIMEOUT, value).apply()

    override var floodMessage: String
        get() = preferences.getString(KEY_FLOOD_MSG, Defaults.FLOOD_MESSAGE) ?: Defaults.FLOOD_MESSAGE
        set(value) = preferences.edit().putString(KEY_FLOOD_MSG, value).apply()

    companion object {
        private const val KEY_TARGET = "key_target"
        private const val KEY_PORT = "key_port"
        private const val KEY_THREADS = "key_threads"
        private const val KEY_ATTACK = "key_attack"
        private const val KEY_PROTOCOL = "key_protocol"
        private const val KEY_TIMEOUT = "key_timeout"
        private const val KEY_FLOOD_MSG = "key_flood_message"
    }
}