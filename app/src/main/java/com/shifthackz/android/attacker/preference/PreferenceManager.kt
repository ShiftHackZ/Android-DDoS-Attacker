package com.shifthackz.android.attacker.preference

interface PreferenceManager {
    var target: String
    var port: String
    var threads: String
    var attack: String
    var protocol: String
    var timeout: Long
    var floodMessage: String
}