package com.example.appofertaslaborales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.appofertaslaborales.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Atributos

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Cambiar opciones de registro

        binding.swPersonaEntidad.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(!isChecked) opcsPersona()
            else opcsInstitucion()
        }
    }

    //Funciones para habilitar los botones en el registro de usuario

    private fun opcsPersona() {
        binding.apply {
            etNombres.visibility = View.VISIBLE
            etApellidos.visibility = View.VISIBLE
            etFechaNacimiento.visibility = View.VISIBLE
            etProfesion.visibility = View.VISIBLE
            etNombreIns.visibility = View.INVISIBLE
            etAreaIns.visibility = View.INVISIBLE
        }
    }

    private fun opcsInstitucion() {
        binding.apply {
            etNombres.visibility = View.INVISIBLE
            etApellidos.visibility = View.INVISIBLE
            etFechaNacimiento.visibility = View.INVISIBLE
            etProfesion.visibility = View.INVISIBLE
            etNombreIns.visibility = View.VISIBLE
            etAreaIns.visibility = View.VISIBLE
        }
    }
}