package ru.irinavb.tinderclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.interfaces.TinderCallback

class MessengerFragment : Fragment() {

    private var callback: TinderCallback? = null

    fun setCallback(callback: TinderCallback) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messenger, container, false)
    }
}