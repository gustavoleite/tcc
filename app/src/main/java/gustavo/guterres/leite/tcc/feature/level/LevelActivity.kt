package gustavo.guterres.leite.tcc.feature.level

import android.app.Activity
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
import gustavo.guterres.leite.tcc.data.entity.model.PlayLevel
import gustavo.guterres.leite.tcc.databinding.ActivityLevelBinding
import gustavo.guterres.leite.tcc.feature.step.StepBuilder
import gustavo.guterres.leite.tcc.feature.step.StepFragment
import gustavo.guterres.leite.tcc.utils.extensions.EventObserver
import org.koin.android.viewmodel.ext.android.viewModel

class LevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLevelBinding
    private val viewModel: LevelViewModel by viewModel()
    private lateinit var stepsFragment: List<StepFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setupBinding()

        val playLevel = intent?.extras?.getParcelable<PlayLevel>(LEVEL_EXTRA_ARG)

        buildSteps(playLevel?.level ?: throw Exception("Level not found"))
        viewModel.setup(playLevel)
        setupObservers()
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

            student.observe(this@LevelActivity, EventObserver {
                val intent = Intent().apply {
                    putExtra(STUDENT_EXTRA_ARG, it)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            })
        }
    }

    private fun buildSteps(level: Level) {
        stepsFragment = StepBuilder().getFragmentList(level.steps!!) { isRightAnswer: Boolean, points: Double ->
            viewModel.setUserAnswer(isRightAnswer, points)
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
                viewModel.updateStudentUser()
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

        const val LEVEL_EXTRA_ARG = "LEVEL_EXTRA_ARG"
        const val STUDENT_EXTRA_ARG = "STUDENT_EXTRA_ARG"

        fun newInstance(from: AppCompatActivity, playLevel: PlayLevel): Intent {

            return Intent(from, LevelActivity::class.java).apply {
                putExtra(LEVEL_EXTRA_ARG, playLevel)
            }
        }
    }
}