package gustavo.guterres.leite.tcc.feature.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.feature.home.HomeActivity
import gustavo.guterres.leite.tcc.feature.origination.OriginationActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: gustavo.guterres.leite.tcc.databinding.ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupObservers()
    }

    private fun setupBinding() {
        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_login)

        binding.viewModel = viewModel
    }

    private fun setupObservers() {
        with(viewModel) {
            message.observe(this@LoginActivity, Observer {
                showMessage(it)
            })
            navigation.observe(this@LoginActivity, Observer {
                handleNavigation(it)
            })
        }
    }

    private fun showMessage(message: String) {
        Snackbar
            .make(binding.loginButton, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun handleNavigation(loginNavigation: LoginNavigation) {
        when(loginNavigation) {
            LoginNavigation.CREATE_ACCCOUNT -> navigateToCreateAccount()
            LoginNavigation.LOGIN -> navigateToHome()
        }
    }

    private fun navigateToCreateAccount() {
        startActivity(Intent(this, OriginationActivity::class.java))
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}