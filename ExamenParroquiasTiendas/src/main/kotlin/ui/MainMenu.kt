package ui

import model.Parroquia
import model.Tienda
import service.ParroquiaTiendaManager
import java.time.LocalDate
import java.util.*

/**
 * Interfaz de usuario para interacción con el sistema de Parroquias y Tiendas.
 *
 * Responsabilidades:
 * - Presentar un menú interactivo al usuario
 * - Capturar entradas del usuario
 * - Invocar métodos correspondientes del ParroquiaTiendaManager
 * - Manejar la lógica de presentación y captura de datos
 */

class MainMenu {
    // Gestor de parroquias y tiendas
    private val manager = ParroquiaTiendaManager()
    private val scanner = Scanner(System.`in`)

    fun mostrarMenu() {
        while (true) {
            println("\n--- SISTEMA DE PARROQUIAS Y TIENDAS ---")
            println("1. Crear Parroquia")
            println("2. Crear Tienda")
            println("3. Listar Parroquias")
            println("4. Listar Tiendas por Parroquia")
            println("5. Actualizar Parroquia")
            println("6. Actualizar Tienda")
            println("7. Eliminar Parroquia")
            println("8. Eliminar Tienda")
            println("9. Salir")
            print("Seleccione una opción: ")

            when (val opcion = scanner.nextLine().toIntOrNull()) {
                1 -> crearParroquia()
                2 -> crearTienda()
                3 -> listarParroquias()
                4 -> listarTiendasPorParroquia()
                5 -> actualizarParroquia()
                6 -> actualizarTienda()
                7 -> eliminarParroquia()
                8 -> eliminarTienda()
                9 -> break
                else -> println("Opción inválida")
            }
        }
    }

    private fun crearParroquia() {
        println("Crear Nueva Parroquia")
        print("Nombre de la Parroquia: ")
        val nombre = scanner.nextLine()
        print("Ubicación: ")
        val ubicacion = scanner.nextLine()
        print("Fecha de Creación (AAAA-MM-DD): ")
        val fechaCreacion = LocalDate.parse(scanner.nextLine())
        print("¿Es zona urbana? (true/false): ")
        val esZonaUrbana = scanner.nextLine().toBoolean()
        print("Población Estimada: ")
        val poblacionEstimada = scanner.nextLine().toInt()

        val nuevaParroquia = Parroquia(
            nombre = nombre,
            ubicacion = ubicacion,
            fechaCreacion = fechaCreacion,
            esZonaUrbana = esZonaUrbana,
            poblacionEstimada = poblacionEstimada
        )

        manager.crearParroquia(nuevaParroquia)
        println("Parroquia creada exitosamente.")
    }

    private fun crearTienda() {
        println("Crear Nueva Tienda")
        print("Nombre de la Parroquia a la que pertenece: ")
        val nombreParroquia = scanner.nextLine()

        val parroquiaExistente = manager.listarParroquias()
            .find { it.nombre.equals(nombreParroquia, ignoreCase = true) }

        if (parroquiaExistente == null) {
            println("Error: La parroquia '$nombreParroquia' no existe.")
            return
        }

        print("Nombre de la Tienda: ")
        val nombre = scanner.nextLine()
        print("Dirección: ")
        val direccion = scanner.nextLine()
        print("Fecha de Fundación (AAAA-MM-DD): ")
        val fechaFundacion = LocalDate.parse(scanner.nextLine())
        print("¿Está activa? (true/false): ")
        val estaActiva = scanner.nextLine().toBoolean()
        print("Ventas Mensuales: ")
        val ventasMensuales = scanner.nextLine().toDouble()

        val nuevaTienda = Tienda(
            nombre = nombre,
            direccion = direccion,
            fechaFundacion = fechaFundacion,
            estaActiva = estaActiva,
            ventasMensuales = ventasMensuales,
            parroquia = parroquiaExistente
        )

        manager.crearTienda(nombreParroquia, nuevaTienda)
        println("Tienda creada exitosamente.")
    }

    private fun listarParroquias() {
        println("Lista de Parroquias:")
        manager.listarParroquias().forEachIndexed { index, parroquia ->
            println("${index + 1}. Nombre: ${parroquia.nombre}")
            println("   Ubicación: ${parroquia.ubicacion}")
            println("   Fecha de Creación: ${parroquia.fechaCreacion}")
            println("   Zona Urbana: ${if(parroquia.esZonaUrbana) "Sí" else "No"}")
            println("   Población Estimada: ${parroquia.poblacionEstimada}")
            println("   Número de Tiendas: ${parroquia.tiendas.size}")
            println()
        }
    }

    private fun listarTiendasPorParroquia() {
        print("Ingrese el nombre de la Parroquia: ")
        val nombreParroquia = scanner.nextLine()

        val tiendasEncontradas = manager.listarTiendasPorParroquia(nombreParroquia)

        if (tiendasEncontradas.isEmpty()) {
            println("No se encontraron tiendas en la parroquia '$nombreParroquia'.")
        } else {
            println("Tiendas en la Parroquia $nombreParroquia:")
            tiendasEncontradas.forEachIndexed { index, tienda ->
                println("${index + 1}. Nombre: ${tienda.nombre}")
                println("   Dirección: ${tienda.direccion}")
                println("   Fecha de Fundación: ${tienda.fechaFundacion}")
                println("   Estado: ${if(tienda.estaActiva) "Activa" else "Inactiva"}")
                println("   Ventas Mensuales: $${tienda.ventasMensuales}")
                println("   Parroquia: ${tienda.parroquia.nombre}")
                println()
            }
        }
    }

