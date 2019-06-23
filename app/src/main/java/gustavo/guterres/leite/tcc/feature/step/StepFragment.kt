package gustavo.guterres.leite.tcc.feature.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Action
import gustavo.guterres.leite.tcc.data.entity.model.Step
import gustavo.guterres.leite.tcc.databinding.FragmentStepBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class StepFragment : Fragment() {

    private lateinit var binding: FragmentStepBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupBinding(inflater, container)
        setupViewModel()

        return binding.root
    }

    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_step,
            container,
            false
        )

        binding.viewModel = getViewModel()
        binding.stepActionView.setAvOnClick(onStepFinishedCallback)
    }

    private fun setupViewModel() {
        binding.viewModel?.setup(
            arguments?.getParcelable(STEP_EXTRA_ARG)
                ?: throw Exception("Argument step not found")
        )
    }

    companion object {

        private const val STEP_EXTRA_ARG = "STEP_EXTRA_ARG"
        private var onStepFinishedCallback: ((Action) -> Unit)? = null

        fun newInstance(step: Step, onStepFinished: (Action) -> Unit): StepFragment {

            onStepFinishedCallback = onStepFinished

            return StepFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(STEP_EXTRA_ARG, step)
                }
            }
        }
    }
}