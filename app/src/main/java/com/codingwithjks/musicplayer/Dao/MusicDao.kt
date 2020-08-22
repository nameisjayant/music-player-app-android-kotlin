package com.codingwithjks.musicplayer.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingwithjks.musicplayer.Model.Music

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(musicList:ArrayList<Music>)

    @Query("SELECT * FROM music ORDER BY id ASC")
    fun getAllMusic():LiveData<List<Music>>

}
