package com.example.alarmahidratate

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.alarmahidratate.Datos.Companion.idUsuarioFB
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    // Inicialización de variables que manejarán el layout
    private var tvEmail: TextView? = null
    private var tvPassword: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Mapear las variables a las vistas del layout
        tvEmail = findViewById(R.id.etCorreo)
        tvPassword = findViewById(R.id.etContrasena)

        btnIngresar.setOnClickListener{
            // Llamamos a la función que verificará si el usuario ya existe.
            validarCampos()
        }

        btnRegistrarse.setOnClickListener{
            // Pasar a la otra actividad
            val intent = Intent(this, DatosGenerales::class.java)
            startActivity(intent)
            finish()
        }
    }

//    Función para validar el ingreso de todos los datos y si el usuario existe
    private fun validarCampos(){

        // Verificamos si se ingresaron todos los datos
        if (etCorreo.text.toString().isEmpty() || etContrasena.text.toString().isEmpty()){
            Toast.makeText(this, getString(R.string.errorDatos),Toast.LENGTH_SHORT).show()
        }else{

            // Creación de varibles para almacenar los valor de los elementos del layout
            val correo = etCorreo.text.toString()
            val contrasena = etContrasena.text.toString()

            //Iniciando Login de usuario ya registrado
            FirebaseAuth.getInstance().signInWithEmailAndPassword(correo,contrasena)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener

                    // Guardamos el idUsuario actual en la variable estática
                    idUsuarioFB = FirebaseAuth.getInstance().uid ?: ""
//                    Toast.makeText(this, idUsuarioFB, Toast.LENGTH_SHORT).show()

                    // Pasamos al menú principal
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    Toast.makeText(this, getString(R.string.ingrese), Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, getString(R.string.errorInicio), Toast.LENGTH_SHORT).show()
                    tvEmail?.text = ""
                    tvPassword?.text = ""
                }
        }
}


}
