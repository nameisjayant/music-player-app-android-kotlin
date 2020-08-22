package com.codingwithjks.musicplayer.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "music")
data class Music(
    val songName:String?,
    val artistName: String?,
    val time:String?,
    val url:String? ) : Serializable{

    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}


