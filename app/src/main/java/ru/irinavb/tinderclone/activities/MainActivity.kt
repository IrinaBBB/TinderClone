package ru.irinavb.tinderclone.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.mainNavView.setupWithNavController(navController)


//        al = ArrayList<String>()
//        al.add("php")
//        al.add("c")
//        al.add("python")
//        al.add("java")
//
//        arrayAdapter = ArrayAdapter<String>(this, R.layout.item, R.id.helloText, al)
//
//        binding.frame.adapter = arrayAdapter
//        binding.frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
//            override fun removeFirstObjectInAdapter() {
//                Log.d(TAG, "removed object!")
//                al.removeAt(0)
//                arrayAdapter?.notifyDataSetChanged()
//            }
//
//            override fun onLeftCardExit(dataObject: Any) {
//                Toast.makeText(this@MainActivity, "Left!", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onRightCardExit(dataObject: Any) {
//                Toast.makeText(this@MainActivity, "Right!", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
//                al.add("XML $i")
//                arrayAdapter?.notifyDataSetChanged()
//                Log.d(TAG, "notified")
//                i++
//            }
//
//            override fun onScroll(p0: Float) {
//            }
//        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        private const val TAG = "LIST"
        fun newIntent(context: Context?) = Intent(context, MainActivity::class.java)
    }
}