package com.example.alarmahidratate

// Clase para obtener los datos generales del usuario
class Datos() {
    companion object {

        // variables estáticas
        var nombre : String = ""
        var genero : String = ""
        var peso : Double = 0.0

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
