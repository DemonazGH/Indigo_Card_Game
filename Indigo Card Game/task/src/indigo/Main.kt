package indigo

fun main() {
    val ranks = mutableListOf<String>("A","2","3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    val suites = mutableListOf<String>("♦", "♥", "♠", "♣")
    println(ranks.joinToString(" "))
    println(suites.joinToString(" "))
    val packOfCards = mutableListOf<String>()
    for (suite in suites) {
        for (rank in ranks) {
            packOfCards.add("$rank$suite")
        }
    }
    println(packOfCards.joinToString(" "))
}
