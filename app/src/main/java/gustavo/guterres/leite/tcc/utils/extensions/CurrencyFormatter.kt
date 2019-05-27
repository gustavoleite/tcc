package gustavo.guterres.leite.tcc.utils.extensions

import java.text.NumberFormat
import java.util.*

fun Double.toBrCurrency(): String {
    return NumberFormat
        .getCurrencyInstance(Locale("pt", "BR"))
        .format(this)
}