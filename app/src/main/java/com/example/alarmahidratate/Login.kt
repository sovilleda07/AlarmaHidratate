package com.example.alarmahidratate

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnIngresar.setOnClickListener{
            validarCampos()

        }

        btnRegistrarse.setOnClickListener{
            // Pasar a la otra actividad
            val intent = Intent(this, DatosGenerales::class.java)
            startActivity(intent)
            finish()
        }
    }

//    Función para validar el ingreso de todos los datos
    private fun validarCampos(){
        if (etUsuario.text.toString().isEmpty() || etContrasena.text.toString().isEmpty()){
            Toast.makeText(this,"Ingrese todos los datos",Toast.LENGTH_SHORT).show()
        }else{
            // Pasar a la otra actividad
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
}


}
