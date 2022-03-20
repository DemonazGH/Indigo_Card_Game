import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val distance = scanner.nextInt()
    val hours = scanner.nextInt()
    println((distance / hours).toDouble())
}
