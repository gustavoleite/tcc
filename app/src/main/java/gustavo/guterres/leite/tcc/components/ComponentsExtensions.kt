package gustavo.guterres.leite.tcc.components

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar

fun ProgressBar.setProgressValueWithAnimation(progressTo: Int, animationDuration: Long = 1000) {
    ObjectAnimator.ofInt(
        this,
        "progress",
        this.progress,
        progressTo * 100
    )
        .apply {
            duration = animationDuration
            interpolator = DecelerateInterpolator()
            start()
        }
}