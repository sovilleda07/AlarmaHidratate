package com.example.alarmahidratate

// Clase para obtener los datos generales del usuario
class Datos(val idUsuario: String, val nombreUsuario: String, val peso: Double, val genero: String, val consumoEsperado: Int, val consumoIngresado: Int) {
    constructor(): this("","", 0.00,"",0,0)
    companion object {
        // varible estática
        var idUsuarioFB : String = ""
        var consumoUsuario : Int = 0

        // función estática para calcular el consumo esperado de agua
        fun consumoAgua(peso: Double, genero: String): Int {
            val consumo: Int
            val conversionOnza = 29.5735
            consumo = when (genero) {
                "Femenino" -> ((peso / 2) * conversionOnza).toInt()
                "Masculino" -> (500+(peso / 2) * conversionOnza).toInt()
                else -> 0
            }
            return consumo

        }
    }

}
