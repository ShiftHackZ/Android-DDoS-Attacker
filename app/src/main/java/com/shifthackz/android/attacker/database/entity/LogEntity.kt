package com.shifthackz.android.attacker.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs")
data class LogEntity(
    @PrimaryKey(autoGenerate = false)
    val timestamp: Long,
    val message: String,
    val color: Int
)
