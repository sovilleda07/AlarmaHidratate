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
private fun validarCampos(){
        val genero : String
        if (etNombre.text.toString().isEmpty() || etPeso.text.toString().isEmpty() || !(rbFemenino.isChecked || rbMasculino.isChecked)) {
            Toast.makeText(this,"Ingrese todos los datos",Toast.LENGTH_LONG).show()
        }else{

            genero = when {
                rbFemenino.isChecked -> "Femenino"
                else -> "Masculino"
            }

            val nombre : String = etNombre.text.toString()
            val peso = etPeso.text.toString()

            // Llamamos a la función para calcular el consumo de Agua esperado
            val agua = consumoAgua(peso.toFloat(),genero)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Nombre",nombre)
            intent.putExtra("Peso", peso)
            intent.putExtra("Genero", genero)
            intent.putExtra("Consumo", agua)

            startActivity(intent)
            finish()
        }
    }
}


    // Función para calular el consumo de agua ideal
fun consumoAgua(peso : Float, genero: String) : Int{
        val consumo: Int
        val conversionOnza = 29.5735
        consumo = when (genero) {
            "Femenino" -> ((peso / 2) * conversionOnza).toInt()
            else -> (500+(peso / 2) * conversionOnza).toInt()
        }
        return consumo

    }
