package ru.irinavb.tinderclone.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.databinding.ActivityMainBinding
import ru.irinavb.tinderclone.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signupButton.setOnClickListener{ onSignup() }
    }

    private fun onSignup() {
        startActivity(MainActivity.newIntent(this))
    }

    companion object {
        fun newIntent(context: Context?) = Intent(context, SignupActivity::class.java)
    }
}