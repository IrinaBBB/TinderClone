package ru.irinavb.tinderclone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.irinavb.tinderclone.databinding.ActivityStartupBinding

class StartupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityStartupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.login.setOnClickListener { onLogin() }
        binding.signup.setOnClickListener { onSignup() }
    }

    private fun onLogin() {
        startActivity(LoginActivity.newIntent(this))
    }

    private fun onSignup() {
        startActivity(SignupActivity.newIntent(this))
    }
}