package com.example.alarmahidratate.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.alarmahidratate.Adapter.AdaptadorHistorial
import com.example.alarmahidratate.Datos
import com.example.alarmahidratate.Historial

import com.example.alarmahidratate.R
import com.example.alarmahidratate.interfaces.RecyclerHistorialListener
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentHistorial : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    // Variables necesarias para el Fragment
    private val historialList: MutableList<Historial> = mutableListOf()
    private val keysList: MutableList<String> = mutableListOf()
    private lateinit var adapter: AdaptadorHistorial
    private var valorConsumo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Creamos una variable de tipo vista para almacenar el fragment que se infla y después retornarla
        val vista = inflater.inflate(R.layout.fragment_fragment_historial, container, false)

        // Hacemos un mapeo del RecyclerView que se encuentra en el layout del Fragment
        val rvHistorial = vista.findViewById<RecyclerView>(R.id.rvHistorial)

        // Hacemos la carga del consumo
        cargarDatos()

        // Creamos una variable para manejar el recycler en el fragment
        val layoutManager =  LinearLayoutManager(context)

        // Obtenemos la fecha actual del celular para filtrar el historial
        val fecha = getDate()

        // Creamos la referencia a la BD filtrando con el id del Usuario en curso
        val ref = FirebaseDatabase.getInstance().getReference("/historial/${Datos.idUsuarioFB}").orderByChild("fecha").equalTo(
            fecha
        )
        // Creamos el evento para leer desde Firebase
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity, getString(R.string.errorcarga), Toast.LENGTH_SHORT).show()
            }
            // Si se cargan los datos
            override fun onDataChange(p0: DataSnapshot) {
                // Limpiamos las listas donde se almacean los datos del RecyclerView y las Keys
                keysList.clear()
                historialList.clear()
                // Realizamos un ciclo for para poder recorrer todos los subnodos o nodos hijos
                // que dieron como resultado según la referencia hecha al inicio (filtrado)
                for (h in p0.children){
                    // En la siguiente variable almacenaremos el objeto de tipo Historial que
                    // trae todos los datos de Firebase y después se agregan en la Lista Mutable creada anteriormente
                    val historial = h.getValue(Historial::class.java)
                    keysList.add(h.key!!)
                    if (historial != null) {
                        historialList.add(historial)
                    }
                }
                // Llamos al adaptador para hacer el enlace entre la Base de Datos en Firebase y
                // el template de nuestro RecyclerView
                adapter = AdaptadorHistorial(historialList, object : RecyclerHistorialListener {
                    override fun onClick(historial: Historial, position: Int) {
//                        Toast.makeText(activity, keysList[position], Toast.LENGTH_SHORT).show()
                        //Toast.makeText(activity, historial.consumo.toString(), Toast.LENGTH_SHORT).show()
                        showDialog(keysList[position], historial.consumo)
                    }

                    override fun onLongClick(historial: Historial, position: Int) {

                    }
                })

                // Establecemos propiedades al RecyclerView
                rvHistorial.setHasFixedSize(true)
                rvHistorial.layoutManager = layoutManager
                rvHistorial.itemAnimator = DefaultItemAnimator()
                rvHistorial.adapter = adapter


            }

        })
        return vista

    }

    // Función para cargar los datos del usuario
    private fun cargarDatos(){
        // Hacemos referencia al nodo que contiene la información del usuario, filtrando por el id de este
        val ref = FirebaseDatabase.getInstance().getReference("/usuarios/${Datos.idUsuarioFB}")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity, getString(R.string.errorcarga), Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                // Creamos una variable de tipo Datos para traer toda la información del Nodo
                val usuario = p0.getValue(Datos::class.java)
                // si la información de Firebase no es nula, nos llenará el TextView con el consumo ingresado
                if (usuario != null) {
                    valorConsumo = usuario.consumoIngresado
                }
            }

        })
    }

    // Función para desplegar el AlertDialog
    private fun showDialog(idHistorialConsumo: String, valorConsumoEliminar: Int){
        // Creamos un AlertDialog
        val builder = AlertDialog.Builder(activity)

        // Colocamos un título
        builder.setTitle("Eliminar consumo")

        // Colocamos el mensaje a desplegar en el cuerpo del AlertDialog
        builder.setMessage("¿Está seguro de eliminar este consumo?")

        // Calculamos el nuevo consumo
        val consumoCalculado = calcularNuevoConsumo(valorConsumoEliminar)

        // Cuando se presione el botón de Eliminar
        builder.setPositiveButton("Si") { dialog, which ->
            // Llamamos al método para eliminar
            eliminarConsumo(idHistorialConsumo, consumoCalculado)
        }

        // Cuando se presione el botón de NO
        builder.setNegativeButton("No") { dialog, which ->
            dialog.cancel()
        }

        // Creamos nuestra alerta
        val alert = builder.create()
        // Y la mostramos
        alert.show()

    }

    // Función para eliminar el registro de consumo del usuario
    private fun eliminarConsumo(idHistorialConsumo: String, nuevoConsumo: Int){
        // Creamos la referencia al nodo del registro del Usuario de nuestra BD
        val refHistorial = FirebaseDatabase.getInstance().getReference("/historial/${Datos.idUsuarioFB}")

        // Hacemos referencia al subnodo del consumo en específico y lo eliminamos
        refHistorial.child(idHistorialConsumo).removeValue()
            .addOnSuccessListener {
                Toast.makeText(activity, "Consumo eliminado", Toast.LENGTH_SHORT).show()
                actualizarConsumo(nuevoConsumo)
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show()
            }

    }

    // Función para calcular el nuevo consumo al eliminar uno del historial
    private  fun calcularNuevoConsumo(valorConsumoEliminar: Int) : Int {
        var nuevoConsumo= 0
        when {
            valorConsumo == 0 -> Toast.makeText(activity,"No se puede eliminar",Toast.LENGTH_SHORT).show()
            valorConsumo < valorConsumoEliminar -> nuevoConsumo = 0
            else -> nuevoConsumo = valorConsumo - valorConsumoEliminar
        }
        return nuevoConsumo
    }

    // Función para actualizar el Consumo Ingresado de agua del Usuario
    private fun actualizarConsumo(consumoActual: Int) {
        // Creamos la referencia al nodo Usuarios de nuestra BD
        val referenciaUsuario = FirebaseDatabase.getInstance().getReference("usuarios")

        // Insertamos en Firebase
        referenciaUsuario.child(Datos.idUsuarioFB).child("consumoIngresado").setValue(consumoActual)
            .addOnSuccessListener {
                Toast.makeText(activity, "Consumo actualizado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Error consumo ingresado", Toast.LENGTH_SHORT).show()
            }
    }

    // Función para tomar la fecha actual del celular
    @SuppressLint("SimpleDateFormat")
    private fun getDate(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val locale = Locale(getString(R.string.localeLanguage), getString(R.string.localeCountry))
            val formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy", locale)
            val answer: String = current.format(formatter)
            answer
        } else {
            val fecha = Date()
            val formatter = SimpleDateFormat("dd LLLL yyyy")
            return formatter.format(fecha)
        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHistorial().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
