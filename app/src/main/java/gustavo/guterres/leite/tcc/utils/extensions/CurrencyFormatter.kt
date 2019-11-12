package gustavo.guterres.leite.tcc.utils.extensions

import java.text.NumberFormat
import java.util.*

fun Double.toBrCurrency(): String {
    return NumberFormat
        .getCurrencyInstance(Locale("pt", "BR"))
        .format(this)
}

fun Int.toPoints(): String {
    return this.toString().plus(" pontos")
}

fun Int.toPointsWithBreakLine(): String {
    return this.toString().plus("\npontos")
}