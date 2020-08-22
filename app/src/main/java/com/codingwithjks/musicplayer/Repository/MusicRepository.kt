package com.codingwithjks.musicplayer.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.codingwithjks.musicplayer.Database.MusicDatabase
import com.codingwithjks.musicplayer.Model.Music
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MusicRepository {

    companion object{
        private var musicDatabase:MusicDatabase?=null

        private fun initDB(context: Context):MusicDatabase?= MusicDatabase.getInstance(context)

        fun insert(context: Context,musicList: ArrayList<Music>)
        {
            musicDatabase= initDB(context)
            CoroutineScope(IO).launch {
                musicDatabase?.getMusicDao()?.insert(musicList)
            }
        }

        fun getAllMusic(context: Context):LiveData<List<Music>>?
        {
         musicDatabase= initDB(context)
            return musicDatabase?.getMusicDao()?.getAllMusic()
        }

    }
}