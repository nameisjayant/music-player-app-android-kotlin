package com.codingwithjks.musicplayer.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.codingwithjks.musicplayer.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
        },3000)
    }
}