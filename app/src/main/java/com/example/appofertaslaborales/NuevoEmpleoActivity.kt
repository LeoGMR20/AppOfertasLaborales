package com.example.appofertaslaborales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appofertaslaborales.Clases.Empleo
import com.example.appofertaslaborales.Clases.Institucion
import com.example.appofertaslaborales.Constantes.lapaz
import com.example.appofertaslaborales.databinding.ActivityNuevoEmpleoBinding
import com.google.android.gms.maps.model.LatLng

class NuevoEmpleoActivity : AppCompatActivity() {

    //Atributos

    private lateinit var binding: ActivityNuevoEmpleoBinding
    private lateinit var institucion3: Institucion
    private lateinit var insM: Institucion
    private var lati: Double = 0.0
    private var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevoEmpleoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        institucion3 = (intent.getSerializableExtra("ins2") as? Institucion)!!
        lati = intent.getDoubleExtra("lat",lapaz.latitude)
        long = intent.getDoubleExtra("long",lapaz.longitude)

        binding.btnNuevoEmpleo.setOnClickListener {
            registrarNuevoEmpleo()
        }
    }

    private fun registrarNuevoEmpleo() {
        if (!(binding.etDescripcion.text.isEmpty() || binding.etTitulo.text.isEmpty())) {
            val intent3 = Intent(this, GoogleMapsActivity::class.java)
            intent3.putExtra("user",2)
            intent3.putExtra("obj",institucion3)
            intent3.putExtra("tit",binding.etTitulo.text.toString())
            intent3.putExtra("des",binding.etDescripcion.text.toString())
            intent3.putExtra("lat2",lati)
            intent3.putExtra("long2",long)
            startActivity(intent3)
        }
        else Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_LONG).show()
    }
}