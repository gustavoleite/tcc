package gustavo.guterres.leite.tcc.utils.extensions

import gustavo.guterres.leite.tcc.data.entity.model.Action
import java.text.NumberFormat
import java.util.Locale

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

fun List<Action>.actionListToString(): String {
    var textActionList = ""
    this.forEachIndexed { index, action ->
        var text = ""
        if (action.text == null) {
            text = "opção ".plus(index.plus(1))
        } else {
            text = action.text
        }
        textActionList += text

        if (index < this.size - 1) {
            textActionList += " ou "
        }
    }
    return textActionList
}
