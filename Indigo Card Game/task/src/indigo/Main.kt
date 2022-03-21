package indigo

import indigo.GameDeck.getPrompt

class Card(private val rank: String, private val suite: String) {
    override fun toString(): String {
        return "$rank$suite"
    }
}

object GameDeck {
    private val ranks = mutableListOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    private val suites = mutableListOf("♦", "♥", "♠", "♣")
    private lateinit var packOfCards: MutableList<Card>

    /*
     */
    fun newDeck(): MutableList<Card> {
        packOfCards = mutableListOf()
        for (suite in suites) {
            for (rank in ranks) {
                packOfCards.add(Card(rank, suite))
            }
        }
        return packOfCards.shuffled().toMutableList()
    }

    /*
     */
    fun shuffle(packOfCards: MutableList<Card>): MutableList<Card> {
        return packOfCards.shuffled().toMutableList()
    }

    /*
     */
    fun getPrompt(packOfCards: MutableList<Card>): String {
        print("Number of cards:")
        val inputSpring = readln().toIntOrNull()
        return if (inputSpring != null) {
            if (inputSpring == 0 || inputSpring > 52) {
                println("Invalid number of cards.")
                "-1"
            } else if (inputSpring > packOfCards.lastIndex + 1 || inputSpring < 0) {
                println("The remaining cards are insufficient to meet the request.")
                "-1"
            } else {
                inputSpring.toString()
            }
        } else {
            println("Invalid number of cards.")
            "-1"
        }
    }

    /*
 */
    fun reset(): MutableList<Card> {
        return newDeck()
    }
}

fun main() {
    val packOfCards = GameDeck.newDeck()
    val gamerDeck = mutableListOf<Card>()
    mainMenu(packOfCards, gamerDeck)
}

/*
 */
fun mainMenu(packOfCards: MutableList<Card>, gamerDeck: MutableList<Card>) {
    var gamePackOfCards = packOfCards
    var checker = 0
    while (checker == 0) {
        print("Choose an action (reset, shuffle, get, exit):")
        when (readln()) {
            "reset" -> {
                gamePackOfCards = GameDeck.reset()
                gamerDeck.clear()
                println("Card deck is reset.")
            }
            "shuffle" -> {
                GameDeck.shuffle(gamePackOfCards)
                println("Card deck is shuffled.")
            }
            "get" -> {
                val gett = getPrompt(gamePackOfCards).toInt()
                if (gett > 0) {
                    val range = (0..gett)
                    for (i in range - 1) {
                        gamerDeck.add(gamePackOfCards[0])
                        gamePackOfCards.removeAt(0)
                    }
                    println(gamerDeck.joinToString(" "))
                }
            }
            "exit" -> {
                println("Bye")
                checker++
            }
            else -> println("Wrong action.")
        }
    }
}