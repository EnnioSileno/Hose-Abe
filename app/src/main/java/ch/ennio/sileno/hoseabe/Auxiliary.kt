package ch.ennio.sileno.hoseabe

import java.util.ArrayList

class Auxiliary {
    companion object {
        fun calculatePoints(cards: ArrayList<Card>): Float {
            val c0: CardColor = cards[0].color
            val c1: CardColor = cards[1].color
            val c2: CardColor = cards[2].color
            val v0: CardValue = cards[0].value
            val v1: CardValue = cards[1].value
            val v2: CardValue = cards[2].value
            val i0: Int = cards[0].value.asInt
            val i1: Int = cards[1].value.asInt
            val i2: Int = cards[2].value.asInt

            return if (v0 === v1 && v1 === v2) {
                if (v0 === CardValue.ACE) { // fire
                    33f
                } else { // 3 times same value
                    30.5f
                }
            } else if (c0 === c1 && c1 === c2) { // 3 times same color, possible pants down
                (i0 + i1 + i2).toFloat()
            } else if (c0 !== c1 && c0 !== c2 && c1 !== c2) { // 3 different colors, return highest worth
                if (i0 >= i1 && i0 >= i2) {
                    i0.toFloat()
                } else if (i1 >= i0 && i1 >= i2) {
                    i1.toFloat()
                } else {
                    i2.toFloat()
                }
            } else { // 2 times same color
                if (c0 === c1) {
                    (i0 + i1).toFloat()
                } else if (c0 === c2) {
                    (i0 + i2).toFloat()
                } else {
                    (i1 + i2).toFloat()
                }
            }
        }

        fun isPantsDown(points: Float): Boolean {
            return points == 31f
        }

        fun isFire(points: Float): Boolean {
            return points == 33f
        }

        fun getImageResource(card: Card): Int {
            val color: CardColor = card.color
            val value: CardValue = card.value
            return if (color === CardColor.EICHEL) {
                if (value === CardValue.SIX) R.drawable.eichel_06
                else if (value === CardValue.SEVEN) R.drawable.eichel_07
                else if (value === CardValue.EIGHT) R.drawable.eichel_08
                else if (value === CardValue.NINE) R.drawable.eichel_09
                else if (value === CardValue.TEN) R.drawable.eichel_10
                else if (value === CardValue.JACK) R.drawable.eichel_jack
                else if (value === CardValue.QUEEN) R.drawable.eichel_queen
                else if (value === CardValue.KING) R.drawable.eichel_king
                else R.drawable.eichel_ace
            } else if (color === CardColor.ROSE) {
                if (value === CardValue.SIX) R.drawable.rosen_06
                else if (value === CardValue.SEVEN) R.drawable.rosen_07
                else if (value === CardValue.EIGHT) R.drawable.rosen_08
                else if (value === CardValue.NINE) R.drawable.rosen_09
                else if (value === CardValue.TEN) R.drawable.rosen_10
                else if (value === CardValue.JACK) R.drawable.rosen_jack
                else if (value === CardValue.QUEEN) R.drawable.rosen_queen
                else if (value === CardValue.KING) R.drawable.rosen_king
                else R.drawable.rosen_ace
            } else if (color === CardColor.SCHELLE) {
                if (value === CardValue.SIX) R.drawable.schellen_06
                else if (value === CardValue.SEVEN) R.drawable.schellen_07
                else if (value === CardValue.EIGHT) R.drawable.schellen_08
                else if (value === CardValue.NINE) R.drawable.schellen_09
                else if (value === CardValue.TEN) R.drawable.schellen_10
                else if (value === CardValue.JACK) R.drawable.schellen_jack
                else if (value === CardValue.QUEEN) R.drawable.schellen_queen
                else if (value === CardValue.KING) R.drawable.schellen_king
                else R.drawable.schellen_ace
            } else { // SCHILTE
                if (value === CardValue.SIX) R.drawable.schilten_06
                else if (value === CardValue.SEVEN) R.drawable.schilten_07
                else if (value === CardValue.EIGHT) R.drawable.schilten_08
                else if (value === CardValue.NINE) R.drawable.schilten_09
                else if (value === CardValue.TEN) R.drawable.schilten_10
                else if (value === CardValue.JACK) R.drawable.schilten_jack
                else if (value === CardValue.QUEEN) R.drawable.schilten_queen
                else if (value === CardValue.KING) R.drawable.schilten_king
                else R.drawable.schilten_ace
            }
        }
    }
}