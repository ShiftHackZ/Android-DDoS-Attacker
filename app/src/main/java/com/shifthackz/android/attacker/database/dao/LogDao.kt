package com.shifthackz.android.attacker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shifthackz.android.attacker.database.entity.LogEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface LogDao {

    @Query("SELECT * FROM logs")
    fun observe(): Flowable<List<LogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LogEntity)

    @Query("DELETE FROM logs")
    fun clear(): Completable
}