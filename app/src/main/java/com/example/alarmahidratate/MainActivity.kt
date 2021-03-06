package com.example.alarmahidratate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.alarmahidratate.Fragments.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
//import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, FragmentConfiguracion.OnFragmentInteractionListener, FragmentContenedores.OnFragmentInteractionListener, FragmentInformacion.OnFragmentInteractionListener, FragmentTabs.OnFragmentInteractionListener, FragmentHistorial.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        // Cargamos el fragmente inicial
        val fragmento : Fragment = FragmentTabs()
        supportFragmentManager.beginTransaction().add(R.id.content_main,fragmento).commit()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // Función para seleccionar los diferentes items que aparecen
    // en la barra lateral
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {
            R.id.nav_inicio -> {
                /*Cargar los fragments en el MainActivity*/
                supportFragmentManager.beginTransaction().replace(R.id.content_main, FragmentTabs()).commit()
            }
            R.id.nav_contenedor -> {
                /*Cargar los fragments en el MainActivity*/
                supportFragmentManager.beginTransaction().replace(R.id.content_main, FragmentContenedores()).commit()
            }
            R.id.nav_historial -> {
                /*Cargar los fragments en el MainActivity*/
                supportFragmentManager.beginTransaction().replace(R.id.content_main, FragmentHistorial()).commit()
            }
            R.id.nav_configuration -> {
                /*Cargar los fragments en el MainActivity*/
                supportFragmentManager.beginTransaction().replace(R.id.content_main, FragmentConfiguracion()).commit()
            }
            R.id.nav_information -> {
                /*Cargar los fragments en el MainActivity*/
                supportFragmentManager.beginTransaction().replace(R.id.content_main, FragmentInformacion()).commit()
            }
            R.id.nav_cerrarsesion -> {
                // Para cerrar Sesión
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
