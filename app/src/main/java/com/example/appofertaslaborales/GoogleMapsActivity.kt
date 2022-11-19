package com.example.appofertaslaborales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appofertaslaborales.Clases.Institucion
import com.example.appofertaslaborales.Clases.Persona

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.appofertaslaborales.databinding.ActivityGoogleMapsBinding

class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    //Atributos

    companion object {
        val REQUIERED_PERMISSION_GPS = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_NETWORK_STATE
        )
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGoogleMapsBinding
    private lateinit var persona: Persona
    private lateinit var institucion: Institucion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGoogleMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (intent.getSerializableExtra("obj") is Persona) {
            persona = (intent.getSerializableExtra("obj") as? Persona)!!
            if (persona != null) {
                Toast.makeText(this, persona.email, Toast.LENGTH_SHORT).show()
            }
        }
        else {
            persona = (intent.getSerializableExtra("obj") as? Persona)!!
            if (persona != null) {
                Toast.makeText(this, persona.email, Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}