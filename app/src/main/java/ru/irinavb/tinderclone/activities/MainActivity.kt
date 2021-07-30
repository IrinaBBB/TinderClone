package ru.irinavb.tinderclone.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.databinding.ActivityMainBinding
import ru.irinavb.tinderclone.fragments.AccountFragment
import ru.irinavb.tinderclone.fragments.MatchesFragment
import ru.irinavb.tinderclone.fragments.MessengerFragment
import ru.irinavb.tinderclone.interfaces.TinderCallback
import ru.irinavb.tinderclone.util.DATA_USERS
import java.io.ByteArrayOutputStream
import java.io.IOException

const val REQUEST_CODE_PHOTO = 1234

class MainActivity : AppCompatActivity(), TinderCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val matchesFragment = MatchesFragment()
    private val messengerFragment = MessengerFragment()
    private val accountFragment = AccountFragment()

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userId = firebaseAuth.currentUser?.uid

    private lateinit var userDatabase: DatabaseReference

    private var resultImageUrl: Uri? = null


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
                R.id.matchesFragment -> {
                    matchesFragment.setCallback(this@MainActivity)
                    replaceFragment(matchesFragment)
                }
                R.id.messengerFragment -> {
                    messengerFragment.setCallback(this@MainActivity)
                    replaceFragment(messengerFragment)
                }
                R.id.accountFragment -> {
                    accountFragment.setCallback(this@MainActivity)
                    replaceFragment(accountFragment)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {
            resultImageUrl = data?.data
            storeImage()
        }
    }

    private fun storeImage() {
        if (resultImageUrl != null && userId != null) {
            val filePath = FirebaseStorage.getInstance()
                .reference.child("ProfileImage")
                .child(userId)
            var bitmap: Bitmap? = null
            try {
                bitmap = MediaStore.Images.Media.getBitmap(application.contentResolver,
                    resultImageUrl)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, baos)
            val data = baos.toByteArray()

            val uploadTask = filePath.putBytes(data)
            uploadTask.addOnFailureListener{ e -> e.printStackTrace() }
            uploadTask.addOnSuccessListener { _ ->
                filePath.downloadUrl
                    .addOnSuccessListener { uri ->
                        accountFragment?.updateImageUri(uri.toString())
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                    }
            }
        }
    }

    private fun initDatabase() {
        userDatabase = FirebaseDatabase
            .getInstance("https://tinderclone-bd785-default-rtdb.europe-west1.firebasedatabase.app")
            .reference.child(DATA_USERS)
    }

    override fun profileComplete() {
        replaceFragment(matchesFragment)
        binding.mainNavView.selectedItemId = R.id.matchesFragment
    }

    override fun startActivityForPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_PHOTO)
    }

    companion object {
        fun newIntent(context: Context?) = Intent(context, MainActivity::class.java)
    }

}