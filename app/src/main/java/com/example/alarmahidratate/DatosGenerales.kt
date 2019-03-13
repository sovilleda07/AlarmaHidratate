package com.example.alarmahidratate

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_datos_generales.*

class DatosGenerales : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_generales)

        // Cuando se le da click al botón aceptar al ingresar los datos
        bAceptar.setOnClickListener{

            // Llamamos a la función para validar el ingreso de los campos
            // y el calculo del consumo esperado
            validarCampos()

        }

    }

    //  Funcion para validar el ingreso de todos lo datos
    private fun validarCampos(){
        // Variable par almacenar el valor al seleccionar el RadioButton
        var genero = ""

        //Validar campos llenos
        if (etNombre.text.toString().isEmpty() || etPeso.text.toString().isEmpty() || !(rbFemenino.isChecked || rbMasculino.isChecked)) {
            Toast.makeText(this,"Ingrese todos los datos",Toast.LENGTH_LONG).show()
        }else{

            // Evaluación de la selección de RadioButton
            if (rbFemenino.isChecked) {
                genero = "Femenino"
            } else if (rbMasculino.isChecked){
                genero = "Masculino"}

            // Damos valor a las variables estáticas de la clase Datos
            Datos.nombre = etNombre.text.toString()
            Datos.genero = genero
            Datos.peso = etPeso.text.toString().toDouble()

            // Pasar a la otra actividad
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}