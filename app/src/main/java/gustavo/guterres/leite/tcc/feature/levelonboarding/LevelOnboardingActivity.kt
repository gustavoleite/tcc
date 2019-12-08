package gustavo.guterres.leite.tcc.feature.levelonboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Onboarding
import gustavo.guterres.leite.tcc.data.entity.model.PlayLevel
import gustavo.guterres.leite.tcc.databinding.ActivityLevelOnboardingBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LevelOnboardingActivity : AppCompatActivity(), OnPageChangeListener {

    private lateinit var binding: ActivityLevelOnboardingBinding
    private val onboardingList: List<Onboarding> by lazy {
        intent?.extras?.getParcelable<PlayLevel>(LEVEL_ONBOARDING_EXTRA_ARG)?.level?.onboardings!!
    }

    private val totalScreens: Int? by lazy {
        intent?.extras?.getParcelable<PlayLevel>(LEVEL_ONBOARDING_EXTRA_ARG)
            ?.level?.onboardings?.size
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
            setupViewPager()
            levelOnboardingViewPager.addOnPageChangeListener(this@LevelOnboardingActivity)
            levelOnboardingDots.setViewPager(levelOnboardingViewPager)
        }
    }

    private fun ActivityLevelOnboardingBinding.setupViewPager() {
        levelOnboardingViewPager.adapter = buildPageAdapter()
        levelOnboardingViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                viewModel?.currentItem?.value = position
            }
        })
    }

    private fun buildPageAdapter(): LevelOnboardingPageAdapter {
        return LevelOnboardingPageAdapter(
            supportFragmentManager,
            onboardingList
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
            intent?.extras?.getParcelable<PlayLevel>(LEVEL_ONBOARDING_EXTRA_ARG)?.let {
                val intent = Intent().apply {
                    putExtra(LEVEL_ONBOARDING_EXTRA_ARG, it)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        })
        viewModel.backClick.observe(this, Observer {
            finish()
        })
        viewModel.currentItem.observe(this, Observer {
            it?.let {
                binding.levelOnboardingViewPager?.currentItem = it
            }
        })
    }

    companion object {

        const val LEVEL_ONBOARDING_EXTRA_ARG = "LEVEL_ONBOARDING_EXTRA_ARG"

        fun newInstance(from: AppCompatActivity, playLevel: PlayLevel): Intent {

            return Intent(from, LevelOnboardingActivity::class.java).apply {
                putExtra(LEVEL_ONBOARDING_EXTRA_ARG, playLevel)
            }
        }
    }
}
