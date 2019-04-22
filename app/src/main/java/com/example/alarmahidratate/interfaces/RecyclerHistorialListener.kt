package com.example.alarmahidratate.interfaces

import com.example.alarmahidratate.Historial

interface RecyclerHistorialListener {
    fun onClick(historial: Historial, position: Int)
    fun onLongClick(historial: Historial, position: Int)
}