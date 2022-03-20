package indigo

class Card(val rank: String, val suite: String) {
    override fun toString(): String {
        return "$rank$suite"
    }
}

fun main() {
    val ranks = mutableListOf<String>("A","2","3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    val suites = mutableListOf<String>("♦", "♥", "♠", "♣")
    println(ranks.joinToString(" "))
    println(suites.joinToString(" "))
    val packOfCards = mutableListOf<Card>()
    for (suite in suites) {
        for (rank in ranks) {
            packOfCards.add(Card(rank, suite))
        }
    }
    //println(packOfCards)
    val shufPackOfCards = packOfCards.shuffled().toMutableList()
    println(shufPackOfCards)
    //println(shufPackOfCards)
    mainMenu(shufPackOfCards)
}


fun mainMenu(pack: MutableList<Card>) {
    var checker = 0
    while (checker == 0) {
        print("Choose an action (reset, shuffle, get, exit):")
        val inputString = readln()
        when (inputString) {
            "reset" -> {
                reset()
                checker++
            }
            "shuffle" -> {
                shuffle()
                checker++
            }
            "get" -> {
                getCard(pack)
                }
            "exit" -> {
                exitGame()
                checker++
            }
            else -> println("Wrong action.")
        }

    }
}

fun reset() {
    TODO("Not yet implemented")
}

fun getCard(pack: MutableList<Card>) {
    print("Number of cards:")
    var number = readln().toInt()
    for (i in 0..number - 1) {
        print("${pack[i]} ")
    }
    println()
}

fun exitGame() {
    println("Bye")
}

fun shuffle() {
    TODO("Not yet implemented")
}
