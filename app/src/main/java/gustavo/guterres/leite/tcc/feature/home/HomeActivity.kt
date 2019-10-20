package gustavo.guterres.leite.tcc.feature.home

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
                    startActivity(LevelOnboardingActivity.newInstance(this@HomeActivity, level))
                } ?: startActivity(LevelActivity.newInstance(this@HomeActivity, level))
            })
            requestInfo.observe(this@HomeActivity, Observer {
                showMessage(it)
            })
        }
    }

    private fun showMessage(message: String) {
        Snackbar
            .make(binding.homeMoneyIcon, message, Snackbar.LENGTH_LONG)
            .show()
    }
}