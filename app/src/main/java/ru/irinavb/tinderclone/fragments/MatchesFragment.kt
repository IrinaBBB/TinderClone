package ru.irinavb.tinderclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.adapters.CardsAdapter
import ru.irinavb.tinderclone.databinding.FragmentMatchesBinding
import ru.irinavb.tinderclone.interfaces.TinderCallback
import ru.irinavb.tinderclone.util.*
import java.util.ArrayList

class MatchesFragment : Fragment() {

    private var callback: TinderCallback? = null

    private var mfb: FragmentMatchesBinding? = null


    private lateinit var userDatabase: DatabaseReference
    private lateinit var userId: String

    private var cardsAdapter: CardsAdapter? = null
    private var rowItems = ArrayList<User>()

    private var preferredGender: String? = null

    fun setCallback(callback: TinderCallback) {
        this.callback = callback
        userDatabase = callback.getUserDatabase()
        userId = callback.onGetUserId()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMatchesBinding.bind(view)
        mfb = binding

        userDatabase.child(userId).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                preferredGender = user?.preferredGender
                populateItems()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        cardsAdapter = CardsAdapter(this.requireContext(), R.layout.item, rowItems)

        mfb?.frame?.adapter = cardsAdapter
        mfb?.frame?.setFlingListener(object: SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                rowItems.removeAt(0)
                if(rowItems.isEmpty()) {
                    mfb?.noUsersCardView?.visibility = View.VISIBLE
                    mfb?.contentGroup?.visibility = View.GONE
                }
                cardsAdapter?.notifyDataSetChanged()

            }

            override fun onLeftCardExit(p0: Any?) {
            }

            override fun onRightCardExit(p0: Any?) {
            }

            override fun onAdapterAboutToEmpty(p0: Int) {
            }

            override fun onScroll(p0: Float) {
            }

        })
    }

    fun populateItems() {
//        mfb?.noUsersCardView?.visibility = View.GONE
//        mfb?.progressBar?.visibility = View.VISIBLE
//        mfb?.contentGroup?.visibility = View.GONE

        val cardsQuery = userDatabase.orderByChild(DATA_GENDER).equalTo(preferredGender)
        cardsQuery.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { child ->
                    val user = child.getValue(User::class.java)
                    if (user != null) {
                        var showUser = true
                        if (child.child(DATA_SWIPES_LEFT).hasChild(userId) ||
                            child.child(DATA_SWIPES_RIGHT).hasChild(userId) ||
                                child.child(DATA_MATCHES).hasChild(userId)) {
                            showUser = false
                        }

                        if (showUser) {
                            rowItems.add(user)
                            cardsAdapter?.notifyDataSetChanged()
                        }
                    }
                }

                mfb?.progressBar?.visibility = View.GONE

                if (rowItems.isEmpty()) {
                    mfb?.noUsersCardView?.visibility = View.VISIBLE
                } else {
                    mfb?.contentGroup?.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}