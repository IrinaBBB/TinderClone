package ru.irinavb.tinderclone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import ru.irinavb.tinderclone.R

class AccountFragment : Fragment() {

    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // (activity as MainActivity?)?.hello()
        return inflater.inflate(R.layout.fragment_account, container, false)
    }
}