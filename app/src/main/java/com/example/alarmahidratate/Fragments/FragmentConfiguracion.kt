package com.example.alarmahidratate.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.example.alarmahidratate.Datos

import com.example.alarmahidratate.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentConfiguracion.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentConfiguracion.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentConfiguracion : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    // Inicialización de variables que manejarán el layout
    private lateinit var tvNombre: TextView
    private lateinit var tvPeso: TextView
    private lateinit var tvGenero: TextView

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
        val v =inflater.inflate(R.layout.fragment_fragment_configuracion2, container, false)

        // Mapear las variables a las vistas del layout
        tvNombre = v.findViewById(R.id.tvNombreUsuario)
        tvPeso = v.findViewById(R.id.tvPesoUsuario)
        tvGenero = v.findViewById(R.id.tvGeneroUsuario)

        cargarDatos()

        tvNombre.setOnClickListener{
            Toast.makeText(activity,"Se presionó nombre", Toast.LENGTH_SHORT).show()
            alertDialogNombre()
        }

        tvPeso.setOnClickListener {
            alertDialogPeso()

        }

        tvGenero.setOnClickListener{
            //alertDialogGenero()

        }

        return v
    }


    /*fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }*/

    // Función para cargar los datos del usuario
    private fun cargarDatos(){
        // Hacemos referencia al nodo que contiene los usuarios especificando el usuario actual por medio del id de este
        val refUsuario = FirebaseDatabase.getInstance().getReference("/usuarios/${Datos.idUsuarioFB}")
        refUsuario.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                // creamos una varible de tipo Datos para traer toda la infoormación del Nodo
                val usuario = p0.getValue(Datos::class.java)
                // si la información de Firebase no es nula
                // llenará los Textview con los datos correspondientes
                if (usuario != null){
                    tvNombre.text = usuario.nombreUsuario
                    tvPeso.text = usuario.peso.toString()
                    tvGenero.text = usuario.genero

                }
            }

            // Mensaje por si no se cargan los datos
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity,getString(R.string.errorcarga),Toast.LENGTH_SHORT).show()
            }
        })
    }

    // función para mostrar un AlertDialog para modificar el nombre del usuario
    private fun alertDialogNombre(){
        // Creamos un AlertDialog
        val builder = AlertDialog.Builder(activity)

        // Colocamos un título
        builder.setTitle("Cambiar Nombre")

        // Creamos el EditText donde se ingresará el nuevo nombre
        val textNombre = EditText(activity)

        // Tomamos el valor del nombre actual
        textNombre.setText(tvNombre.text)

        // Seteamos la vista que contiene el layout
        builder.setView(textNombre)

        // Cuando se presione el botón de Actualizar
        builder.setPositiveButton("Actualizar") { dialog, which ->
            val nuevoNombre = textNombre.toString().trim()

            // Si el nombre está vacío que muestre un error
            if (nuevoNombre.isEmpty()){
                textNombre.error = "Ingrese un nombre"
                Toast.makeText(activity, "Ingrese un nombre", Toast.LENGTH_SHORT).show()
                textNombre.requestFocus()
            }else{
                // AQUI VA LA FUNCIÓN DE ACTUALIZAR
            }

        }

        // Cuando se presione el botón de NO
        builder.setNegativeButton("No") { dialog, which ->

        }

        // Creamos nuestra alerta
        val alert = builder.create()
        // Y la mostramos
        alert.show()

    }

    // Función para mostrar un AlertDialog para modificar el peso del usuario
    private fun alertDialogPeso(){
        // Creamos un AlertDialog
        val builder = AlertDialog.Builder(activity)

        // Colocamos un Título
        builder.setTitle("Cambiar peso")

        // Vamos a inflar el layout
        val inflater = LayoutInflater.from(activity)

        // Creamos una vista para poder inflar el layout creado que contendrá las ocpciones a actualizar
        val view = inflater.inflate(R.layout.layout_update_peso, null)

        // Los valores que tomaremos del layout
        val editText = view.findViewById<EditText>(R.id.editTextPeso)

        // Tomamos el valor del peso actual del usuario
        editText.setText(tvPeso.text)

        // Seteamos la vista que contiene el layout
        builder.setView(view)

        // Cuando se presione el botón de actualizar
        builder.setPositiveButton("Actualizar") { dialog, which ->
            // Tomamos el valor del editText escrito
            val nuevoPeso = editText.text.toString().toInt()

            // Si el peso ingresado es 0, no permitir actualizar
            if (nuevoPeso == 0){
                editText.error = "Ingrese un valor diferente a cero"
                Toast.makeText(activity, "Ingrese un valor diferente a cero", Toast.LENGTH_SHORT ).show()
            }
            else{
                // Llamamos a la función para actualizar al nuevo peso
                // actualizarDatos()
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

    // Función para mostrar un AlertDialog para modificar el genero del usuario
    private fun alertDialogGenero(){
        // Creamos un AlertDialog
        val builder = AlertDialog.Builder(activity)

        // Colocamos un Título
        builder.setTitle("Cambiar peso")

        // Vamos a inflar el layout
        val inflater = LayoutInflater.from(activity)

        // Creamos una vista para poder inflar el layout creado que contendrá las ocpciones a actualizar
        val view = inflater.inflate(R.layout.layout_update_peso, null)

        // Los valores que tomaremos del layout
        val rbF = view.findViewById<RadioButton>(R.id.rbFemenino)
        val rbM = view.findViewById<RadioButton>(R.id.rbMasculino)

        // Tomaremos el genero actual
        val genero = tvGenero.text

        // -------------------AQUI ESTÁ EL PROBLEMA
        if (genero == "Femenino"){
            rbF.isChecked
        } else if(genero == "Masculino"){
            rbM.isChecked
        }

        // Seteamos la vista que contiene el layout
        builder.setView(view)

        // Cuando se presione el botón de actualizar
        builder.setPositiveButton("Actualizar") { dialog, which ->
            // Tomamos el nuevo valor del RadioButton
            var nuevoGenero = ""

            // Evaluación de la selección de RadioButton
            if (rbF.isChecked){
                nuevoGenero = "Femenino"
            } else if(rbM.isChecked){
                nuevoGenero = "Masculino"
            }

            Toast.makeText(activity,nuevoGenero,Toast.LENGTH_SHORT).show()



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

    // Función para actualizar los datos del usuario
    private fun actualizarDatos(){

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
         * @return A new instance of fragment FragmentConfiguracion.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentConfiguracion().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
