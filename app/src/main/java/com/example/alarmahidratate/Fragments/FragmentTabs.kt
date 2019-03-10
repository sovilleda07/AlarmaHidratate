package com.example.alarmahidratate.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alarmahidratate.Adaptadores.SeccionesAdapter

import com.example.alarmahidratate.R

// TODO: Rename parameter arguments, choose names that match
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

    private var listener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val vista = inflater.inflate(R.layout.fragment_fragment_tabs, container, false)

        var contenedor = container!!.parent as View
        appBar = contenedor.findViewById(R.id.appBar) as AppBarLayout
        tabs = TabLayout(activity)
        appBar.addView(tabs)

        viewPager = view!!.findViewById(R.id.pager) as ViewPager
        llenarViewPager(viewPager)
        tabs.setupWithViewPager(viewPager)

        return vista

/*
            // Gesto de arrastrar la pantalla
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(p0: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onPageSelected(p0: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

            })*/

    }

    override fun onDestroyView() {
        super.onDestroyView()
        appBar.removeView(tabs)
    }

    private fun llenarViewPager(viewPager: ViewPager) {
       val adapter = SeccionesAdapter(fragmentManager)
        adapter.addFragment(FragmentVaso(),"VASO")
        adapter.addFragment(FragmentTaza(),"TAZA")
        adapter.addFragment(FragmentBotella(),"BOTELLA")

        viewPager.adapter = adapter
    }


    companion object {
        lateinit var tabs: TabLayout
        lateinit var appBar : AppBarLayout
        lateinit var viewPager : ViewPager
        //var vista: View? = null


    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener") as Throwable
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
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


}

