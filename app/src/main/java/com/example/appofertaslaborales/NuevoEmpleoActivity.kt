package com.example.appofertaslaborales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appofertaslaborales.databinding.ActivityNuevoEmpleoBinding

class NuevoEmpleoActivity : AppCompatActivity() {

    //Atributos

    private lateinit var binding: ActivityNuevoEmpleoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevoEmpleoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}