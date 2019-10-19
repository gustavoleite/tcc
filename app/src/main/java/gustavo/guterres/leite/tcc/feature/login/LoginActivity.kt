package gustavo.guterres.leite.tcc.feature.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.feature.home.HomeActivity
import org.koin.android.viewmodel.ext.android.viewModel
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AdapterView
import android.view.View


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: gustavo.guterres.leite.tcc.databinding.ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupObservers()
        viewModel.setupFirebaseAuth()
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
            schools.observe(this@LoginActivity, Observer {
                binding.loginSchoolSpinner.apply {
                    setupSpinner(this, it)
                    onItemSelectedListener = onItemSelectedListener {
                        viewModel.loadClassroomsOptions(selectedItem as String)
                    }
                }
            })
            classrooms.observe(this@LoginActivity, Observer {
                binding.loginClassroomSpinner.apply {
                    setupSpinner(this, it)
                    onItemSelectedListener = onItemSelectedListener {
                        viewModel.loadStudentOptions(selectedItem as String)
                    }
                }
            })
            students.observe(this@LoginActivity, Observer {
                binding.loginStudentSpinner.apply {
                    setupSpinner(this, it)
                    onItemSelectedListener = onItemSelectedListener {
                        viewModel.selectedStudent = selectedItem as String
                    }
                }
            })
        }
    }

    private fun onItemSelectedListener(listener: () -> Unit): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                listener.invoke()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun showMessage(message: String) {
        Snackbar
            .make(binding.loginButton, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun handleNavigation(loginNavigation: LoginNavigation) {
        when (loginNavigation) {
            LoginNavigation.LOGIN -> navigateToHome()
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    fun setupSpinner(spinner: Spinner, items: List<String>) {
        val spinnerArrayAdapter = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item,
            items
        )
        spinnerArrayAdapter.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item
        )
        spinner.adapter = spinnerArrayAdapter
    }
}