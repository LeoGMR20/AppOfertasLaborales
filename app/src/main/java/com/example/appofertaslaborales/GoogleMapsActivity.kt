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
import com.example.appofertaslaborales.Clases.Empleo
import com.example.appofertaslaborales.Clases.Institucion
import com.example.appofertaslaborales.Clases.Persona
import com.example.appofertaslaborales.Constantes.empleo1
import com.example.appofertaslaborales.Constantes.empleo2
import com.example.appofertaslaborales.Constantes.empleo3
import com.example.appofertaslaborales.Constantes.lapaz

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.appofertaslaborales.databinding.ActivityGoogleMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker

class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener{

    private var isGPSEnabled = false
    private val PERMISSION_ID = 42
    private lateinit var fusedLocation : FusedLocationProviderClient

    private var latitud: Double = 0.0
    private var longitud: Double = 0.0

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGoogleMapsBinding
    private lateinit var persona: Persona
    private lateinit var institucion4: Institucion
    private lateinit var tit: String
    private lateinit var des: String
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private var lat2: Double = 0.0
    private var long2: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGoogleMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        enableGPSServices()
        manageLocation()

        if (intent.getIntExtra("user",0) == 1) {
            persona = (intent.getSerializableExtra("obj") as? Persona)!!
        }
        else if(intent.getIntExtra("user",0) == 2){
            institucion4 = (intent.getSerializableExtra("obj") as? Institucion)!!
            lat = intent.getDoubleExtra("lat2",lapaz.latitude)
            long = intent.getDoubleExtra("long2",lapaz.longitude)
            tit = intent.getStringExtra("tit").toString()
            des = intent.getStringExtra("des").toString()
            //setMarkerEmpleo1()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(this))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(lapaz))

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true // Botones + - zoom in zoom out
            isCompassEnabled = true // la brújula de orientación del mapa
            isMapToolbarEnabled = true // habilito para un marcador la opción de ir a ver una ruta a verlo en la app Mapa Google
            isRotateGesturesEnabled = false // deshabilitar la opción de rotación del mapa
            isTiltGesturesEnabled = false // deshabilitar la opción de rotación de la cámara
            isZoomControlsEnabled = false // deshabilita las opciones de zoom con los dedos del mapa
        }

        mMap.setPadding(0,0,0,Utils.dp(64))

        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener {
            //it es la posición donde haces click con tu dedo
            mMap.addMarker(MarkerOptions()
                .title("Nueva ubicación Random")
                .snippet("${it.latitude},\n${it.longitude}")
                .position(it)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
            )
        }
        mMap.addMarker(MarkerOptions()
            .title(empleo1.titulo)
            .snippet("")
            .position(LatLng(-16.51065389615145,-68.1280593211182))
            .draggable(false)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
        )
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //marker es el marcador al que le estas haciendo click
        Toast.makeText(this, "${marker.position.latitude}, ${marker.position.longitude}", Toast.LENGTH_LONG).show()
        return false
    }

    private fun setMarkerEmpleo1() {
        mMap.addMarker(MarkerOptions()
            .title(tit)
            .snippet(des)
            .position(LatLng(lat,long))
            .draggable(false)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
        )
    }

    private fun setMarkerEmpleo2(emp: Empleo) {
        lat2 = emp.institucion.ubicacion!!.latitude
        long2 = emp.institucion.ubicacion!!.longitude
        mMap.addMarker(MarkerOptions()
            .title(emp.titulo)
            .snippet("""
                Empresa: ${emp.institucion.nombre}
                Descripción: ${emp.Descripcion}
            """.trimIndent())
            .position(LatLng(lat2, long2))
            .draggable(false)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
        )
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
        return UbicacionInstitucionMapsActivity.REQUIERED_PERMISSION_GPS.all {
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
                //solo puede ser tratado si el usuario dio permisos
                fusedLocation = LocationServices.getFusedLocationProviderClient(this)
                //Estan configurando un evento que escuche cuando
                // del sensor GPS se captura datos correctamente
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
            Constantes.INTERVAL_TIME
        ).setMaxUpdates(1).build()
        fusedLocation.requestLocationUpdates(myLocationRequest, myLocationCallback, Looper.myLooper())
    }

    private fun requestPermissionsLocation() {
        ActivityCompat.requestPermissions(this,
            UbicacionInstitucionMapsActivity.REQUIERED_PERMISSION_GPS, PERMISSION_ID)
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