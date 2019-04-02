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
import android.widget.Button
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.TextView
import android.widget.Toast

import com.example.alarmahidratate.R
import kotlinx.android.synthetic.main.fragment_fragment_contenedores.*
import kotlinx.android.synthetic.main.fragment_fragment_contenedores.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentContenedores.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentContenedores.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentContenedores : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var number: TextView? = null
    var numberPicker: Button? = null

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

        val v =inflater.inflate(R.layout.fragment_fragment_contenedores, container, false)

        v.bCambiarVaso.setOnClickListener{
            /*number = v.findViewById(R.id.tvnumero) as TextView
            numberPicker = v.findViewById(R.id.bCambiarVaso) as Button
            numberPicker!!.setOnClickListener{
               // numberPickerDialog()
                //Toast.makeText(activity,"presionado", Toast.LENGTH_SHORT).show()
            }*/
            Toast.makeText(activity,"Tamaño modificado", Toast.LENGTH_SHORT).show()
        }
        v.bCambiarTaza.setOnClickListener{ view ->
            Toast.makeText(activity,"Tamaño modificado", Toast.LENGTH_SHORT).show()
        }
        v.bCambiarBotella.setOnClickListener{view ->
            Toast.makeText(activity,"Tamaño modificado", Toast.LENGTH_SHORT).show()
        }
        return v


    }

    /*fun numberPickerDialog(){
        var myNumberPicker: NumberPicker? = null
        myNumberPicker!!.maxValue = 10
        myNumberPicker!!.minValue = 1
        myNumberPicker.wrapSelectorWheel = true

        var myValChangedListener: OnValueChangeListener? = null
        myNumberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            number?.setText(""+newVal)
        }
        myNumberPicker.setOnValueChangedListener(myValChangedListener)

        val builder = AlertDialog.Builder(activity).setView(myNumberPicker)
        builder.setTitle(getString(R.string.cantidad))
        //builder.setPositiveButton(getString(R.string.modificar),DialogInterface.OnClickListener())

        builder.setPositiveButton(getString(R.string.modificar)) { dialog, which ->
            Toast.makeText(activity,"Si funciona", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(getString(R.string.cancelar)) { dialog, which ->
            Toast.makeText(activity,"NO funciona", Toast.LENGTH_SHORT).show()
        }
        //val dialog: AlertDialog = builder.create()

        // Mostrar el AlertDialog
        builder.show()
    }*/

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
