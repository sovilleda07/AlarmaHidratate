package com.example.alarmahidratate.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.alarmahidratate.Datos

import com.example.alarmahidratate.R
import kotlinx.android.synthetic.main.fragment_fragment_configuracion2.view.*

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
    private var tvUsuario: TextView? = null
    private var tvlibras : TextView? = null

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
        tvUsuario = v.findViewById(R.id.tvUsuario)
        tvlibras = v.findViewById(R.id.tvlibras)

        // Asignar los valores de la clase DatosGenerales
        tvUsuario?.text = Datos.nombre
        tvlibras?.text = Datos.peso.toString()

        v.rbF1.setOnClickListener{
            Toast.makeText(activity,"Alarma establecida", Toast.LENGTH_SHORT).show()
        }
        v.rbF2.setOnClickListener{
            Toast.makeText(activity,"Alarma establecida", Toast.LENGTH_SHORT).show()
        }

        return v
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
