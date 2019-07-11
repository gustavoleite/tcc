package gustavo.guterres.leite.tcc.feature.level

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.Slide
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
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

        val level = intent?.extras?.getParcelable<Level>(LEVEL_EXTRA_ARG)

        buildSteps(level ?: throw Exception("Level not found"))
        viewModel.setup(level)
    }

    private fun setupBinding() {
        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_level)

        binding.viewModel = viewModel
    }

    private fun setupObservers() {
        with(viewModel) {
            close.observe(this@LevelActivity, Observer {
                finish()
            })
        }
    }

    private fun buildSteps(level: Level) {
        stepsFragment = StepBuilder().getFragmentList(level.steps!!) {
            navigateToNextStep()
        }
        replaceFragment(stepsFragment.first())
    }

    private fun navigateToNextStep() {
        with(viewModel) {
            currentStep.set(currentStep.get().inc())
            if (stepsFragment.size >= currentStep.get()) {
                replaceFragment(stepsFragment[currentStep.get() - 1])
            } else {
                finish()
            }
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

    companion object {

        private const val LEVEL_EXTRA_ARG = "LEVEL_EXTRA_ARG"

        fun newInstance(from: AppCompatActivity, level: Level): Intent {

            return Intent(from, LevelActivity::class.java).apply {
                putExtra(LEVEL_EXTRA_ARG, level)
            }
        }
    }
}