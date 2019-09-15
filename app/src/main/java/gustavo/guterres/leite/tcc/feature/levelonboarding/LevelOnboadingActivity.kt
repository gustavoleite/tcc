package gustavo.guterres.leite.tcc.feature.levelonboarding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Onboarding
import gustavo.guterres.leite.tcc.databinding.ActivityLevelOnboardingBinding

class LevelOnboadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLevelOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setupBinding()
        val pagerAdapter = LevelOnboardingPageAdapter(supportFragmentManager, arrayListOf(Onboarding("1", "oi", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."), Onboarding("1", "oi", "eu sou um page adapter"), Onboarding("1", "oi", "eu sou um page adapter")))
        binding.apply {
            levelOnboardingViewPager.adapter = pagerAdapter
            levelOnboardingDots.setViewPager(levelOnboardingViewPager)
        }
    }

    private fun setupBinding() {
        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_level_onboarding)
    }
}