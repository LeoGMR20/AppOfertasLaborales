package com.example.appofertaslaborales.Clases

import java.time.LocalDate

class Persona(
    email: String,
    password: String,
    nombres: String,
    apellidos: String,
    fechaNacimiento: LocalDate,
    profesion: String,
    numCelular: String
): Usuario() {
    val fechaNacimiento: LocalDate
    override var email: String = ""
    override var password: String = ""
    override var numCelular: String = ""
    var edad: Int? = null

    init {
        this.email = email
        this.password = password
        this.fechaNacimiento = fechaNacimiento
        this.numCelular = numCelular
        edad = calcularEdad()
    }

    private fun calcularEdad(): Int = LocalDate.now().year - this.fechaNacimiento.year
}