package gustavo.guterres.leite.tcc.feature.level

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.databinding.ActivityLevelBinding
import gustavo.guterres.leite.tcc.feature.step.StepBuilder
import gustavo.guterres.leite.tcc.feature.step.StepFragment
import org.koin.android.viewmodel.ext.android.getViewModel

class LevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLevelBinding
    private lateinit var stepsFragment: List<StepFragment>
    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setupBinding()
        setupObserver()
      //  replaceFragment(StepFragment())
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("ActionOutput", null).show()
    }

    private fun setupBinding() {
        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_level)

        binding.viewModel = getViewModel()
    }

    private fun setupObserver() {
        binding.viewModel?.levels?.observe(this, Observer { levels ->
            levels?.let {
                stepsFragment = StepBuilder().getFragmentList(it.first().steps) {
                    navigateToNextStep()
                }
                replaceFragment(stepsFragment.first())
            }
        })
    }

    private fun navigateToNextStep() {
        currentStep++
        replaceFragment(stepsFragment[currentStep])
    }

    private fun replaceFragment(newFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.level_container_view, newFragment)
            .addToBackStack(null)
            .commit()
    }
}