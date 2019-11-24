package gustavo.guterres.leite.tcc.feature.levelonboarding

import android.os.Build
import android.view.View
import androidx.lifecycle.Observer
import gustavo.guterres.leite.tcc.BaseApplicationTest
import gustavo.guterres.leite.tcc.BaseTest
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

@Config(sdk = [Build.VERSION_CODES.P], application = BaseApplicationTest::class)
@RunWith(RobolectricTestRunner::class)
class LevelOnboardingViewModelTest : BaseTest() {

    private lateinit var sut: LevelOnboardingViewModel

    @MockK
    private lateinit var mockOkClik: Observer<Unit>
    @MockK
    private lateinit var mockCloseClik: Observer<Unit>
    @MockK
    private lateinit var mockCurrentItem: Observer<Int>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        sut = LevelOnboardingViewModel(3)

        sut.okClick.observe(mockLifecycleOwner(), mockOkClik)
        sut.backClick.observe(mockLifecycleOwner(), mockCloseClik)
        sut.currentItem.observe(mockLifecycleOwner(), mockCurrentItem)
    }

    @Test
    fun `must set static data`() {
        verify {
            mockCurrentItem.onChanged(0)
        }
        assertThat(sut.okButtonVisibility.get(), `is`(View.GONE))
    }

    @Test
    fun `when ok button click must trigger action`() {
        sut.onOkClick()

        verify {
            mockOkClik.onChanged(Unit)
        }
    }

    @Test
    fun `when close button click must trigger action`() {
        sut.onCloseClick()

        verify {
            mockCloseClik.onChanged(Unit)
        }
    }

    @Test
    fun `when back button clicked and current item is not the first item must decrement it`() {
        sut.currentItem.value = 2
        sut.onBackClick()

        verify {
            mockCurrentItem.onChanged(1)
        }
    }

    @Test
    fun `when back button clicked and current item is the first item must not decrement it`() {
        sut.currentItem.value = 0
        sut.onBackClick()

        verify {
            mockCurrentItem.onChanged(0)
        }
    }

    @Test
    fun `when next button clicked and current item is not the last item must increment it`() {
        sut.currentItem.value = 1
        sut.onNextClick()

        verify {
            mockCurrentItem.onChanged(2)
        }
    }

    @Test
    fun `when next button clicked and current item is the last item must not increment it`() {
        sut.currentItem.value = 2
        sut.onNextClick()

        verify {
            mockCurrentItem.onChanged(2)
        }
    }

    @Test
    fun `when current screen is last item must show ok button`() {
        sut.handleOkButtonVisibility(2)

        assertThat(sut.okButtonVisibility.get(), `is`(View.VISIBLE))
    }

    @Test
    fun `when current screen is not last item must hide ok button`() {
        sut.handleOkButtonVisibility(1)

        assertThat(sut.okButtonVisibility.get(), `is`(View.GONE))

        sut.handleOkButtonVisibility(3)

        assertThat(sut.okButtonVisibility.get(), `is`(View.GONE))
    }
}