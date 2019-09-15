package gustavo.guterres.leite.tcc.components.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Onboarding
import gustavo.guterres.leite.tcc.databinding.FragmentOnboardingBinding
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class OnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return DataBindingUtil.inflate<FragmentOnboardingBinding>(
            inflater,
            R.layout.fragment_onboarding,
            container,
            false
        ).apply {
            viewModel = provideViewModel()
        }.root
    }

    private fun provideViewModel(): OnboardingViewModel {
        return getViewModel(
            parameters = parametersOf(arguments?.getParcelable(ONBOARDING_EXTRA_ARG)).component1()
        )
    }

    companion object {

        private const val ONBOARDING_EXTRA_ARG = "ONBOARDING_EXTRA_ARG"

        fun newInstance(onboarding: Onboarding): OnboardingFragment {

            return OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ONBOARDING_EXTRA_ARG, onboarding)
                }
            }
        }
    }
}