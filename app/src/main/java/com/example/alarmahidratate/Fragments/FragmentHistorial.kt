package com.example.alarmahidratate.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.alarmahidratate.Adapter.AdaptadorHistorial
import com.example.alarmahidratate.Datos
import com.example.alarmahidratate.Historial

import com.example.alarmahidratate.R
import com.example.alarmahidratate.interfaces.RecyclerHistorialListener
import com.google.firebase.database.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentHistorial.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentHistorial.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentHistorial : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    // Variables necesarias para el Fragment
    private val historialList: MutableList<Historial> = mutableListOf()
    private lateinit var adapter: AdaptadorHistorial
    lateinit var ref: DatabaseReference

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
        // Creamos una variable para manejar el recycler en el fragment
        val layoutManager =  LinearLayoutManager(context)

        // Creamos la referencia a la BD filtrando con el id del Usuario en curso
        val ref = FirebaseDatabase.getInstance().getReference("/historial/${Datos.idUsuarioFB}").orderByChild("fecha").equalTo("21 Abril 2019")
        // Creamos el evento para leer desde Firebase
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity, getString(R.string.errorcarga), Toast.LENGTH_SHORT).show()
            }
            // Si se cargan los datos
            override fun onDataChange(p0: DataSnapshot) {
                // Realizamos un ciclo for para poder recorrer todos los subnodos o nodos hijos
                // que dieron como resultado según la referencia hecha al inicio (filtrado)
                for (h in p0.children){
                    // En la siguiente variable almacenaremos el objeto de tipo Historial que
                    // trae todos los datos de Firebase y después se agregan en la Lista Mutable creada anteriormente
                    val historial = h.getValue(Historial::class.java)
                    if (historial != null) {
                        historialList.add(historial)
                    }
                }
                // Llamos al adaptador para hacer el enlace entre la Base de Datos en Firebase y
                // el template de nuestro RecyclerView
                adapter = AdaptadorHistorial(historialList, object : RecyclerHistorialListener {
                    override fun onClick(historial: Historial, position: Int) {
                        Toast.makeText(activity,"Se seleccionó", Toast.LENGTH_SHORT).show()
                    }

                    override fun onLongClick(historial: Historial, position: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
         * @return A new instance of fragment FragmentHistorial.
         */

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
