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

        bAceptar.setOnClickListener{

            validarCampos()

        }

    }

//  Funcion para validar el ingreso de todos lo datos
    fun validarCampos(){
        val genero : String
        if (etNombre.text.toString().isEmpty() || etPeso.text.toString().isEmpty() || !(rbFemenino.isChecked || rbMasculino.isChecked)) {
            Toast.makeText(this,"Ingrese todos los datos",Toast.LENGTH_LONG).show()
        }else{

//           Esto es para que muestre un mensaje según el radioButton seleccionado
            if (rbFemenino.isChecked()) {
                genero = "Femenino"
//                Toast.makeText(this,"Femenino",Toast.LENGTH_LONG).show()
            }else{
//                Toast.makeText(this,"Masculino",Toast.LENGTH_LONG).show()
                genero = "Masculino"
            }

            val nombre : String = etNombre.text.toString()
            val peso : Float = etPeso.text.toString().toFloat()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Nombre",nombre)
            intent.putExtra("Peso", peso)
            intent.putExtra("Genero", genero)

            startActivity(intent)
            finish()
        }
    }
}
