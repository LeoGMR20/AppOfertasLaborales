package com.example.appofertaslaborales.Clases

import com.google.android.gms.maps.model.LatLng

class Institucion(
    nombre: String,
    area: String,
    numCelular: String
): Usuario() {
    override var email: String = ""
    override var password: String = ""
    override var numCelular: String = ""

    init {
        this.email = email
        this.password = password
        this.numCelular = numCelular
    }
}