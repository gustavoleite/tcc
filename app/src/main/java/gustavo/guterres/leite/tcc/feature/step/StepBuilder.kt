package gustavo.guterres.leite.tcc.feature.step

import gustavo.guterres.leite.tcc.data.entity.model.Step

class StepBuilder {

    fun getFragmentList(steps: List<Step>, onStepFinished: (isRightAnswer: Boolean, points: Int) -> Unit): List<StepFragment> {

        val fragments = mutableListOf<StepFragment>()

        for (step in steps) {
            fragments.add(StepFragment.newInstance(step, onStepFinished))
        }

        return fragments
    }

}