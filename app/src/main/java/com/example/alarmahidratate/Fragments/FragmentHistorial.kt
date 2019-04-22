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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_fragment_historial.*

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

    private lateinit var v : View

    private lateinit var elrv: RecyclerView

    private val historialList: ArrayList<Historial> = ArrayList()
    private lateinit var adapter: AdaptadorHistorial

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
        v = inflater.inflate(R.layout.fragment_fragment_historial, container, false)

        val layoutManager = LinearLayoutManager(activity)
        adapter = AdaptadorHistorial(historialList, object : RecyclerHistorialListener {
            override fun onClick(historial: Historial, position: Int) {
                Toast.makeText(activity, "${historial.consumo}", Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(historial: Historial, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        elrv = v.findViewById(R.id.rvHistorial)


        elrv.setHasFixedSize(true)
        elrv.layoutManager = layoutManager
        elrv.itemAnimator = DefaultItemAnimator()
        elrv.adapter = adapter

        cargarHistorial()

        return v
    }
    // Función para cargar el historial de consumo del usuario actual
    private fun cargarHistorial() {
        // Hacemos referencia al nodo que contiene todos los consumos del usuario
        val refHistorial = FirebaseDatabase.getInstance().getReference("/historial/${Datos.idUsuarioFB}")
        refHistorial.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                // Creamos una variable de tipo Historial para traer toda la informacion
                val historial = p0.getValue(Historial::class.java)
                if (historial != null) {
                    historialList.add(historial)
                }


            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
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
