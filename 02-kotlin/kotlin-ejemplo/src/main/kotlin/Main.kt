import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {

//    val suma = Suma(10, 20)
//    println("La suma es: ${suma.sumar()}")

//    imprimirNombre("Miguel")
//    calcularSueldo(264.50)
//
//    calcularSueldo(10.00, bonoEspecial = 20.00 )
//    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumaA = Suma(1,1)
    val sumaB = Suma(null,1)
    val sumaC = Suma(1,null)
    val sumaD = Suma(null,null)
    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)


    //Arreglos estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico.joinToString());
    //Arreglos dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)


    //FOR EACH => Unit
    //Iterar un arreglo
//    val respuestaForEach: Unit arregloDinamico
//        .forEach {valorActual: Int ->
//            println("Valor actual: ${valorActual}");
//        }

    //Iterar un arreglo
    //'it' (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach{ println("Valor Actual (it): ${it}")}

    //MAP -> (Modifica o cambia) el arrreglo
    // 1. Enviamos el nuevo valor de la iteracion
    // 2. Nos devuelve un nuevo arregl con valores de las iteraciones

    val respuestaMap: List<Double> = arregloDinamico.map {
        valorActual: Int ->
        return@map valorActual.toDouble() + 100.00
    }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)

    //Filter -> Flitrar el arreglo
    //1. Devuelve una expresion (TRUE O FALSE)
    //2. Nuevo arreglo FILTRADO.
    val respuestaFilter: List<Int> = arregloDinamico.filter {
        valorActual: Int ->
        //Expresion o Condicion
        val mayoresACinco: Boolean = valorActual > 5
        return@filter mayoresACinco
    }
    println(respuestaFilter)


    val respuestaFilterDos = arregloDinamico.filter {
        it <= 5
    }
    println(respuestaFilterDos)


    //OR AND
    // OR -> ANY (Alguno cumple)
    // And -> ALL (Todos cumplen)
    val respuestaAny: Boolean = arregloDinamico.any {
        valorActual: Int ->
        return@any (valorActual > 5)
    }
    println(respuestaAny)   //True

    val respuestaAll: Boolean = arregloDinamico.all {
        valorActual: Int ->
        return@all (valorActual > 5)
    }
    println(respuestaAll)   //False

    // REDUCE -> Valor acumulado
    // Valor acumulado = 0 (Siempre empieza en 0 en Kotlin)
    // [1,2,3,4,5] -> Acumular "SUMAR" estos valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteracion1
    // valorIteracion2 = valorAcumuladoIteracion1 + 2 = 1 + 2 = 3 -> Iteracion2
    // valorIteracion3 = valorAcumuladoIteracion2 + 3 = 3 + 3 = 6 -> Iteracion3
    // valorIteracion4 = valorAcumuladoIteracion3 + 3 = 6 + 4 = 10 -> Iteracion4
    // valorIteracion5 = valorAcumuladoIteracion4 + 5 = 10 + 5 = 15 -> Iteracion5
    val respuestaReduce: Int = arregloDinamico.reduce {
        acumulado:Int, valorActual: Int ->
        return@reduce (acumulado + valorActual)
    }
    println(respuestaReduce)



}
fun imprimirNombre(nombre: String): Unit {
    fun otraFuncionAdentro(){
        println("Otra fucion adentro")
    }
    println("Nombre: $nombre")
    println("Nombre: ${nombre}")
    println("Nombre: ${nombre + nombre}")
    println("Nombre: ${nombre.toString()}")
    println("Nombre: ${nombre.toString()}")
    otraFuncionAdentro()
}

fun calcularSueldo(
    sueldo:Double,
    tasa: Double = 12.00,
    bonoEspecial: Double? = null
): Double {
    if (bonoEspecial == null) {
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}


abstract class Numeros(
    protected val numeroUno: Int,
    protected val numeroDos: Int,
    parametroNousadoNoPropiedadDeLaClase:Int? = null
){init {    //Bloque constructor primario OPCIONAL
    this.numeroUno
    this.numeroDos
    println("Inicializando")
}}

//class Suma (
//    unoParametro: Int,
//    dosParametro: Int,
//): Numeros(
//    unoParametro,
//    dosParametro
//){
//  fun sumar(): Int {
//      return numeroUno + numeroDos
//  }
//}

class Suma (    //Constructor primario
    unoParametro: Int,  //Parametro
    dosParametro: Int,  //parametro
): Numeros(     //Clase papa, Numeros(extendido)
    unoParametro,
    dosParametro
){
   public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicito: String = "Publico implicito"
    init {  //Bloque constructor primario
        this.numeroUno
        this.numeroDos
        numeroUno   //this. es opcional [propiedades, metodos]
        numeroDos   //this. es opcional [propiedades, metodos]
        this.soyPublicoImplicito
        soyPublicoExplicito
    }

    constructor(    //Construcotr secundario
        uno:Int?,   //Entero nullable
        dos:Int,
    ) : this(
        if (uno == null) 0 else uno,
        dos
    ){
            //Bloque de codigo de constructor secundario
    }

    constructor(
        uno:Int,
        dos: Int?,  //Entero nullable
    ) : this(
        uno,
        if (dos == null) 0 else dos
    )

    constructor(
        uno: Int?,  //Entero nullable
        dos: Int?,  //Entero nullable
    ) : this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )

    fun sumar(): Int {
      val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object {  //Comparte entre todas las instancias, similar al STATIC
        //funciones, variables
        //NombreClase.metodo, NombreClase.funcion =>
        //Suma.pi
        val pi = 3.14
        // Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num:Int):Int{return num * num}
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){   //Suma.agregarHistorial
            historialSumas.add(valorTotalSuma)
        }
    }

}










