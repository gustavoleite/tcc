package gustavo.guterres.leite.tcc

import gustavo.guterres.leite.tcc.utils.extensions.toBrCurrency
import gustavo.guterres.leite.tcc.utils.extensions.toPoints
import gustavo.guterres.leite.tcc.utils.extensions.toPointsWithBreakLine
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class ExtensionUtilsTest {

    @Test
    fun `must convert to string and add br currency symbol`() {
        assertThat(0.0.toBrCurrency(), `is`("R$ 0,00"))
        assertThat(0.5.toBrCurrency(), `is`("R$ 0,50"))
        assertThat(5.3.toBrCurrency(), `is`("R$ 5,30"))
        assertThat(23.56.toBrCurrency(), `is`("R$ 23,56"))
        assertThat(968.23.toBrCurrency(), `is`("R$ 968,23"))
        assertThat(1435.0.toBrCurrency(), `is`("R$ 1.435,00"))
        assertThat(12435.0.toBrCurrency(), `is`("R$ 12.435,00"))
        assertThat(123435.09.toBrCurrency(), `is`("R$ 123.435,09"))
    }

    @Test
    fun `must convert to string and concatenate points`() {
        assertThat(0.toPoints(), `is`("0 pontos"))
        assertThat(5.toPoints(), `is`("5 pontos"))
        assertThat(10.toPoints(), `is`("10 pontos"))
        assertThat(100.toPoints(), `is`("100 pontos"))
        assertThat(1234.toPoints(), `is`("1234 pontos"))
    }

    @Test
    fun `must convert to string and concatenate points with break line`() {
        assertThat(0.toPointsWithBreakLine(), `is`("0\npontos"))
        assertThat(5.toPointsWithBreakLine(), `is`("5\npontos"))
        assertThat(10.toPointsWithBreakLine(), `is`("10\npontos"))
        assertThat(100.toPointsWithBreakLine(), `is`("100\npontos"))
        assertThat(1234.toPointsWithBreakLine(), `is`("1234\npontos"))
    }
}