package com.example.appofertaslaborales.Clases

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

class Institucion(
    email: String,
    password: String,
    nombre: String,
    area: String,
    numCelular: String
): Usuario(), Serializable {
    //Getters y setters

    var nombre: String = ""
        get() { return nombre }
    var area: String = ""
        get() { return area }
    var ubicacion: LatLng? = null
        get() { return ubicacion}
        set(value) { field = value }

    //Herencia

    override var email: String = ""
    override var password: String = ""
    override var numCelular: String = ""

    //Constructor secundario

    constructor(
        email: String,
        password: String,
        nombre: String,
        area: String,
        numCelular: String,
        ubicacion: LatLng
    ): this( email, password, nombre, area, numCelular ) {
        this.ubicacion = ubicacion
    }

    init {
        this.nombre = nombre
        this.area = area
        this.email = email
        this.password = password
        this.numCelular = numCelular
    }
}