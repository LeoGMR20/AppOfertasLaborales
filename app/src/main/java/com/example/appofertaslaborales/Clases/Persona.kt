package com.example.appofertaslaborales.Clases

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calcularEdad(): Int = LocalDate.now().year - this.fechaNacimiento.year
}