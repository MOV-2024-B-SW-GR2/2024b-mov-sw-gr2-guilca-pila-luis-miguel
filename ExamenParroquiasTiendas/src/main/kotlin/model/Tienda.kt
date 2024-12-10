package model

import java.time.LocalDate

data class Tienda(
    var nombre: String,
    var direccion: String,
    var fechaFundacion: LocalDate,
    var estaActiva: Boolean,
    var ventasMensuales: Double,
    val parroquia: Parroquia
)