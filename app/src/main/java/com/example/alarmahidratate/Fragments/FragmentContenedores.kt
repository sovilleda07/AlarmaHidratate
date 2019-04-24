package com.example.alarmahidratate.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.alarmahidratate.Contenedor
import com.example.alarmahidratate.Datos
import com.example.alarmahidratate.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_fragment_contenedores.view.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentContenedores : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    // Variables utilizadas para los Layouts
    var tvTVaso: TextView? = null
    var tvTTaza: TextView? = null
    var tvTBotella: TextView? = null

    // Varibles utilizadas para obtener los tamaños de los contenedores
    var valorVaso: Int = 0
    var valorTaza: Int = 0
    var valorBotella: Int = 0

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

        // Creamos una variable para que almacene el View y luego retornarla
        val v =inflater.inflate(R.layout.fragment_fragment_contenedores, container, false)

        // Mapear las varibles a las vistas del layout
        tvTVaso = v.findViewById(R.id.tvTVaso)
        tvTTaza = v.findViewById(R.id.tvTTaza)
        tvTBotella = v.findViewById(R.id.tvTBotella)

        // Llamamos a las funciones para cargar los datos al Layout
        cargarVaso()
        cargarTaza()
        cargarBotella()

        // Botones para modificar los tamaños de los contenedores
        v.bCambiarVaso.setOnClickListener{
            // Llamamos a la función para mostrar el Alert Dialog
            // enviándole como parámetro el tipo de contenedor a modificar
            showUpdateDialog("Vaso")
        }
        v.bCambiarTaza.setOnClickListener{
            // Llamamos a la función para mostrar el Alert Dialog
            // enviándole como parámetro el tipo de contenedor a modificar
            showUpdateDialog("Taza")
        }
        v.bCambiarBotella.setOnClickListener{
            // Llamamos a la función para mostrar el Alert Dialog
            // enviándole como parámetro el tipo de contenedor a modificar
            showUpdateDialog("Botella")
        }
        return v


    }

    @SuppressLint("InflateParams")
    // Función mostrar un AlertDialog para actualizar el tamaño de los contenedores
    fun showUpdateDialog(nombreContenedor : String){
        // Creamos un AlertDialog
        val builder = AlertDialog.Builder(activity)

        // Colocamos un título
        builder.setTitle("Cambiar tamaño de contenedor")

        // vamos a inflar el layout
        val inflater = LayoutInflater.from(activity)

        // Creamos una vista para poder inflar el layout creado que contendrá las opciones a actualizar
        val view = inflater.inflate(R.layout.layout_update_contenedor,null)

        // Los valores que tomaremos el layout
        val editText = view.findViewById<EditText>(R.id.editTextContenedor)

        // Dependiendo del contenedor a actualizar
        when (nombreContenedor) {
            "Vaso" -> {
                // Tomamos el valor del tamaño actual del contenedor
                editText.setText(tvTVaso?.text)

                // Seteamos la vista que contiene el layout
                builder.setView(view)

                // Cuando se presione el botón de actualizar
                builder.setPositiveButton("Actualizar") { dialog, which ->
                    // Tomamos el valor del editText escrito
                    val tamano = editText.text.toString()

                    // Si el tamaño ingresado es 0, no permitir actualizar
                    if (tamano.isEmpty() ||tamano.isBlank() || tamano.toInt() == 0){
                        editText.error = "Ingrese un valor diferente a cero"
                        Toast.makeText(activity, "Ingrese un valor diferente a cero", Toast.LENGTH_SHORT).show()
                        editText.requestFocus()
                    }
                    else{
                        // Verificamos que el tamaño nuevo del contenedor está permitido
                        if (tamano.toInt() < valorTaza){
                            // Si está permitido, llamamos la función que actualizará el tamaño
                            // enviando como parámetro el nombre del contenedor y su tamaño
                            this.actualizarContenedor(nombreContenedor,tamano.toInt())
                        }
                        else{
                            // sino, mostramos un mensaje de error
                            Toast.makeText(activity, "No puede cambiar el tamano", Toast.LENGTH_SHORT).show()
                        }
                    }
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
            "Taza" -> {
                // Tomamos el valor del tamaño actual del contenedor
                editText.setText(tvTTaza?.text)

                // Seteamos la vista que contiene el layout
                builder.setView(view)

                // Cuando se presione el botón de actualizar
                builder.setPositiveButton("Actualizar") { dialog, which ->
                    // Tomamos el valor del editText escrito
                    val tamano = editText.text.toString()

                    // Si el tamaño ingresado es 0, no permitir actualizar
                    if (tamano.isEmpty() ||tamano.isBlank() || tamano.toInt() == 0){
                        Toast.makeText(activity, "Ingrese un valor diferente a cero", Toast.LENGTH_SHORT).show()
                        editText.requestFocus()
                    }
                    else{
                        // Verificamos que el tamaño nuevo del contenedor está permitido
                        if (tamano.toInt() > valorVaso && tamano.toInt() < valorBotella){
                            // Si está permitido, llamamos la función que actualizará el tamaño
                            // enviando como parámetro el nombre del contenedor y su tamaño
                            this.actualizarContenedor(nombreContenedor,tamano.toInt())
                        }
                        else {
                            // sino, mostramos un mensaje de error
                            Toast.makeText(activity, "No puede cambiar el tamano", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                // Cuando se presione el botón de no
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.cancel()
                }

                // Creamos nuestra alerta
                val alert = builder.create()
                // Y la mostramos
                alert.show()

            }
            "Botella" -> {
                // Tomamos el valor del tamaño actual del contenedor
                editText.setText(tvTBotella?.text)

                // Seteamos la vista que contiene el layout
                builder.setView(view)

                // Cuando se presione el botón de actualizar
                builder.setPositiveButton("Actualizar") { dialog, which ->
                    // Tomamos el valor del editText escrito
                    val tamano = editText.text.toString()

                    // Si el tamaño ingresado es 0, no permitir actualizar
                    if (tamano.isEmpty() ||tamano.isBlank() || tamano.toInt() == 0){
                        Toast.makeText(activity, "Ingrese un valor diferente a cero", Toast.LENGTH_SHORT).show()
                        editText.requestFocus()
                    }
                    else{
                        // Verificamos que el tamaño nuevo del contenedor está permitido
                        if (tamano.toInt() > valorTaza) {
                            // Si está permitido, llamamos la función que actualizará el tamaño
                            // enviando como parámetro el nombre del contenedor y su tamaño
                            this.actualizarContenedor(nombreContenedor,tamano.toInt())
                        }
                        else {
                            // sino, mostramos un mensaje de error
                            Toast.makeText(activity, "No puede cambiar el tamano", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

                // Cuando se presione el botón de no
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.cancel()
                }

                // Creamos nuestra alerta
                val alert = builder.create()
                // Y la mostramos
                alert.show()

            }
        }

    }

    override fun onResume() {
        super.onResume()
        // Llamamos a las funciones para cargar los datos al Layout
        cargarVaso()
        cargarTaza()
        cargarBotella()
    }

    // Función para cargar los datos del contenedor Vaso según el usuario actual
    private fun cargarVaso(){
        // Hacemos referencia la nodo que contiene los contenedores con la Key específico del usuario
        val refVaso = FirebaseDatabase.getInstance().getReference("/contenedores/Vaso-${Datos.idUsuarioFB}")
        refVaso.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                // creamos una variable de tipo Contenedor para traer toda la información del Nodo
                val vaso = p0.getValue(Contenedor::class.java)
                // si la información de firebase no es nula, nos llenará los TextView con los correspondientes
                if (vaso != null){
                    tvTVaso?.text = vaso.tamano.toString()
                    valorVaso = vaso.tamano.toString().toInt()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity,getString(R.string.errorcarga),Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Función para cargar los datos del contenedor Taza según el usuario actual
    private fun cargarTaza(){
        // Hacemos referencia la nodo que contiene los contenedores con la Key específico del usuario
        val refTaza = FirebaseDatabase.getInstance().getReference("/contenedores/Taza-${Datos.idUsuarioFB}")
        refTaza.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                // creamos una variable de tipo Contenedor para traer toda la información del Nodo
                val taza = p0.getValue(Contenedor::class.java)
                // si la información de firebase no es nula, nos llenará los TextView con los correspondientes
                if (taza != null){
                    tvTTaza?.text = taza.tamano.toString()
                    valorTaza = taza.tamano.toString().toInt()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity,getString(R.string.errorcarga),Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Función para cargar los datos del contenedor Botella según el usuario actual
    private fun cargarBotella(){
        // Hacemos referencia la nodo que contiene los contenedores con la Key específico del usuario
        val refBotella = FirebaseDatabase.getInstance().getReference("/contenedores/Botella-${Datos.idUsuarioFB}")
        refBotella.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                // creamos una variable de tipo Contenedor para traer toda la información del Nodo
                val botella = p0.getValue(Contenedor::class.java)
                // si la información de firebase no es nula, nos llenará los TextView con los correspondientes
                if (botella != null){
                    tvTBotella?.text = botella.tamano.toString()
                    valorBotella = botella.tamano.toString().toInt()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity,getString(R.string.errorcarga),Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Función para actualizar el tamaño del contenedor elegido por el usuario
    private fun actualizarContenedor(nombre: String, nuevoTamano: Int){
        /*Creamos la referencia al nodo del contenedore del usuario de nuestra BD
        como al momento de crear el usuario, se crean los contenedores con un Key en específico,
        que es la combinación del nombre del contenedor, guión, seguido del Id del Usuario*/

        // Recibimos como parámetro el nombre del contenedor que se utiliza para hacer referencia al nodo
        // que tiene como Key la combinación anteriormente descrita.
        val referenciaContenedor = FirebaseDatabase.getInstance().getReference("/contenedores/$nombre-${Datos.idUsuarioFB}")

        // Hacemos la actualización del tamano
        referenciaContenedor.child("tamano").setValue(nuevoTamano)
            .addOnSuccessListener {
                Toast.makeText(activity,"Contenedor actualizado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
    }

    /*fun onButtonPressed(uri: Uri) {
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
         * @return A new instance of fragment FragmentContenedores.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentContenedores().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
