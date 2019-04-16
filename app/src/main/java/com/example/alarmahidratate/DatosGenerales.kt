package com.example.alarmahidratate

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_datos_generales.*

class DatosGenerales : AppCompatActivity() {
    lateinit var idInsercion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_generales)

        // Cuando se le da click al botón aceptar al ingresar los datos
        bAceptar.setOnClickListener{
            Toast.makeText(this,"Se presiono esto",Toast.LENGTH_LONG).show()

            // Llamamos a la función para validar el ingreso de los campos
            // y el calculo del consumo esperado
            registrar()

        }

    }

    //  Funcion para validar el ingreso de todos lo datos
    private fun registrar(){
        // Variable para almacenar almacenar los valores de los layouts
        val correo= etCorreo.text.toString()
        val contrasena= etContrasena.text.toString()
        val nombreUsuario = etNombre.text.toString()
        val peso= etPeso.text.toString().toDouble()
        var genero = ""

        //Validar que se introduzcan campos llenos
        if (etCorreo.text.toString().isEmpty() || etContrasena.text.toString().isEmpty() || etNombre.text.toString().isEmpty() || etPeso.text.toString().isEmpty() || !(rbFemenino.isChecked || rbMasculino.isChecked)) {
            Toast.makeText(this,"Ingrese todos los datos",Toast.LENGTH_LONG).show()
        }else{

            // Evaluación de la selección de RadioButton
            if (rbFemenino.isChecked) {
                genero = "Femenino"
            } else if (rbMasculino.isChecked){
                genero = "Masculino"}

            Toast.makeText(this,correo,Toast.LENGTH_LONG).show()

            /* Implementación de Firebase
               Creando una instancia de autenticación con el correo y la contraseña
               Obteniendo los valores de las variables correo y contraseña
            */

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, contrasena)
            // Si el proceso de autenticación no es completado con éxito, devolver al usuario par que lo complete
                .addOnCompleteListener {
                    if (!it.isSuccessful){
                        return@addOnCompleteListener
                    }else {
                        // si es completado con éxito
                        Toast.makeText(this,"Su cuenta se ha creado con exito :${it.result?.user?.uid}",Toast.LENGTH_SHORT).show()
                        idInsercion = it.result?.user?.uid.toString()
                        guardarUsuario(nombreUsuario, peso, genero)
                    }
                }
        }
    }

    private fun guardarUsuario(nombreUsuario: String, peso: Double, genero: String){
        val idUsuario = FirebaseAuth.getInstance().uid ?: ""

        val referenciaBaseDatos = FirebaseDatabase.getInstance().getReference("/usuarios/$idUsuario")
        val usuario = Datos1(idUsuario, nombreUsuario,peso,genero)

        referenciaBaseDatos.setValue(usuario)
            .addOnSuccessListener {
                Toast.makeText(this, "Se ha guardado con exito el usuario", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "falló  ${it.message}", Toast.LENGTH_SHORT).show()
            }



    }
    class Datos1(val idUsuario: String, val nombreUsuario: String, val peso: Double, val genero: String)
}