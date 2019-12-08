package gustavo.guterres.leite.tcc.feature

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.feature.home.HomeActivity
import gustavo.guterres.leite.tcc.feature.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupFirebaseAuth()
    }

    fun setupFirebaseAuth() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser?.isAnonymous == true) {
            currentUser.delete()
            FirebaseAuth.getInstance().signOut()
        }

        Handler().postDelayed({
            FirebaseAuth.getInstance().currentUser?.let {
                navigateToHome()
            } ?: navigateToLogin()
        }, 2000)
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
