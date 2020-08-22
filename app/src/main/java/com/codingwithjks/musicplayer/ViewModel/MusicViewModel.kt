package com.codingwithjks.musicplayer.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingwithjks.musicplayer.Model.Music
import com.codingwithjks.musicplayer.Repository.MusicRepository

class MusicViewModel : ViewModel(){

    fun insert(context: Context,musicList: ArrayList<Music>)
    {
        MusicRepository.insert(context,musicList)
    }

    fun getAllMusic(context: Context):LiveData<List<Music>>?= MusicRepository.getAllMusic(context)

}