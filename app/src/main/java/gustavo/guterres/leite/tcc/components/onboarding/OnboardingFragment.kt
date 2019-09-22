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
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.ParameterDefinition
import org.koin.core.parameter.parametersOf

class OnboardingFragment : Fragment() {

    private val extraOnboarding : Onboarding? by lazy { arguments?.getParcelable<Onboarding>(ONBOARDING_EXTRA_ARG) }
    private val viewModel: OnboardingViewModel by inject { parametersOf(extraOnboarding) }

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
            viewModel = this@OnboardingFragment.viewModel
        }.root
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