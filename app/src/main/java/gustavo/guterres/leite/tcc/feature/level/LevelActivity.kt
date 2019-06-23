package gustavo.guterres.leite.tcc.feature.level

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.databinding.ActivityLevelBinding
import gustavo.guterres.leite.tcc.feature.step.StepFragment
import org.koin.android.viewmodel.ext.android.getViewModel

class LevelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setupBinding().viewModel = getViewModel()
        replaceFragment(StepFragment())
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("ActionOutput", null).show()
    }

    private fun replaceFragment(newFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.level_container_view, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupBinding(): ActivityLevelBinding {
        return DataBindingUtil
            .setContentView(this, R.layout.activity_level)
    }
}