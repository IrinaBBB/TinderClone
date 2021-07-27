package ru.irinavb.tinderclone.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.databinding.ActivityMainBinding
import ru.irinavb.tinderclone.fragments.AccountFragment
import ru.irinavb.tinderclone.fragments.MatchesFragment
import ru.irinavb.tinderclone.fragments.MessengerFragment
import ru.irinavb.tinderclone.interfaces.TinderCallback
import ru.irinavb.tinderclone.util.DATA_USERS


class MainActivity : AppCompatActivity(), TinderCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val matchesFragment = MatchesFragment()
    private val messengerFragment = MessengerFragment()
    private val accountFragment = AccountFragment()

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userId = firebaseAuth.currentUser?.uid

    private lateinit var userDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        initDatabase()
        setUpBottomNavBar()

        if (userId.isNullOrEmpty()) {
            onSingOut()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onSingOut() {
        firebaseAuth.signOut()
        startActivity(StartupActivity.newIntent(this))
        finish()
    }

    override fun onGetUserId(): String = userId!!

    override fun getUserDatabase(): DatabaseReference = userDatabase

    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setUpBottomNavBar() {
        replaceFragment(matchesFragment)

        binding.mainNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.matchesFragment -> replaceFragment(matchesFragment)
                R.id.messengerFragment -> replaceFragment(messengerFragment)
                R.id.accountFragment -> replaceFragment(accountFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.commit()
    }

    private fun initDatabase() {
        userDatabase = FirebaseDatabase.getInstance().reference.child(DATA_USERS)
    }

    companion object {
        fun newIntent(context: Context?) = Intent(context, MainActivity::class.java)
    }

}