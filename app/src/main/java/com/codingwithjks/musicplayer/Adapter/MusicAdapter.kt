package com.codingwithjks.musicplayer.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codingwithjks.musicplayer.Listener.Listener
import com.codingwithjks.musicplayer.Model.Music
import com.codingwithjks.musicplayer.R

class MusicAdapter(private val context: Context,private var musicList:ArrayList<Music>,private val listener:Listener) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder =
        MusicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.each_row,parent,false))

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music=musicList[position]
        holder.songName.text=music.songName
        holder.artistName.text=music.artistName
        holder.time.text=music.time
    }

    override fun getItemCount(): Int =musicList.size

  inner class MusicViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)
 {
       val songName:TextView=itemView.findViewById(R.id.songName)
       val artistName:TextView=itemView.findViewById(R.id.artistName)
       val time:TextView=itemView.findViewById(R.id.time)
       init {
           itemView.setOnClickListener {
               listener.onCardClickListener(adapterPosition)
           }
       }
 }

    fun setMusicData(musicList: ArrayList<Music>)
    {
        this.musicList=musicList
        notifyDataSetChanged()
    }
}