package service

import model.Parroquia
import model.Tienda
import repository.ArchivoManager

/**
 * Clase de servicio que gestiona las operaciones de negocio
 * relacionadas con Parroquias y Tiendas.
 *
 * Responsabilidades:
 * - Gestionar el ciclo de vida de parroquias y tiendas
 * - Realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * - Coordinar la persistencia de datos a través de ArchivoManager
 */

class ParroquiaTiendaManager {
    // Lista interna de parroquias gestionadas por el sistema
    private val parroquias = mutableListOf<Parroquia>()

    init {
        cargarDatosExistentes()
    }

    //Carga los datos existentes desde el almacenamiento persistente.
    //Se ejecuta automáticamente al inicializar el gestor.
    private fun cargarDatosExistentes() {
        parroquias.clear()
        val datosExistentes = ArchivoManager.cargarDatos()

        if (datosExistentes.isNotEmpty()) {
            parroquias.addAll(datosExistentes)
        }
    }

    fun crearParroquia(parroquia: Parroquia) {
        parroquias.add(parroquia)
        guardarCambios()
    }

    fun crearTienda(nombreParroquia: String, tienda: Tienda) {
        val parroquia = parroquias.find { it.nombre.uppercase() == nombreParroquia.uppercase() }
        parroquia?.tiendas?.add(tienda)
        guardarCambios()
    }

    fun listarParroquias(): List<Parroquia> = parroquias

    fun listarTiendasPorParroquia(nombreParroquia: String): List<Tienda> {
        return parroquias.find { it.nombre.uppercase() == nombreParroquia.uppercase() }?.tiendas ?: emptyList()
    }

    fun actualizarParroquia(nombreOriginal: String, parroquiaActualizada: Parroquia) {
        val indice = parroquias.indexOfFirst { it.nombre.uppercase() == nombreOriginal.uppercase() }
        if (indice != -1) {
            // Preservar las tiendas existentes
            parroquiaActualizada.tiendas.addAll(parroquias[indice].tiendas)
            parroquias[indice] = parroquiaActualizada
            guardarCambios()
        }
    }

    fun actualizarTienda(nombreParroquia: String, nombreTiendaOriginal: String, tiendaActualizada: Tienda) {
        val parroquia = parroquias.find { it.nombre.uppercase() == nombreParroquia.uppercase() }
        val indice = parroquia?.tiendas?.indexOfFirst { it.nombre.uppercase() == nombreTiendaOriginal.uppercase() }

        if (parroquia != null && indice != null && indice != -1) {
            parroquia.tiendas[indice] = tiendaActualizada
            guardarCambios()
        }
    }

    fun eliminarParroquia(nombreParroquia: String) {
        parroquias.removeIf { it.nombre.uppercase() == nombreParroquia.uppercase() }
        guardarCambios()
    }

    fun eliminarTienda(nombreParroquia: String, nombreTienda: String) {
        val parroquia = parroquias.find { it.nombre.uppercase() == nombreParroquia.uppercase() }
        parroquia?.tiendas?.removeIf { it.nombre.uppercase() == nombreTienda.uppercase() }
        guardarCambios()
    }

    private fun guardarCambios() {
        ArchivoManager.guardarDatos(parroquias)
    }
}