package gustavo.guterres.leite.tcc.feature

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.feature.step.StepFragment


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val binding = DataBindingUtil.setContentView<gustavo.guterres.leite.tcc.databinding.ActivityHomeBinding>(this, R.layout.activity_home)
        binding.viewModel = HomeViewModel()


        // Create new fragment and transaction
        val newFragment = StepFragment()
        val transaction = supportFragmentManager.beginTransaction()

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.home_container_view, newFragment)
        transaction.addToBackStack(null)

// Commit the transaction
        transaction.commit()


//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
    }
}