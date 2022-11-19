package com.example.appofertaslaborales

import com.example.appofertaslaborales.Clases.Empleo
import com.example.appofertaslaborales.Clases.Institucion
import com.google.android.gms.maps.model.LatLng

object Constantes {
    val lapaz = LatLng(-16.48892110589471, -68.11743375119276)
    const val INTERVAL_TIME = 5000L
    val empleo1 = Empleo(
        Institucion(
            "abc@gmail.com",
            "asd123",
            "Insti S.A",
            "Auditoria",
            "78454112",
            LatLng(-16.503926343777003, -68.1306991207844)
        ),
        "Auxiliar de sistemas",
        """Ingeniero en sistemas egresado
        Conocimientos en: Linux, MongoDB
        
        """.trimMargin(),
    )
    val empleo2 = Empleo(
        Institucion(
            "bvc@gmail.com",
            "asd645",
            "DevInc",
            "Desarrollo de software",
            "78454744",
            LatLng(-16.51065389615145, -68.1280593211182)
        ),
        "Auxiliar de front end developer",
        """Ingeniero en sistemas con 2 años de experiencia
        Conocimientos en: Html, CSS, Js
        Salario: 500$ al mes
        
        """.trimMargin()
    )
    val empleo3 = Empleo(
        Institucion(
            "gfd@gmail.com",
            "asd456",
            "Mercadil",
            "Exportaciones",
            "78454112",
            LatLng(-16.508596465663356, -68.13526341956207)
        ),
        "Diseñador gráfico",
        """Diseñador gráfico con 2 años de experiencia
        Salario: 1000$ al mes
        
        """.trimMargin()
    )
}