package gustavo.guterres.leite.tcc.feature.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.databinding.ActivityHomeBinding
import gustavo.guterres.leite.tcc.feature.level.LevelActivity
import gustavo.guterres.leite.tcc.feature.levelonboarding.LevelOnboardingActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.ObservableInt
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.model.PlayLevel
import gustavo.guterres.leite.tcc.feature.login.LoginActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val listAdapter: LevelItemAdapter by inject()
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setupBinding()
        setupRecyclerView()
        setupObservers()
        viewModel.setup()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == HOME_LEVEL_REQUEST_CODE) {
                viewModel.saveStudentData(data?.getParcelableExtra(LevelActivity.STUDENT_EXTRA_ARG)!!)
            } else if (requestCode == HOME_ONBOARDING_REQUEST_CODE) {
                Handler().postDelayed({
                    navigateToLevel(data?.getParcelableExtra<PlayLevel>(LevelOnboardingActivity.LEVEL_ONBOARDING_EXTRA_ARG)?.level!!)
                }, 250)
            }
        }

    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = viewModel
    }

    private fun setupRecyclerView() {
        binding.homeRecyclerView.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 3)
            adapter = listAdapter
        }
        listAdapter.setItemCallback { level, isEnabled ->
            if (isEnabled) {
                viewModel.fetchLevelDetail(level.id)
            } else {
                showMessage("Acerte ao menos 3 questões no nível anterior para desbloquear este nível.")
            }
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            levelList.observe(this@HomeActivity, Observer {
                listAdapter.list = it
            })
            level.observe(this@HomeActivity, Observer { level ->
                level?.onboardings?.let {
                    navigeToOnboarding(level)
                } ?: navigateToLevel(level)
            })
            requestInfo.observe(this@HomeActivity, Observer {
                showMessage(it)
            })
            logout.observe(this@HomeActivity, Observer {
                startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                finish()
            })
        }
    }

    fun navigeToOnboarding(level: Level) {
        startActivityForResult(
            LevelOnboardingActivity.newInstance(
                this,
                PlayLevel(
                    level,
                    viewModel.authenticatedStudent!!,
                    (level.id.toInt() + 1).toString()
                )
            ),
            HOME_ONBOARDING_REQUEST_CODE
        )
    }

    fun navigateToLevel(level: Level) {
        val intent =
            LevelActivity.newInstance(
                this,
                PlayLevel(
                    level,
                    viewModel.authenticatedStudent!!,
                    (level.id.toInt() + 1).toString()
                )
            )

        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, home_total_points, "points")
        startActivityForResult(intent, HOME_LEVEL_REQUEST_CODE, options.toBundle())
    }

    private fun showMessage(message: String) {
        Snackbar
            .make(binding.homeMoneyIcon, message, Snackbar.LENGTH_LONG)
            .show()
    }

    companion object {
        const val HOME_LEVEL_REQUEST_CODE = 1
        const val HOME_ONBOARDING_REQUEST_CODE = 2
    }
}