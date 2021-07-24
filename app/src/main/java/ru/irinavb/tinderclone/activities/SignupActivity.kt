package ru.irinavb.tinderclone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }
}