package gustavo.guterres.leite.tcc.feature.level

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.Slide
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.databinding.ActivityLevelBinding
import gustavo.guterres.leite.tcc.feature.step.StepBuilder
import gustavo.guterres.leite.tcc.feature.step.StepFragment
import org.koin.android.viewmodel.ext.android.viewModel

class LevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLevelBinding
    private val viewModel: LevelViewModel by viewModel()
    private lateinit var stepsFragment: List<StepFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setupBinding()
        setupObservers()
        //  replaceFragment(StepFragment())
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("ActionOutput", null).show()
    }

    private fun setupBinding() {
        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_level)

        binding.viewModel = viewModel

        Button(this)
    }

    private fun setupObservers() {
        with(viewModel) {
            levels.observe(this@LevelActivity, Observer { levels ->
                levels?.let {
                    stepsFragment = StepBuilder().getFragmentList(it.first().steps) {
                        navigateToNextStep()
                    }
                    replaceFragment(stepsFragment.first())
                }
            })
            close.observe(this@LevelActivity, Observer {
                finish()
            })
        }
    }

    private fun navigateToNextStep() {
        with(viewModel) {
            currentStep.set(currentStep.get().inc())
            replaceFragment(stepsFragment[currentStep.get() - 1])
        }
    }

    private fun replaceFragment(newFragment: Fragment) {
        with(newFragment) {
            enterTransition = Slide(Gravity.RIGHT)
            exitTransition = Slide(Gravity.LEFT)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.level_container_view, this)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        //@TODO Implement dialog
    }
}