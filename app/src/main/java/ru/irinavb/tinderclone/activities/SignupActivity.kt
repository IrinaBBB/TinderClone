package ru.irinavb.tinderclone.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import ru.irinavb.tinderclone.databinding.ActivitySignupBinding
import ru.irinavb.tinderclone.util.DATA_USERS
import ru.irinavb.tinderclone.util.User

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

    private val firebaseDatabase = FirebaseDatabase.getInstance("https://tinderclone-bd785-default-rtdb.europe-west1.firebasedatabase.app").reference

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
        val password = binding.passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.d(TAG, "Signup error ${task.exception?.localizedMessage}")
                        Toast.makeText(
                            this,
                            "Signup error ${task.exception?.localizedMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Log.d(TAG, "Hello")
                        val userEmail = binding.emailEditText.text.toString()
                        val userId = firebaseAuth.currentUser?.uid ?: ""
                        val user = User(uid = userId, email = userEmail)
                        Log.d(TAG, user.toString())
                        firebaseDatabase.child(DATA_USERS).child(userId).setValue(user)
                    }
                }
        }
    }

    companion object {
        private const val TAG = "SignupActivity"
        fun newIntent(context: Context?) = Intent(context, SignupActivity::class.java)
    }
}