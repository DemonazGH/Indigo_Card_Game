import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val eqarray = Array<Double>(3) { scanner.nextDouble() }
    println((eqarray[2] - eqarray[1]) / eqarray[0])
}
