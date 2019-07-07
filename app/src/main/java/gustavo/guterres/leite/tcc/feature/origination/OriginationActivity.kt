package gustavo.guterres.leite.tcc.feature.origination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.databinding.ActivityOriginationBinding
import org.koin.android.viewmodel.ext.android.viewModel

class OriginationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOriginationBinding
    private val viewModel: OriginationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupObservers()
    }

    private fun setupBinding() {
        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_origination)

        binding.viewModel = viewModel
    }

    private fun setupObservers() {
        with(viewModel) {
            message.observe(this@OriginationActivity, Observer {
                showMessage(it)
            })
        }
    }

    private fun showMessage(message: String) {
        Snackbar
            .make(binding.originationEmail, message, Snackbar.LENGTH_SHORT)
            .show()
    }
}