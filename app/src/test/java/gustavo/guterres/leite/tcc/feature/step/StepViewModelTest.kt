package gustavo.guterres.leite.tcc.feature.step

import android.os.Build
import androidx.lifecycle.Observer
import gustavo.guterres.leite.tcc.BaseTest
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Action
import gustavo.guterres.leite.tcc.data.entity.model.Content
import gustavo.guterres.leite.tcc.data.entity.model.Step
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import gustavo.guterres.leite.tcc.BaseApplicationTest

@Config(sdk = [Build.VERSION_CODES.P], application = BaseApplicationTest::class)
@RunWith(RobolectricTestRunner::class)
class StepViewModelTest : BaseTest() {

    private lateinit var sut: StepViewModel

    private val actionList = arrayListOf(
        Action("1", null, R.drawable.ic_note_fifty),
        Action("2", null, R.drawable.ic_note_five),
        Action("3", null, R.drawable.ic_note_hundred)
    )
    private val content = Content("Identifique o valor da nota", arrayListOf())
    private val step = Step("1", 25.0, content, actionList, "3")

    @MockK
    lateinit var mockIsRightAnswer: Observer<Boolean>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        sut = StepViewModel()
        sut.isRightAnswer.observe(mockLifecycleOwner(), mockIsRightAnswer)
    }

    @Test
    fun `must set data`() {
        sut.setData(step)

        assertThat(sut.question.get(), `is`("Identifique o valor da nota"))
        assertThat(sut.spotlightList.get(), `is`(arrayListOf()))
        assertThat(sut.actionList.get(), `is`(actionList.toList()))
        assertThat(sut.expectedActionId, `is`("3"))
    }

    @Test
    fun `when select a action must validate if its corret`() {
        sut.setData(step)

        sut.onActionSelected(actionList[2])

        verify {
            mockIsRightAnswer.onChanged(true)
        }
    }
}