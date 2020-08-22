package com.codingwithjks.musicplayer.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codingwithjks.musicplayer.Dao.MusicDao
import com.codingwithjks.musicplayer.Model.Music

@Database(entities = [Music::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun getMusicDao():MusicDao

    companion object{
        private const val DATABASE_NAME="music"
        @Volatile
        private var instance:MusicDatabase?=null

        fun getInstance(context: Context):MusicDatabase?{
            if(instance == null)
            {
                synchronized(MusicDatabase::class.java)
                {
                    if(instance == null)
                    {
                        instance=Room.databaseBuilder(context,MusicDatabase::class.java,
                        DATABASE_NAME)
                            .build()
                    }
                }
            }

            return instance
        }
    }
}