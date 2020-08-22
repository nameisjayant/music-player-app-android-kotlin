package com.codingwithjks.musicplayer.Ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingwithjks.musicplayer.Adapter.MusicAdapter
import com.codingwithjks.musicplayer.Listener.Listener
import com.codingwithjks.musicplayer.Model.Music
import com.codingwithjks.musicplayer.R
import com.codingwithjks.musicplayer.ViewModel.MusicViewModel
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() ,Listener{
    private lateinit var recyclerView: RecyclerView
    private lateinit var musicList: ArrayList<Music>
    private lateinit var musicViewModel:MusicViewModel
    private val READ_STORAGE = 100
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var mediaPlayer: MediaPlayer

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayer= MediaPlayer()
        musicList = ArrayList()
        initRecyclerView()
        initViewModel()
        getPermission()
    }

    private fun initViewModel() {
        musicViewModel=ViewModelProvider(this).get(MusicViewModel::class.java)

        musicViewModel.getAllMusic(this)?.observe(this, androidx.lifecycle.Observer {
            musicAdapter.apply {
                setMusicData(it as ArrayList<Music>)
               notifyDataSetChanged()
            }

        })


    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        musicAdapter = MusicAdapter(this, ArrayList<Music>(),this)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = musicAdapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE
            )
        } else {
            readExternalData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_STORAGE -> if ((grantResults.isNotEmpty()) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                readExternalData()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)

    private fun readExternalData() {
        val contentResolver = contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                val music:Music
                val songName =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                val artistName =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                val songUrl = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))

                val secToMin=TimeUnit.MILLISECONDS.toMinutes(time)

                music= Music(songName,artistName,"$secToMin min",songUrl)
                musicList.add(music)
                Log.d("main", "readExternalData: ${music.artistName} ")
            }
             musicViewModel.insert(this,musicList)

        }
    }

    override fun onCardClickListener(position: Int) {
        val intent=Intent(this,PlaySong::class.java)
        intent.putExtra("song",musicList[position])
       startActivity(intent)

    }
}
