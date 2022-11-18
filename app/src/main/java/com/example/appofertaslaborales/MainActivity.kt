package com.example.appofertaslaborales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appofertaslaborales.Clases.Persona
import com.example.appofertaslaborales.databinding.ActivityMainBinding
import java.io.Serializable
import java.time.LocalDate

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

        //Botón de registro

        binding.btnRegistrar.setOnClickListener {
            validaciones()
        }
    }

    private fun validaciones() {
        if(!binding.swPersonaEntidad.isChecked) {
            if (validarCamposVacios(false)) {
                if(verificarPassword()) registrarPersona()
                else alerta("Ingrese la misma contraseña en los campos pedidos")
            }
            else alerta("Ingrese todos los campos")
        }
        else {
            if (validarCamposVacios(true)) {
                if(verificarPassword()) registrarInstitucion()
                else alerta("Ingrese la misma contraseña en los campos pedidos")
            }
            else alerta("Ingrese todos los campos")
        }
    }

    //Función de alerta de campo vacio

    private fun alerta(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show() }

    //Función para validar los campos vacíos

    private fun validarCamposVacios(tipoUsuario: Boolean): Boolean {
        if(
            binding.etEmail.text.isEmpty() ||
            binding.etPassword.text.isEmpty() ||
            binding.etConfirmarPassword.text.isEmpty() ||
            binding.etCelular.text.isEmpty()
        ) return false

        if(!tipoUsuario) {
            if(
                binding.etNombres.text.isEmpty() ||
                binding.etApellidos.text.isEmpty() ||
                binding.etFechaNacimiento.text.isEmpty() ||
                binding.etProfesion.text.isEmpty()
            ) return false
        }
        else {
            if (
                binding.etNombreIns.text.isEmpty() ||
                binding.etAreaIns.text.isEmpty()
            ) return false
        }
        return true
    }

    //Función verificar contraseña

    private fun verificarPassword(): Boolean {
        binding.apply {
            if(etPassword.text.equals(etConfirmarPassword.text)) return true
        }
        return false
    }

    //Función para registrar persona

    private fun registrarPersona() {
        val persona: Persona
        binding.apply {
            persona = Persona(
                etEmail.text.toString(),
                etPassword.text.toString(),
                etNombres.text.toString(),
                etApellidos.text.toString(),
                LocalDate.parse(etFechaNacimiento.text.toString()),
                etProfesion.text.toString(),
                etCelular.text.toString()
            )
        }
        val intent = Intent(this, GoogleMapsActivity::class.java)
        intent.putExtra("obj",persona as Serializable)
        startActivity(intent)
    }

    //Función para registrar institución

    private fun registrarInstitucion() {

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