    private fun actualizarParroquia() {
        print("Ingrese el nombre de la Parroquia a actualizar: ")
        val nombreOriginal = scanner.nextLine()

        println("Ingrese los nuevos datos:")
        print("Nuevo Nombre: ")
        val nuevoNombre = scanner.nextLine()
        print("Nueva Ubicación: ")
        val nuevaUbicacion = scanner.nextLine()
        print("Nueva Fecha de Creación (AAAA-MM-DD): ")
        val nuevaFechaCreacion = LocalDate.parse(scanner.nextLine())
        scanner.nextLine() // Consume the newline left-over
        print("¿Es zona urbana? (true/false): ")
        val nuevoEstadoUrbano = scanner.nextLine().toBoolean()
        print("Nueva Población Estimada: ")
        val nuevaPoblacion = scanner.nextLine().toInt()

        val parroquiaActualizada = Parroquia(
            nombre = nuevoNombre,
            ubicacion = nuevaUbicacion,
            fechaCreacion = nuevaFechaCreacion,
            esZonaUrbana = nuevoEstadoUrbano,
            poblacionEstimada = nuevaPoblacion
        )

        manager.actualizarParroquia(nombreOriginal, parroquiaActualizada)
        println("Parroquia actualizada exitosamente.")
    }

    private fun actualizarTienda() {
        print("Ingrese el nombre de la Parroquia: ")
        val nombreParroquia = scanner.nextLine()
        print("Ingrese el nombre de la Tienda a actualizar: ")
        val nombreTiendaOriginal = scanner.nextLine()

        // Find the parish case-insensitively
        val parroquiaExistente = manager.listarParroquias()
            .find { it.nombre.equals(nombreParroquia, ignoreCase = true) }

        if (parroquiaExistente == null) {
            println("Error: La parroquia '$nombreParroquia' no existe.")
            return
        }

        // Find the store within the parish case-insensitively
        val tiendaOriginal = parroquiaExistente.tiendas
            .find { it.nombre.equals(nombreTiendaOriginal, ignoreCase = true) }

        if (tiendaOriginal == null) {
            println("Error: La tienda '$nombreTiendaOriginal' no existe en la parroquia.")
            return
        }

        println("Ingrese los nuevos datos:")
        print("Nuevo Nombre: ")
        val nuevoNombre = scanner.nextLine()
        print("Nueva Dirección: ")
        val nuevaDireccion = scanner.nextLine()
        print("Nueva Fecha de Fundación (AAAA-MM-DD): ")
        val nuevaFechaFundacion = LocalDate.parse(scanner.nextLine())
        print("¿Está activa? (true/false): ")
        val nuevoEstadoActivo = scanner.nextLine().toBoolean()
        print("Nuevas Ventas Mensuales: ")
        val nuevasVentas = scanner.nextLine().toDouble()

        val tiendaActualizada = Tienda(
            nombre = nuevoNombre,
            direccion = nuevaDireccion,
            fechaFundacion = nuevaFechaFundacion,
            estaActiva = nuevoEstadoActivo,
            ventasMensuales = nuevasVentas,
            parroquia = parroquiaExistente
        )

        manager.actualizarTienda(parroquiaExistente.nombre, tiendaOriginal.nombre, tiendaActualizada)
        println("Tienda actualizada exitosamente.")
    }

    private fun eliminarParroquia() {
        print("Ingrese el nombre de la Parroquia a eliminar: ")
        val nombreParroquia = scanner.nextLine()

        // Buscar la parroquia de manera case-insensitive
        val parroquiaExistente = manager.listarParroquias()
            .find { it.nombre.equals(nombreParroquia, ignoreCase = true) }

        if (parroquiaExistente == null) {
            println("Error: La parroquia '$nombreParroquia' no existe.")
            return
        }

        manager.eliminarParroquia(nombreParroquia)
        println("Parroquia eliminada exitosamente.")
    }

    private fun eliminarTienda() {
        print("Ingrese el nombre de la Parroquia: ")
        val nombreParroquia = scanner.nextLine()
        print("Ingrese el nombre de la Tienda a eliminar: ")
        val nombreTienda = scanner.nextLine()

        val parroquia = manager.listarParroquias()
            .find { it.nombre.equals(nombreParroquia, ignoreCase = true) }

        if (parroquia == null) {
            println("Error: La parroquia '$nombreParroquia' no existe.")
            return
        }

        val tiendaEliminada = parroquia.tiendas
            .find { it.nombre.equals(nombreTienda, ignoreCase = true) }

        if (tiendaEliminada == null) {
            println("Error: La tienda '$nombreTienda' no existe en la parroquia '$nombreParroquia'.")
            return
        }

        manager.eliminarTienda(nombreParroquia, nombreTienda)
        println("Tienda eliminada exitosamente.")
    }
}