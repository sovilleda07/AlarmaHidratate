package com.example.alarmahidratate

// Clase para manejar el historial del consumo
class Historial(val nombre: String, val consumo: Int, val fecha: String, val hora: String){
    constructor(): this("",0,"", "")
}