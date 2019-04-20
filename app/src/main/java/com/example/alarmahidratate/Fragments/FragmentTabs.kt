package com.example.alarmahidratate.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.alarmahidratate.Contenedor
import com.example.alarmahidratate.Datos
import com.example.alarmahidratate.Historial

import com.example.alarmahidratate.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_fragment_tabs.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentTabs.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentTabs.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentTabs : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    // Inicialización de variables que manejarán el layout
    var tvConsumoEsperado: TextView? = null
    var tvNombreMain: TextView? = null
    var tvMVaso: TextView? = null
    var tvMTaza: TextView? = null
    var tvMBotella: TextView? = null
    var tvConsumoIngresado : TextView? = null
    var tvVaso: TextView? = null
    var tvTaza: TextView? = null
    var tvBotella: TextView? = null

    private var consumoIngresadoUsuario : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    // Para inflar el fragment en el MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Creamos una variable para que almacene el View y luego retornarla
        val v = inflater.inflate(R.layout.fragment_fragment_tabs, container, false)

        // Mapear las variables a las vistas del layout
        tvConsumoEsperado = v.findViewById(R.id.tvConsumoEsperado)
        tvNombreMain = v.findViewById(R.id.tvNombreMain)
        tvMVaso = v.findViewById(R.id.tvMVaso)
        tvMTaza = v.findViewById(R.id.tvMTaza)
        tvMBotella = v.findViewById(R.id.tvMBotella)
        tvConsumoIngresado = v.findViewById(R.id.tvConsumoIngresado)

        tvVaso = v.findViewById(R.id.tvVaso)
        tvTaza = v.findViewById(R.id.tvTaza)
        tvBotella = v.findViewById(R.id.tvBotella)

        // Llamamos las funciones para cargar los datos en el layout
        cargarDatos()
        cargarContenedores()

        // Detectar al presionar los Floating Action Button
        // Dependiendo del FAB presionado se actualizará el consumo ingresado
        // con el valor del contenedor

        var tamanoConsumo: Int
        var nombreContenedor: String
        v.fabVaso.setOnClickListener{
            // Tomamos la fecha y hora del celular
            val laFecha = getDate()
            val laHora = getTime()
            // Tomamos el tamaño del contenedor
            tamanoConsumo = tvMVaso?.text.toString().toInt()
            // Tomamos el nombre del contenedor
            nombreContenedor = tvVaso?.text.toString()
            // Realizamos la suma del consumo del usuario + el tamano del contenedor
            consumoIngresadoUsuario = Datos.consumoUsuario + tamanoConsumo
            // Registramos el consumo en el historial
            registrarConsumo(nombreContenedor, tamanoConsumo,laFecha, laHora)
            // Actualizamos el consumo ingresado por el usuario
            actualizarConsumo(consumoIngresadoUsuario)
        }
         v.fabTaza.setOnClickListener{
             // Tomamos la fecha y hora del celular
             val laFecha = getDate()
             val laHora = getTime()
             // Tomamos el tamaño del contenedor
             tamanoConsumo = tvMTaza?.text.toString().toInt()
             // Tomamos el nombre del contenedor
             nombreContenedor = tvTaza?.text.toString()
             // Realizamos la suma del consumo del usuario + el tamano del contenedor
             consumoIngresadoUsuario = Datos.consumoUsuario + tamanoConsumo
             // Registramos el consumo en el historial
             registrarConsumo(nombreContenedor, tamanoConsumo,laFecha, laHora)
             // Actualizamos el consumo ingresado por el usuario
             actualizarConsumo(consumoIngresadoUsuario)
         }
        v.fabBotella.setOnClickListener{
            // Tomamos la fecha y hora del celular
            val laFecha = getDate()
            val laHora = getTime()
            // Tomamos el tamaño del contenedor
            tamanoConsumo = tvMBotella?.text.toString().toInt()
            // Tomamos el nombre del contenedor
            nombreContenedor = tvBotella?.text.toString()
            // Realizamos la suma del consumo del usuario + el tamano del contenedor
            consumoIngresadoUsuario = Datos.consumoUsuario + tamanoConsumo
            // Registramos el consumo en el historial
            registrarConsumo(nombreContenedor, tamanoConsumo,laFecha, laHora)
            // Actualizamos el consumo ingresado por el usuario
            actualizarConsumo(consumoIngresadoUsuario)
        }

        // Retornamos la vista para inflarla
        return  v

    }

    // Sobreescribimos esta parte del ciclo de vida, para poder traer
    // el valor del agua consumida de la clase Datos
    override fun onResume() {
        super.onResume()
        cargarDatos()
        cargarContenedores()
    }

    // Función para cargar los datos del usuario
    private fun cargarDatos(){
        // Hacemos referencia al nodo que contiene los usuarios especificando el usuario actual por medio del id de este
        val ref = FirebaseDatabase.getInstance().getReference("/usuarios/${Datos.idUsuarioFB}")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                // creamos una variable de tipo Datos para traer toda la información del Nodo
                val usuario = p0.getValue(Datos::class.java)
                // si la información de firebase no es nula, nos llenará los TextView con los correspondientes
                if(usuario != null){
                    tvNombreMain?.text = usuario.nombreUsuario
                    tvConsumoEsperado?.text = usuario.consumoEsperado.toString()
                    tvConsumoIngresado?.text = usuario.consumoIngresado.toString()
                    Datos.consumoUsuario = usuario.consumoIngresado
                }
            }
            // Mensaje por si no se cargan los datos
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity,getString(R.string.errorcarga),Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Función para cargar los tamaños de los contenedores según el usuario actual
    private fun cargarContenedores(){
        // Hacemos referencia al nodo que contiene los contenedores filtrandolos por el campo del idUsuario con el id del usuario actual
        val ref = FirebaseDatabase.getInstance().getReference("contenedores").orderByChild("idUsuario").equalTo(Datos.idUsuarioFB)
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                // Como son 3 tipos de contenedores, creamos una lista mutable
                // para poder agregar los objetos de tipo Contenedor
                val contenedoresUsuario: MutableList<Contenedor> = mutableListOf()
                // Variable para almacenar la posición del objeto
                var cont=0
                // Realizamos un ciclo for para poder recorrer todos los subnodos o nodos hijos
                // que dieron como resultado según la referencia hecha al inicio (filtrado)
                for (datasnapshot in p0.children){
                    // En la siguiente varible almacenaremos el objeto de tipo Contenedor que
                    // trae todos los datos de Firebase y despues se agregan en la Lista Mutabble
                    val usuario = datasnapshot.getValue(Contenedor::class.java)
                    if (usuario != null) {
                        contenedoresUsuario.add(cont,usuario)
                        cont+=1
                    }
                }
                // si la información de firebase no es nula, nos llenará los TextView con los correspondientes
                tvMVaso?.text = contenedoresUsuario[2].tamano.toString()
                tvMTaza?.text = contenedoresUsuario[1].tamano.toString()
                tvMBotella?.text = contenedoresUsuario[0].tamano.toString()

            }
            // Mensaje por si no se cargan los datos
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity,getString(R.string.errorcarga),Toast.LENGTH_SHORT).show()
            }
        })

    }

    // Función para tomar la hora actual del celular
    @SuppressLint("SimpleDateFormat")
    private fun getDate(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val locale = Locale(getString(R.string.localeLanguage), getString(R.string.localeCountry))
            val formatter = DateTimeFormatter.ofPattern("hh:mm a", locale)
            val answer: String = current.format(formatter)
            answer
        } else {
            val fecha = Date()
            val formatter = SimpleDateFormat("hh:mm:ss a")
            return formatter.format(fecha)
        }
    }

    // Función para tomar la fecha actual del celular
    @SuppressLint("SimpleDateFormat")
    private fun getTime(): String {
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

    // Función para registrar en el Historial el consumo de agua
    // y Actualizar el ConsumoIngresado de Agua
    private fun registrarConsumo(nombre: String,tamano: Int, fecha : String, hora: String){
        // Creamos la referencia al nodo de Contenedores de nuestra BD
        val referenciaBDHistorial = FirebaseDatabase.getInstance().getReference("/historial/${Datos.idUsuarioFB}")

        // Variable de tipo Historial que almacenará toda la información a guardar en la BD
        val registro = Historial(nombre,tamano,fecha, hora)

        // Insertamos en Firebase
        referenciaBDHistorial.push().setValue(registro)
            .addOnSuccessListener {
                Toast.makeText(activity, "Consumo registrado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Error consumo", Toast.LENGTH_SHORT).show()
            }
    }

    // Función para actualizar el Consumo Ingresado de agua del usuario
    private fun actualizarConsumo(consumoActual: Int){
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
        // Volvemos a cargar los datos del usuario después de realizar la inserción
        this.cargarDatos()

    }


/*    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }*/

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentTabs.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentTabs().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
