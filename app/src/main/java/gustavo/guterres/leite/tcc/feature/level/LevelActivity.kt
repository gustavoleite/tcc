package gustavo.guterres.leite.tcc.feature.level

import android.animation.Animator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
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
import gustavo.guterres.leite.tcc.utils.extensions.actionListToString
import org.koin.android.viewmodel.ext.android.viewModel

class LevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLevelBinding
    private val viewModel: LevelViewModel by viewModel()
    private lateinit var stepsFragment: List<StepFragment>
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setupBinding()

        val playLevel = intent?.extras?.getParcelable<PlayLevel>(LEVEL_EXTRA_ARG)

        buildSteps(playLevel?.level ?: throw Exception("Level not found"))
        viewModel.setup(playLevel)
        setupObservers()
        setupAnimation()
        textToSpeech = TextToSpeech(this, null).apply {
            setSpeechRate(0.9f)
        }
    }

    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()

        super.onDestroy()
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

            speak.observe(this@LevelActivity, Observer {
                intent?.extras?.getParcelable<PlayLevel>(LEVEL_EXTRA_ARG).run {
                    val currentStep = this?.level?.steps?.get(viewModel.currentStep.get().minus(1))
                    val tts = currentStep?.content?.description + currentStep?.actions?.actionListToString()
                    textToSpeech.speak(tts, TextToSpeech.QUEUE_FLUSH, null)
                }
            })
        }
    }

    private fun buildSteps(level: Level) {
        stepsFragment =
            StepBuilder().getFragmentList(level.steps!!) { isRightAnswer: Boolean, points: Int ->
                viewModel.setUserAnswer(isRightAnswer, points)
                showAnimation(isRightAnswer)
            }
        replaceFragment(stepsFragment.first())
    }

    private fun showAnimation(isRightAnswer: Boolean) {
        if (isRightAnswer) {
            playAnimation()
            Handler().postDelayed({
                navigateToNextStep()
            }, 1000)
        } else {
            navigateToNextStep()
        }
    }

    private fun navigateToNextStep() {
        with(viewModel) {
            currentStep.set(currentStep.get().inc())
            if (stepsFragment.size >= currentStep.get()) {
                replaceFragment(stepsFragment[currentStep.get() - 1])
            } else {
                if (viewModel.levelUp()) {
                    binding.levelAnimation.setAnimation(R.raw.level_up_animation)
                    playAnimation()
                    Handler().postDelayed({
                        viewModel.updateStudentUser()
                    }, 2000)
                } else {
                    viewModel.updateStudentUser()
                }
            }
        }
    }

    private fun playAnimation() {
        binding.levelAnimation.playAnimation()
        binding.levelAnimation.visibility = View.VISIBLE
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
        // @TODO Implement dialog
    }

    private fun setupAnimation() {
        binding.levelAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                binding.levelAnimation.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator) {
                binding.levelAnimation.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
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
