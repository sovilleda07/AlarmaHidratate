package com.example.alarmahidratate

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import java.util.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Establecemos el estilo creado
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Hacemos la transición de una activity a otra
        val intent = Intent(this@SplashScreen, DatosGenerales::class.java)
        startActivity(intent)
        finish()

    }
}
