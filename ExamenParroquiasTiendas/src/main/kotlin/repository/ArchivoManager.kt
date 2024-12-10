package repository

import model.Parroquia
import model.Tienda
import java.io.File
import java.time.LocalDate

/**
 * Objeto responsable de la gestión de persistencia de datos.
 * Maneja la serialización y deserialización de información de parroquias y tiendas
 * en un archivo de texto plano.
 *
 * Funcionalidades:
 * - Guardar datos de parroquias y tiendas en un archivo
 * - Cargar datos previamente guardados
 * - Manejar la relación entre parroquias y tiendas durante la serialización
 */

object ArchivoManager {

    // Constante que define el nombre del archivo de persistencia
    private const val ARCHIVO_DATOS = "parroquias_tiendas.txt"

    //Guarda los datos de todas las parroquias en un archivo de texto.
    //Serializa la información de parroquias y sus tiendas.
    fun guardarDatos(parroquias: List<Parroquia>) {
        File(ARCHIVO_DATOS).bufferedWriter().use { writer ->
            parroquias.forEach { parroquia ->
                writer.write("PARROQUIA|${parroquia.nombre}|${parroquia.ubicacion}|" +
                        "${parroquia.fechaCreacion}|${parroquia.esZonaUrbana}|${parroquia.poblacionEstimada}\n")

                parroquia.tiendas.forEach { tienda ->
                    writer.write("TIENDA|${tienda.nombre}|${tienda.direccion}|" +
                            "${tienda.fechaFundacion}|${tienda.estaActiva}|${tienda.ventasMensuales}|" +
                            "${parroquia.nombre}\n")
                }
            }
        }
    }

    //Carga los datos de parroquias y tiendas desde un archivo de texto.
    //Reconstruye las relaciones entre parroquias y tiendas.
    fun cargarDatos(): MutableList<Parroquia> {
        val parroquias = mutableListOf<Parroquia>()
        val parroquiasMapa = mutableMapOf<String, Parroquia>()

        val archivo = File(ARCHIVO_DATOS)

        if (!archivo.exists()) {
            return parroquias
        }

        archivo.bufferedReader().use { reader ->
            reader.forEachLine { linea ->
                val datos = linea.split("|")
                when (datos[0]) {
                    "PARROQUIA" -> {
                        val parroquia = Parroquia(
                            nombre = datos[1],
                            ubicacion = datos[2],
                            fechaCreacion = LocalDate.parse(datos[3]),
                            esZonaUrbana = datos[4].toBoolean(),
                            poblacionEstimada = datos[5].toInt()
                        )
                        parroquias.add(parroquia)
                        parroquiasMapa[parroquia.nombre] = parroquia
                    }
                    "TIENDA" -> {
                        val parroquia = parroquiasMapa[datos[6]]!!
                        val tienda = Tienda(
                            nombre = datos[1],
                            direccion = datos[2],
                            fechaFundacion = LocalDate.parse(datos[3]),
                            estaActiva = datos[4].toBoolean(),
                            ventasMensuales = datos[5].toDouble(),
                            parroquia = parroquia
                        )
                        parroquia.tiendas.add(tienda)
                    }
                }
            }
        }
        return parroquias
    }
}