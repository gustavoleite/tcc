package gustavo.guterres.leite.tcc.feature

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.components.content.ContentViewItem
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val binding = DataBindingUtil.setContentView<gustavo.guterres.leite.tcc.databinding.ActivityHomeBinding>(this, R.layout.activity_home)
        binding.viewModel = HomeViewModel()
        binding.homeActionView.setAvOnClick { action ->
            Toast.makeText(this, "Botao clicado: "+action.id, Toast.LENGTH_SHORT).show()
        }


//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
    }
}