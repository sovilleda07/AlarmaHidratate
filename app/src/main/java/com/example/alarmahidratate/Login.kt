package com.example.alarmahidratate

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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
        if (etCorreo.text.toString().isEmpty() || etContrasena.text.toString().isEmpty()){
            Toast.makeText(this,"Ingrese todos los datos",Toast.LENGTH_SHORT).show()
        }else{
            val correo = etCorreo.text.toString()
            val contrasena = etContrasena.text.toString()

            //Iniciando Login de usuario ya registrado
            FirebaseAuth.getInstance().signInWithEmailAndPassword(correo,contrasena)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro al iniciar", Toast.LENGTH_SHORT).show()
                }
        }
}


}
