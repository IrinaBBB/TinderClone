package ru.irinavb.tinderclone.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import ru.irinavb.tinderclone.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser
        if (user != null) {
            Log.d(TAG, user.toString())
            startActivity(MainActivity.newIntent(this))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signupButton.setOnClickListener { onSignup() }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    private fun onSignup() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.d(TAG, "Signup error ${task.exception?.localizedMessage}")
                        Toast.makeText(
                            this,
                            "Signup error ${task.exception?.localizedMessage}",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
        }
    }

    companion object {
        private const val TAG = "SignupActivity"
        fun newIntent(context: Context?) = Intent(context, SignupActivity::class.java)
    }
}