package model

import java.time.LocalDate

data class Parroquia(
    var nombre: String,
    var ubicacion: String,
    var fechaCreacion: LocalDate,
    var esZonaUrbana: Boolean,
    var poblacionEstimada: Int,
    val tiendas: MutableList<Tienda> = mutableListOf()
)