package com.example.appofertaslaborales.Clases

import java.io.Serializable

class Persona(
    email: String,
    password: String,
    nombres: String,
    apellidos: String,
    fechaNacimiento: String,
    profesion: String,
    numCelular: String
): Usuario(), Serializable {
    //Getters

    var nombres: String = ""
        get() { return nombres }
    var apellidos: String = ""
        get() { return apellidos }
    var profesion: String = ""
        get() { return profesion }
    var fechaNacimiento: String

    //Herencia

    override var email: String = ""
    override var password: String = ""
    override var numCelular: String = ""

    init {
        this.nombres = nombres
        this.apellidos = apellidos
        this.profesion = profesion
        this.email = email
        this.password = password
        this.fechaNacimiento = fechaNacimiento
        this.numCelular = numCelular
    }
}