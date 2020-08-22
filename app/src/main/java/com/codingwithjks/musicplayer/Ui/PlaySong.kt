package com.codingwithjks.musicplayer.Ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import com.codingwithjks.musicplayer.Model.Music
import com.codingwithjks.musicplayer.R
import kotlinx.android.synthetic.main.activity_play_song.*
import java.lang.Exception

class PlaySong : AppCompatActivity(),View.OnClickListener {
    private lateinit var getMusic:Music
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_song)
        handler= Handler()
        mediaPlayer= MediaPlayer()
        getSerializableData()
        loadData()
        initSeekBar()
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser)
                {
                    mediaPlayer.seekTo(progress*1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    private fun initSeekBar() {
        seekbar.max=mediaPlayer.seconds
        runnable= Runnable {
            seekbar.progress = mediaPlayer.currentSeconds
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
    }

    // Extension property to get media player duration in seconds
    private val MediaPlayer.seconds:Int
        get() {
            return this.duration / 1000
        }


    // Extension property to get media player current position in seconds
    private val MediaPlayer.currentSeconds:Int
        get() {
            return this.currentPosition/1000
        }

    private fun loadData() {
        songName1.text=getMusic.songName
        artistName1.text=getMusic.artistName
       try {
           mediaPlayer.setDataSource(getMusic.url)
           mediaPlayer.prepare()
           mediaPlayer.start()
       }
       catch (e:Exception)
       {
           e.printStackTrace()
       }
       previous.setOnClickListener(this)
       next.setOnClickListener(this)
       play.setOnClickListener(this)
    }



    private fun getSerializableData() {

        val bundle:Bundle?=intent.extras
        getMusic= bundle?.getSerializable("song") as Music
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
           R.id.previous->{}
           R.id.play->{
               if(mediaPlayer.isPlaying)
               {
                   play.setImageResource(R.drawable.play)
                   mediaPlayer.pause()
                   handler.removeCallbacks(runnable)

               }
               else{
                   initSeekBar()
                   play.setImageResource(R.drawable.pause)
                   mediaPlayer.start()
               }
           }
           R.id.next->{}

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

}