package com.example.appofertaslaborales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appofertaslaborales.Clases.Institucion
import com.example.appofertaslaborales.databinding.ActivityNuevoEmpleoBinding

class NuevoEmpleoActivity : AppCompatActivity() {

    //Atributos

    private lateinit var binding: ActivityNuevoEmpleoBinding
    private lateinit var institucion3: Institucion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevoEmpleoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        institucion3 = (intent.getSerializableExtra("ins2") as? Institucion)!!

        binding.btnNuevoEmpleo.setOnClickListener {
            registrarNuevoEmpleo()
        }
    }

    private fun registrarNuevoEmpleo() {
        TODO("Not yet implemented")
    }
}