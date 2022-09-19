package ch.ennio.sileno.hoseabe

class Player(playerName: String) {
    companion object {
        private const val MAX_LIFES: Int = 3
    }

    private val name: String = playerName

    private var cards: ArrayList<Card> = ArrayList()
    private var lifes: Int = MAX_LIFES
    private var points: Float = 0f
    var hasSkipped: Boolean = false

    private var roundStartCounter: Int = 0

    fun getName(): String {
        return name
    }

    fun getLifes(): Int {
        return lifes
    }

    fun subtractOneLife() {
        lifes--
    }

    fun getPoints(): Float {
        return points
    }

    fun setPoints(points: Float) {
        this.points = points
    }

    fun getCards(): ArrayList<Card> {
        return cards
    }

    fun getCardByIndex(index: Int): Card {
        return cards[index]
    }

    fun setCards(cards: ArrayList<Card>) {
        this.cards = cards
    }

    fun setCardByIndex(index: Int, card: Card) {
        cards[index] = card
    }

    fun getRoundStartCounter(): Int {
        return roundStartCounter
    }

    fun addOneRoundStart() {
        roundStartCounter++
    }
}