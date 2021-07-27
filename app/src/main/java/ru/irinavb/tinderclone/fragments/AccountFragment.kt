package ru.irinavb.tinderclone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.interfaces.TinderCallback

class AccountFragment : Fragment() {

    private var callback: TinderCallback? = null

    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference

    fun setCallback(callback: TinderCallback) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }
}