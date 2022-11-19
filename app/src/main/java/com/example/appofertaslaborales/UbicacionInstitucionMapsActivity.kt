package com.example.appofertaslaborales

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.appofertaslaborales.Clases.Institucion
import com.example.appofertaslaborales.Constantes.INTERVAL_TIME
import com.example.appofertaslaborales.Constantes.lapaz

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.appofertaslaborales.databinding.ActivityUbicacionInstitucionMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*

class UbicacionInstitucionMapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener{

    companion object {
        val REQUIERED_PERMISSION_GPS = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_NETWORK_STATE
        )
    }

    private var isGPSEnabled = false
    private val PERMISSION_ID = 42
    private lateinit var fusedLocation : FusedLocationProviderClient

    private var latitud: Double = 0.0
    private var longitud: Double = 0.0

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityUbicacionInstitucionMapsBinding
    private lateinit var institucion2: Institucion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbicacionInstitucionMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        institucion2 = (intent.getSerializableExtra("ins") as? Institucion)!!

        enableGPSServices()
        manageLocation()

        Toast.makeText(this, "Pulse el lugar donde se encuentra la institución", Toast.LENGTH_LONG).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(this))

        val sydney = LatLng(-34.0, 151.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true // Botones + - zoom in zoom out
            isCompassEnabled = true // la brújula de orientación del mapa
            isMapToolbarEnabled = true // habilito para un marcador la opción de ir a ver una ruta a verlo en la app Mapa Google
            isRotateGesturesEnabled = false // deshabilitar la opción de rotación del mapa
            isTiltGesturesEnabled = false // deshabilitar la opción de rotación de la cámara
            isZoomControlsEnabled = false // deshabilita las opciones de zoom con los dedos del mapa
        }

        mMap.setPadding(0,0,0,Utils.dp(64))

        //mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.my_map_style))

        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener {
            institucion2.ubicacion = LatLng(it.latitude,it.longitude)
            nuevoEmpleo()
        }
    }

    private fun nuevoEmpleo() {
        val intent2 = Intent(this, NuevoEmpleoActivity::class.java)
        intent2.putExtra("ins2",institucion2)
        startActivity(intent2)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //Toast.makeText(this, "${marker.position.latitude}, ${marker.position.longitude}", Toast.LENGTH_SHORT).show()
        return false
    }

    //HABILITAR PERMISOS DE GPS Y COORDENADAS

    /**
     *
     *
     * GPS
     *
     *
     */

    private fun enableGPSServices() {
        if(!hasGPSEnabed()){
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_description)
                .setPositiveButton(
                    R.string.alert_dialog_button_accept,
                    DialogInterface.OnClickListener{
                            dialog, wich -> goToEnableGPS()
                    })
                .setNegativeButton(R.string.alert_dialog_button_denny) {
                        dialog, wich -> isGPSEnabled = false
                }
                .setCancelable(true)
                .show()
        } else
            Toast.makeText(this,"Ya tienes el GPS habilitado", Toast.LENGTH_SHORT).show()
    }

    private fun hasGPSEnabed(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun goToEnableGPS() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    //Seccion: tratamiento de permisos para el uso del GPS
    private fun allPermissionsGrantedGPS(): Boolean {
        return REQUIERED_PERMISSION_GPS.all {
            ContextCompat.checkSelfPermission( baseContext, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     *
     *
     * Coordenadas
     *
     *
     * */

    @SuppressLint("MissingPermission")
    private fun manageLocation() {
        if (hasGPSEnabed()){
            if (allPermissionsGrantedGPS()) {
                fusedLocation = LocationServices.getFusedLocationProviderClient(this)
                fusedLocation.lastLocation.addOnSuccessListener {
                        location -> requestNewLocationData()
                }
            }else{
                requestPermissionsLocation()
            }
        }else{
            goToEnableGPS()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var myLocationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            INTERVAL_TIME
        ).setMaxUpdates(1).build()
        fusedLocation.requestLocationUpdates(myLocationRequest, myLocationCallback, Looper.myLooper())
    }

    private fun requestPermissionsLocation() {
        ActivityCompat.requestPermissions(this, REQUIERED_PERMISSION_GPS, PERMISSION_ID)
    }

    private val myLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var myLastLocation: Location? = locationResult.lastLocation
            if(myLastLocation != null) {
                latitud = myLastLocation.latitude
                longitud = myLastLocation.longitude
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitud,longitud),15f))
            }
        }
    }
}