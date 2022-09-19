package ch.ennio.sileno.hoseabe

import java.util.*

class Deck {
    private var unusedCards: ArrayList<Card> = ArrayList()

    init {
        val colors: Array<CardColor> = CardColor.values()
        val values: Array<CardValue> = CardValue.values()
        for (color in colors) {
            for (value in values) {
                unusedCards.add(Card(color, value))
            }
        }
    }

    // Method only returns cards that are not already fire or pants down
    fun getThreeRandomCards(): ArrayList<Card> {
        val random: Random = Random()
        val cards: ArrayList<Card> = ArrayList()
        var cardsAreValid = false
        while(!cardsAreValid) {
            for(i in 0..2) {
                val index = random.nextInt(unusedCards.size)
                cards.add(unusedCards[index])
                unusedCards.removeAt(index)
            }
            val points: Float = Auxiliary.calculatePoints(cards)
            if (Auxiliary.isFire(points) || Auxiliary.isPantsDown(points)) {
                for (i in 2 downTo 0) {
                    unusedCards.add(cards[i])
                    cards.removeAt(i)
                }
            } else {
                cardsAreValid = true
            }
        }
        return cards
    }
}
