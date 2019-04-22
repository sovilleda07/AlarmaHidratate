package com.example.alarmahidratate.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alarmahidratate.Historial
import com.example.alarmahidratate.R
import com.example.alarmahidratate.interfaces.RecyclerHistorialListener
import kotlinx.android.synthetic.main.template_historial.view.*

class AdaptadorHistorial(private var listaHistorial: List<Historial>, private var listener: RecyclerHistorialListener)
    : RecyclerView.Adapter<AdaptadorHistorial.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorHistorial.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_historial, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = listaHistorial.size

    override fun onBindViewHolder(holder: AdaptadorHistorial.ViewHolder, position: Int) = holder.bind(listaHistorial[position], listener)


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(historial: Historial, listener: RecyclerHistorialListener) = with(itemView) {
            tvFecha.text = historial.fecha
            tvHora.text = historial.hora
            tvNombreContenedor.text = historial.nombre
            tvConsumo.text = historial.consumo.toString()

            // Implementar los eventos
            setOnClickListener { listener.onClick(historial, adapterPosition) }
        }

    }
}