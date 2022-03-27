import kotlin.math.pow

fun main() {
    val inputString = readln()
    val meanString = readln().toInt()
    when (inputString) {
        "amount" -> println(compInterest(meanString).toInt())
        "percent" -> println(compInterest(ypercent = meanString).toInt())
        "years" -> println(compInterest(years = meanString).toInt())

    }
}

fun compInterest(
    sammount: Int = 1000,
    ypercent: Int = 5,
    years: Int = 10
) = sammount * (1 + ypercent.toDouble() / 100).pow(years.toDouble())
