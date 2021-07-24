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

        // binding.login.setOnClickListener(onLogin(this))
    }

    fun onLogin(v: View) {

    }

    fun onSignup(v: View) {

    }
}