package gustavo.guterres.leite.tcc.feature.levelonboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.model.Onboarding
import gustavo.guterres.leite.tcc.databinding.ActivityLevelOnboardingBinding
import gustavo.guterres.leite.tcc.feature.level.LevelActivity
import org.koin.core.parameter.parametersOf
import org.koin.android.viewmodel.ext.android.viewModel

class LevelOnboardingActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private lateinit var binding: ActivityLevelOnboardingBinding
    private val totalScreens: Int? by lazy {
        intent?.extras?.getParcelable<Level>(LEVEL_ONBOARDING_EXTRA_ARG)?.onboardings?.size
    }
    private val viewModel: LevelOnboardingViewModel by viewModel {
        parametersOf(totalScreens)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setupBinding()
        setupObserver()

        binding.apply {
            viewModel = this@LevelOnboardingActivity.viewModel
            levelOnboardingViewPager.adapter = setupPageAdapter()
            levelOnboardingViewPager.addOnPageChangeListener(this@LevelOnboardingActivity)
            levelOnboardingDots.setViewPager(levelOnboardingViewPager)
        }
    }

    private fun setupPageAdapter(): LevelOnboardingPageAdapter {
        return LevelOnboardingPageAdapter(
            supportFragmentManager,
            intent?.extras?.getParcelable<Level>(LEVEL_ONBOARDING_EXTRA_ARG)?.onboardings!!
        )
    }

    private fun setupBinding() {
        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_level_onboarding)
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        viewModel.handleOkButtonVisibility(position)
    }

    private fun setupObserver() {
        viewModel.okClick.observe(this, Observer {
            intent?.extras?.getParcelable<Level>(LEVEL_ONBOARDING_EXTRA_ARG)?.let {
                startActivity(
                    LevelActivity.newInstance(
                        this,
                        it
                    )
                )
                finish()
            }
        })
    }

    companion object {

        private const val LEVEL_ONBOARDING_EXTRA_ARG = "LEVEL_ONBOARDING_EXTRA_ARG"

        fun newInstance(from: AppCompatActivity, level: Level): Intent {

            return Intent(from, LevelOnboardingActivity::class.java).apply {
                putExtra(LEVEL_ONBOARDING_EXTRA_ARG, level)
            }
        }
    }
}