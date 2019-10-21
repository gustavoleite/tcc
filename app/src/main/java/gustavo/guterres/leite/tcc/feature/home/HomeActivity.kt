package gustavo.guterres.leite.tcc.feature.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
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
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.feature.level.LevelActivity.Companion.STUDENT_EXTRA_ARG
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
        if (requestCode == HOME_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.saveStudentData(data?.getParcelableExtra(STUDENT_EXTRA_ARG)!!)
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
        listAdapter.setItemCallback { level ->
            viewModel.fetchLevelDetail(level.id)
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            levelList.observe(this@HomeActivity, Observer {
                listAdapter.list = it
            })
            level.observe(this@HomeActivity, Observer { level ->
                level?.onboardings?.let {
                    startActivity(
                        LevelOnboardingActivity.newInstance(
                            this@HomeActivity,
                            level,
                            viewModel.authenticatedStudent!!
                        )
                    )
                } ?: navigateToLevel(level)
            })
            requestInfo.observe(this@HomeActivity, Observer {
                showMessage(it)
            })
        }
    }

    fun navigateToLevel(level: Level) {
        val intent =
            LevelActivity.newInstance(this@HomeActivity, level, viewModel.authenticatedStudent!!)
        // Pass data object in the bundle and populate details activity.
        //intent.putExtra(DetailsActivity.EXTRA_CONTACT, contact)
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, home_total_points, "points")
        startActivityForResult(intent, HOME_REQUEST_CODE, options.toBundle())
    }

    private fun showMessage(message: String) {
        Snackbar
            .make(binding.homeMoneyIcon, message, Snackbar.LENGTH_LONG)
            .show()
    }

    companion object {
        const val HOME_REQUEST_CODE = 1
    }
}