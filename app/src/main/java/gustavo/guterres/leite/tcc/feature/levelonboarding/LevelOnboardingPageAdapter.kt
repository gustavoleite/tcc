package gustavo.guterres.leite.tcc.feature.levelonboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import gustavo.guterres.leite.tcc.components.onboarding.OnboardingFragment
import gustavo.guterres.leite.tcc.data.entity.model.Onboarding

class LevelOnboardingPageAdapter(
    fragmentManager: FragmentManager,
    private val onboadingList: ArrayList<Onboarding>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return OnboardingFragment.newInstance(onboadingList[position])
    }

    override fun getCount(): Int {
        return onboadingList.size
    }
}