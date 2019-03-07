package com.example.alarmahidratate

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val intent = Intent(this@SplashScreen, MainActivity::class.java)
        startActivity(intent)
        finish()


       // setContentView(R.layout.activity_splash_screen)
        //Timer().schedule(object : TimerTask(){
           // override fun run() {
             //   val intent = Intent(this@SplashScreen, MainActivity::class.java)
               // startActivity(intent)
                //finish()
            //}

        //}, 1200L)
    }
}
