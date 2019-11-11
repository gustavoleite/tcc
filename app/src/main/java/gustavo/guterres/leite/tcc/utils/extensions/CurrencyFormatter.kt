package gustavo.guterres.leite.tcc.utils.extensions

fun Double.toPoints(): String {
    return this.toString().plus(" pontos")
}

fun Double.toPointsWithBreakLine(): String {
    return this.toString().plus("\npontos")
}