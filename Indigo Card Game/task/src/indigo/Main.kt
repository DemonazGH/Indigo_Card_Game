package indigo

import kotlin.random.Random

class Player(
    val inHand: MutableList<Card>,
    val winCards: MutableList<Card>,
    val name: String,
    var lastWin: Boolean = false,
    var first: Boolean = false
) {
    var score = 0

    /* Player draws cards from the deck */
    fun fillDeck(inHand: MutableList<Card>, packOfCards: MutableList<Card>): MutableList<Card> {
        inHand.addAll(GameDeck.transferCards(packOfCards))
        return inHand
    }

    /* score calculation */
    fun scoreCard(player: Player) {
        var counter = 0
        for (it in player.winCards) {
            if (it.returnRank() == "A" || it.returnRank() == "Q" || it.returnRank() == "K"
                || it.returnRank() == "J" || it.returnRank() == "10"
            )
                counter++
        }
        player.score = counter
    }

    /* generates random card when PC moves*/
    fun getRandomCard(player: Player): Card {
        val card = player.inHand.get(Random.nextInt(0, player.inHand.size))
        //  println(getCardPos(inHand, card))
        return card
    }

    /* defines generated card's position in PC's deck*/
    fun getCardPos(inHand: MutableList<Card>, card: Card): Int {
        return inHand.indexOf(card)
    }
}

/* operations with cards */
object GameDeck {
    private val ranks = mutableListOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    private val suites = mutableListOf("♦", "♥", "♠", "♣")
    private lateinit var packOfCards: MutableList<Card>

    /*Transferring to the table 4 cards at the beginning of the game*/
    fun transToTableCards(packOfCards: MutableList<Card>): MutableList<Card> {
        val trans = packOfCards.slice(0..3)
        packOfCards.removeAll(packOfCards.slice(0..3))
        return trans.toMutableList()
    }

    /* transferring cards from the game deck to the players' decks */
    fun transferCards(
        packOfCards: MutableList<Card>
    ): MutableList<Card> {
        val transfercards = mutableListOf<Card>()
        transfercards.addAll(packOfCards.slice(0..5))
        packOfCards.removeAll(packOfCards.slice(0..5))
        return transfercards

    }

    /* Generating of the new shuffled game deck */
    fun newDeck(): MutableList<Card> {
        packOfCards = mutableListOf()
        for (suite in suites) {
            for (rank in ranks) {
                packOfCards.add(Card(rank, suite))
            }
        }
        return packOfCards.shuffled().toMutableList()
    }

    /* transfering cards from table to winner */
    fun ifWinTransferCards(
        inHand: MutableList<Card>,
        winCards: MutableList<Card>,
        tableCards: MutableList<Card>,
        choice: Int
    ) {
        winCards.add(inHand[choice])
        winCards.addAll(tableCards)
        tableCards.clear()
        inHand.removeAt(choice)
    }
}

class Card(private val rank: String, private val suite: String) {
    override fun toString(): String {
        return "$rank$suite"
    }

    fun returnSuite(): String {
        return suite
    }

    fun returnRank(): String {
        return rank
    }
}


fun main() {
    val packOfCards = GameDeck.newDeck()
    val humanDeck = mutableListOf<Card>()
    val pcDeck = mutableListOf<Card>()
    val humanWinCards = mutableListOf<Card>()
    val pcWinCards = mutableListOf<Card>()
    val tableCards = mutableListOf<Card>()
    val human = Player(humanDeck, humanWinCards, "Player")
    val pc = Player(pcDeck, pcWinCards, "Computer")
    gameInit(human, pc, packOfCards, tableCards)
    val turn = gameFirstPrompt()
    if (turn == 1) human.first = true else pc.first = true
    print("Initial cards on the table: ")
    println(tableCards.joinToString(" "))
    gamePlay(human, pc, packOfCards, tableCards, turn)
    print("Game Over")
}

/*
Initializing variables and game environment
 */
fun gameInit(
    human: Player,
    pc: Player,
    packOfCards: MutableList<Card>,
    tableCards: MutableList<Card>,
) {
    tableCards.addAll(GameDeck.transToTableCards(packOfCards))
    human.fillDeck(human.inHand, packOfCards)
    pc.fillDeck(pc.inHand, packOfCards)
}

/*
First dialogue function
 */
fun gameFirstPrompt(): Int {
    println("Indigo Card Game")
    while (true) {
        println("Play first?")
        val answer = readln()
        if (answer.contains("yes")) {
            return 1
        } else if (answer.contains("no")) {
            return 2
        }
    }
}

/*
Gameplay processing function
 */
