package ru.irinavb.tinderclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.databinding.FragmentMatchesBinding
import java.util.ArrayList

class MatchesFragment : Fragment() {

    private var matchesFragmentBinding: FragmentMatchesBinding? = null

    private var al = ArrayList<String>()
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMatchesBinding.bind(view)
        matchesFragmentBinding = binding

        al = ArrayList<String>()
        al.add("php")
        al.add("c")
        al.add("python")
        al.add("java")

        arrayAdapter = ArrayAdapter<String>(this.requireContext(), R.layout.item, R.id.helloText, al)

        binding.frame.adapter = arrayAdapter
        binding.frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                Log.d("Matches", "removed object!")
                al.removeAt(0)
                arrayAdapter?.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                Toast.makeText(activity, "Left!", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(dataObject: Any) {
                Toast.makeText(activity, "Right!", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                al.add("XML $i")
                arrayAdapter?.notifyDataSetChanged()
                Log.d("Matches", "notified")
                i++
            }

            override fun onScroll(p0: Float) {
            }
        })
    }
}