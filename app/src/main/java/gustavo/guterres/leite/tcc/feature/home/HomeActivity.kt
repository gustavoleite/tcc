package gustavo.guterres.leite.tcc.feature.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.databinding.ActivityHomeBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val listAdapter: LevelItemAdapter by inject()
    private val viewModel: HomeViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = viewModel
        setupList()

//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("ActionOutput", null).show()
    }

    private fun setupList() {
        binding.homeRecyclerView.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 3)
            adapter = listAdapter
        }

        listAdapter.list = listOf(
            Level("asd", "As notas", "1"),
            Level("asd", "Soma", "2"),
            Level("asd", "Preços", "3"),
            Level("asd", "Compras", "4"),
            Level("asd", "Troco", "5")
        )
    }
}