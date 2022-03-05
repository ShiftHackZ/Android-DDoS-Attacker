package com.shifthackz.android.attacker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shifthackz.android.attacker.database.dao.LogDao
import com.shifthackz.android.attacker.database.entity.LogEntity

@Database(
    entities = [LogEntity::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun logDao(): LogDao
}