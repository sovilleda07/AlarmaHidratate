package com.example.alarmahidratate

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.alarmahidratate.Datos.Companion.consumoAgua
import com.example.alarmahidratate.Datos.Companion.idUsuarioFB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_datos_generales.*

class DatosGenerales : AppCompatActivity() {

    // Inicialización de variables que manejarán el layout
    private var tvEmail: TextView? = null
    private var tvPassword: TextView? = null
    private var tvNombre: TextView? = null
    private var tvPeso: TextView? = null
    private var rgGenero: RadioGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_generales)

        // Mapear las variables a las vistas del layout
        tvEmail = findViewById(R.id.etCorreo)
        tvPassword = findViewById(R.id.etContrasena)
        tvNombre = findViewById(R.id.etNombre)
        tvPeso = findViewById(R.id.etPeso)
        rgGenero = findViewById(R.id.rgGenero)

        // Cuando se le da click al botón aceptar al ingresar los datos
        bAceptar.setOnClickListener{
            // Llamamos a la función que valida los datos e ingresa el usuario
            registrar()
        }

    }

    //  Funcion para validar el ingreso de todos lo datos y registrar al usuario
    private fun registrar(){
        // Validar que se introduzcan campos llenos
        if (etCorreo.text.toString().isEmpty() || etCorreo.text.toString().isBlank()|| etContrasena.text.toString().isEmpty() || etContrasena.text.toString().isBlank() || etNombre.text.toString().isEmpty() || etNombre.text.toString().isBlank()|| etPeso.text.toString().isEmpty() || etPeso.text.toString().isBlank() || etPeso.text.toString().toDouble() <= 0 || !(rbFemenino.isChecked || rbMasculino.isChecked)) {
            Toast.makeText(this, getString(R.string.errorDatos),Toast.LENGTH_SHORT).show()
        }else{

            // Variable para almacenar almacenar los valores de los layouts
            val correo= etCorreo.text.toString().trim()
            val contrasena= etContrasena.text.toString().trim()
            val nombreUsuario = etNombre.text.toString().trim()
            val peso= etPeso.text.toString().toDouble()
            var genero = ""

            // Evaluación de la selección de RadioButton
            if (rbFemenino.isChecked) {
                genero = "Femenino"
            } else if (rbMasculino.isChecked){
                genero = "Masculino"}

            /* Implementación de Firebase
               Creando una instancia de autenticación con el correo y la contraseña
               Obteniendo los valores de las variables correo y contraseña
            */

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, contrasena)
            // Si el proceso de autenticación no es completado con éxito, devolver al usuario para que lo complete
                .addOnCompleteListener {
                    if (!it.isSuccessful){
                        tvEmail?.text = ""
                        tvPassword?.text = ""
                        tvNombre?.text = ""
                        tvPeso?.text = ""
                        rgGenero?.clearCheck()

                        Toast.makeText(this, getString(R.string.errorRegistro), Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }else {
                        // si es completado con éxito
                        Toast.makeText(this, getString(R.string.cuentaCreada),Toast.LENGTH_SHORT).show()
                        guardarUsuario(nombreUsuario, peso, genero)
                    }
                }
        }
    }

    // Función para insertar el usuario y sus contenedores en Firebase
    private fun guardarUsuario(nombreUsuario: String, peso: Double, genero: String){
        // variable para almacenar el id del usuario actual
        idUsuarioFB = FirebaseAuth.getInstance().uid ?: ""

        // variable para almacenar el resultado de la función
        // que calcula cuanta agua se debe tomar
        val agua = consumoAgua(peso,genero)

        // Referencia para insertar usuarios
        val referenciaBaseDatos = FirebaseDatabase.getInstance().getReference("/usuarios/$idUsuarioFB")

        // Variable de tipo Datos que almacenará toda la información a guardar en la BD
        val usuario = Datos(idUsuarioFB, nombreUsuario,peso,genero, agua, 0)

        // Insertamos en Firebase
        referenciaBaseDatos.setValue(usuario)
            .addOnSuccessListener {
                //Toast.makeText(this, "Se ha guardado con exito el usuario", Toast.LENGTH_SHORT).show()

                // Guardaremos los contenedores para el usuario recien creado
                guardarContenedores(idUsuarioFB)

                // Pasaremos al menú principal
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "falló  ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }

    // Función para almacenar los contenedores del usuario recién creado
    private fun guardarContenedores(idUsuario: String) {
        // Creamos la referencia al nodo de Contenedores de nuestra BD
        val referenciaBDContenedor = FirebaseDatabase.getInstance().getReference("contenedores")

        // Creamos una lista de tipo Contenedor para almacenar los 3 tipos de contenedores
        // que tendrá el usuario y almacenarlos al mismo tiempo
        val contenedoresUsuario: List<Contenedor> = listOf(
            Contenedor("Vaso", 100, idUsuario),
            Contenedor("Taza", 150, idUsuario),
            Contenedor("Botella", 200, idUsuario)
        )

        // Insertaremos cada uno de los elementos en la lista
        /* Por medio de un ciclo For recorremos nuestra lista de contenedores
            para poder obtener el nombre de cada uno de ellos, para poder crear un Key
            personalizado, el cuál se realiza por medio de la función de Firebase .child()
            donde se establece un patrón, en este caso el Key estará compuesta por
            el nombre del contenedor - el id del usuario actual
        * */
        for ((indice, item) in contenedoresUsuario.withIndex()){
            referenciaBDContenedor.child("${contenedoresUsuario[indice].nombre}-${idUsuario}").setValue(item)
                .addOnSuccessListener {
                    //Toast.makeText(this, "Se ha guardado el contenedor", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar contenedor", Toast.LENGTH_SHORT).show()
                }
        }

    }

}