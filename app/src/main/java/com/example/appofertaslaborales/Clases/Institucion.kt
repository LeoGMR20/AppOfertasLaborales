package com.example.appofertaslaborales.Clases

import java.io.Serializable

class Institucion(
    email: String,
    password: String,
    nombre: String,
    area: String,
    numCelular: String
): Usuario(), Serializable {
    //Getters

    var nombre: String = ""
        get() { return nombre }
    var area: String = ""
        get() { return area }

    //Herencia

    override var email: String = ""
    override var password: String = ""
    override var numCelular: String = ""

    init {
        this.nombre = nombre
        this.area = area
        this.email = email
        this.password = password
        this.numCelular = numCelular
    }
}