fun gamePlay(
    human: Player,
    pc: Player,
    packOfCards: MutableList<Card>,
    tableCards: MutableList<Card>, firstMove: Int
) {
    var _firstMove = firstMove
    var choice: String

    while (human.winCards.size + pc.winCards.size + tableCards.size < 52) {
        if (tableCards.size == 0) {
            println("No cards on the table")
        } else {
            println("${tableCards.size} cards on the table, and the top card is ${tableCards.last()}")
        }
        if (_firstMove == 1) { /* Human's move starts here */
            print("Cards in hand: ")
            choice = humanMovePromptExecution(human.inHand)
            if (choice == "exit") return
            else {
                val humChoice = choice.toInt() - 1
                gameMove(human, pc, tableCards, packOfCards, humChoice)
                _firstMove++
            }
        } else { /* PC's move starts here */
            var pcChoice = 0
            if (pc.inHand.size == 1) {
                println("Computer plays ${pc.inHand[pcChoice]}")
            } else {
                val choiceCard = pc.getRandomCard(pc)
                pcChoice = pc.getCardPos(pc.inHand, choiceCard)
                println("Computer plays $choiceCard")
            }
            gameMove(pc, human, tableCards, packOfCards, pcChoice)
            _firstMove--
        }
    }
    /* Start of processing of final calculations in the end of the game*/
    if (tableCards.size > 0) {
        println("${tableCards.size} cards on the table, and the top card is ${tableCards.last()}")
        if (human.lastWin) {
            human.winCards.addAll(tableCards)
        } else if (pc.lastWin) {
            pc.winCards.addAll(tableCards)
        } else if (human.first) {
            human.winCards.addAll(tableCards)
        } else {
            println("No cards on the table")
            pc.winCards.addAll(tableCards)
        }
    }
    scoreCalc(human, pc)
    if (human.winCards.size == pc.winCards.size && human.first) {
        human.score += 3
    } else if (human.winCards.size == pc.winCards.size && pc.first) {
        pc.score += 3
    } else if (human.winCards.size > pc.winCards.size) {
        human.score += 3
    } else pc.score += 3
    printInGameScore(human, pc)
}

/* Processing of a Player's move */
fun gameMove(
    mover: Player,
    opposite: Player,
    tableCards: MutableList<Card>,
    packOfCards: MutableList<Card>,
    num: Int,
) {
    if (tableCards.size > 0 && winCardCheck(mover, tableCards, num)) {
        println("${mover.name} wins cards")
        mover.lastWin = true
        opposite.lastWin = false
        GameDeck.ifWinTransferCards(mover.inHand, mover.winCards, tableCards, num)
        scoreCalc(mover, opposite)
        if (mover.winCards.size + opposite.winCards.size < 52) {
            printInGameScore(mover, opposite)
        }
    } else {
        tableCards.add(mover.inHand[num])
        mover.inHand.removeAt(num)
    }
    if (mover.inHand.size == 0 && packOfCards.size != 0) {
        mover.fillDeck(mover.inHand, packOfCards)
    }
}

/* Checking if player's card wins */
fun winCardCheck(player: Player, tableCards: MutableList<Card>, num: Int): Boolean {
    return player.inHand[num].returnSuite() == tableCards[tableCards.lastIndex].returnSuite() ||
            player.inHand[num].returnRank() == tableCards[tableCards.lastIndex].returnRank()
}

/* Processing of human's move choice */
fun humanMovePromptExecution(human: MutableList<Card>): String {
    var checker = 0
    print(buildString {
        for (i in 1..human.size) {
            append("$i)${human[i - 1]} ")
        }
        append("\n")
    })
    while (checker == 0) {
        println("Choose a card to play (1-${human.size}):")
        val choice: String? = readlnOrNull()
        if (choice != null) {
            try {
                if (choice.contains("exit")) {
                    checker++
                    return "exit"
                } else if (choice.toInt() <= human.size &&
                    choice.toInt() > 0
                ) {
                    checker++
                    return choice
                }
            } catch (e: NumberFormatException) {
            }
        }
    }
    return ""
}

/*In game score calculation */
fun scoreCalc(player1: Player, player2: Player) {
    player1.scoreCard(player1)
    player2.scoreCard(player2)
}

/* Printing the score */
fun printInGameScore(player1: Player, player2: Player) {
    if (player1.name == "Player") {
        println("Score: Player ${player1.score} - Computer ${player2.score}")
        println("Cards: Player ${player1.winCards.size} - Computer ${player2.winCards.size}")
    } else {
        println("Score: Player ${player2.score} - Computer ${player1.score}")
        println("Cards: Player ${player2.winCards.size} - Computer ${player1.winCards.size}")
    }
}
