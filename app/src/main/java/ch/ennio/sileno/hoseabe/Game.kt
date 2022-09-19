package ch.ennio.sileno.hoseabe

class Game(playerNames: ArrayList<String>) {
    private val players: ArrayList<Player> = ArrayList()

    private val removedPlayers: ArrayList<String> = ArrayList()
    private var cardsToTrade: ArrayList<Card> = ArrayList()
    private var isFirstRound: Boolean = true
    private var isStopped: Boolean = false
    private var firstRoundCounter: Int = 0
    private var currentPlayerIndex: Int = 0
    private var stopCounter: Int = 0
    private var deck: Deck = Deck()

    init {
        playerNames.iterator().forEach { playerName ->
            players.add(Player(playerName))
        }
        setupNewGameRound()
    }

    private fun setupNewGameRound() {
        setupCards()
        for(player in players) {
            player.hasSkipped = false
        }
        currentPlayerIndex = getCurrentPlayerAtRoundStart()
        players[currentPlayerIndex].addOneRoundStart()
        isFirstRound = true
        firstRoundCounter = 0
        isStopped = false
        stopCounter = 0
        calculateAllPlayerPoints()
    }

    private fun setupCards() {
        deck = Deck()
        cardsToTrade = deck.getThreeRandomCards()
        for(player in players) {
            player.setCards(deck.getThreeRandomCards())
        }
    }

    private fun getCurrentPlayerAtRoundStart(): Int {
        val roundStartCounter: Int = players[0].getRoundStartCounter()
        for (player in players) {
            if (player.getRoundStartCounter() < roundStartCounter) {
                return players.indexOf(player)
            }
        }
        return 0 // all players have same amount of round starts, return first player
    }

    private fun calculateAllPlayerPoints() {
        for (player in players) {
            val points: Float = Auxiliary.calculatePoints(player.getCards())
            player.setPoints(points)
        }
    }

    // indices: -1 means stop, -2 means skip, 3 means change all cards
    fun updateGame(middleCardIndex: Int, playerCardIndex: Int): GameState {
        // update cards
        if (middleCardIndex == 3 && playerCardIndex == 3) {
            changeAllCards()
        } else if (middleCardIndex == -1 && playerCardIndex == -1) {
            // player chose stop, no cards have to be changed
        } else if (middleCardIndex == -2 && playerCardIndex == -2) {
            // player chose skip, no cards have to be changed
        } else {
            changeOneCard(middleCardIndex, playerCardIndex)
        }

        // update points
        val points = Auxiliary.calculatePoints(players[currentPlayerIndex].getCards())
        players[currentPlayerIndex].setPoints(points)

        // handle round / game is finished
        if (Auxiliary.isFire(points) || Auxiliary.isPantsDown(points)
            || stopCounter == players.size - 1
        ) {
            if (Auxiliary.isFire(points)) {
                subtractLifesCaseFire()
            } else {
                subtractLifes()
            }
            removePlayersWithZeroLives()
            return if (players.size > 1) {
                setupNewGameRound()
                GameState.ROUND_FINISHED
            } else {
                GameState.GAME_FINISHED
            }
        }

        // update isFirstRound if needed
        if (isFirstRound) {
            if (firstRoundCounter < players.size - 1) {
                firstRoundCounter++
            } else {
                isFirstRound = false
            }
        }

        // update isStopped, lastRoundCounter if needed
        if (playerCardIndex == -1 && middleCardIndex == -1) {
            if (!isStopped) {
                isStopped = true
            }
            stopCounter++
        } else if (isStopped) { // round is stopped but the current player changed his cards
            stopCounter++
        }

        if (playerCardIndex == -2 && middleCardIndex == -2) {
            getCurrentPlayer().hasSkipped = true
        }

        // update currentPlayer
        if (currentPlayerIndex < players.size - 1) {
            currentPlayerIndex++
        } else {
            currentPlayerIndex = 0
        }
        return GameState.RUNNING
    }

    private fun changeAllCards() {
        val newMiddleCards = players[currentPlayerIndex].getCards()
        val newPlayerCards: ArrayList<Card> = cardsToTrade
        players[currentPlayerIndex].setCards(newPlayerCards)
        cardsToTrade = newMiddleCards
    }

    private fun changeOneCard(middleCardIndex: Int, playerCardIndex: Int) {
        val newMiddleCard = players[currentPlayerIndex].getCardByIndex(playerCardIndex)
        val newPlayerCard: Card = cardsToTrade.get(middleCardIndex)
        players[currentPlayerIndex].setCardByIndex(playerCardIndex, newPlayerCard)
        cardsToTrade.set(middleCardIndex, newMiddleCard)
    }

    private fun subtractLifes() {
        var leastPoints = 33f
        for (player in players) {
            if (player.getPoints() < leastPoints) leastPoints = player.getPoints()
        }
        for (player in players) {
            if (player.getPoints() === leastPoints) player.subtractOneLife()
        }
    }

    private fun subtractLifesCaseFire() {
        for (i in players.indices) {
            if (i != currentPlayerIndex) players[i].subtractOneLife()
        }
    }

    private fun removePlayersWithZeroLives() {
        removedPlayers.clear()
        for (i in players.indices.reversed()) {
            if (players[i].getLifes() === 0) {
                removedPlayers.add(players[i].getName())
                players.removeAt(i)
            }
        }
    }

    fun getCardsToTrade(): List<Card> {
        return cardsToTrade
    }

    fun getCurrentPlayer(): Player {
        return players[currentPlayerIndex]
    }

    fun getIsFirstRound(): Boolean {
        return isFirstRound
    }

    fun getWinner(): String {
        return if (players.size == 1) players[0].getName() else ""  // else = game has no winner
    }

    fun getRemovedPlayers(): ArrayList<String> {
        return removedPlayers
    }
}