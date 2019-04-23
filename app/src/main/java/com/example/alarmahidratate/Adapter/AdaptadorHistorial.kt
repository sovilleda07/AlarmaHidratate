package com.example.alarmahidratate.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.alarmahidratate.Historial
import com.example.alarmahidratate.R
import com.example.alarmahidratate.interfaces.RecyclerHistorialListener
import kotlinx.android.synthetic.main.template_historial.view.*

class AdaptadorHistorial(private val historial: List<Historial>, private val listener: RecyclerHistorialListener)
    : RecyclerView.Adapter<AdaptadorHistorial.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorHistorial.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_historial, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = historial.size

    override fun onBindViewHolder(holder: AdaptadorHistorial.ViewHolder, position: Int){
        holder.bind(historial[position], listener)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(note: Historial, listener:RecyclerHistorialListener) = with(itemView) {

            tvFecha.text = note.fecha
            tvHora.text = note.hora
            tvNombreContenedor.text = note.nombre
            tvConsumo.text = note.consumo.toString()

            // Implementar los eventos
            setOnClickListener { listener.onClick(note, adapterPosition) }

        }

    }
}