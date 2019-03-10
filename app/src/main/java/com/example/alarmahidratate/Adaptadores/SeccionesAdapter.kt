package com.example.alarmahidratate.Adaptadores

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class SeccionesAdapter (fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private val listaFragments = ArrayList<Fragment>()
    private val listaTitulos = ArrayList<String>()

    //Construir lista de Fragmentos y Títulos
    fun addFragment(fragment:Fragment, titulo : String) {
        listaFragments.add(fragment)
        listaTitulos.add(titulo)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listaTitulos[position]
    }

    override fun getItem(position: Int): Fragment {
        return listaFragments[position]
    }

    override fun getCount(): Int {
        return listaFragments.size
    }





}

