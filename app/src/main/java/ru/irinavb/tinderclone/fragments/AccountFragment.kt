package ru.irinavb.tinderclone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.databinding.FragmentAccountBinding
import ru.irinavb.tinderclone.interfaces.TinderCallback
import ru.irinavb.tinderclone.util.*

class AccountFragment : Fragment() {

    private var callback: TinderCallback? = null
    private var afb: FragmentAccountBinding? = null

    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference

    fun setCallback(callback: TinderCallback) {
        this.callback = callback
        userId = callback.onGetUserId()
        userDatabase = callback.getUserDatabase().child(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAccountBinding.bind(view)
        afb = binding

        binding.progressBar.visibility = View.VISIBLE
        binding.accountScrollView.visibility = View.GONE
        populateInfo()

        binding.applyButton.setOnClickListener { onApply() }
        binding.signOutButton.setOnClickListener { callback?.onSingOut() }
        binding.profileImage.setOnClickListener{ callback?.startActivityForPhoto() }

    }

    private fun populateInfo() {
        afb?.progressBar?.visibility = View.VISIBLE
        afb?.accountScrollView?.visibility = View.GONE

        userDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) {
                    val user = snapshot.getValue(User::class.java)
                    afb?.emailTextInputEditText?.setText(user?.email, TextView
                        .BufferType.EDITABLE)
                    afb?.nameTextInputEditText?.setText(user?.name, TextView
                        .BufferType.EDITABLE)
                    afb?.ageTextInputEditText?.setText(user?.age, TextView
                        .BufferType.EDITABLE)
                    if (user?.gender == GENDER_MALE) {
                        afb?.genderMaleRadioButton?.isChecked = true
                    }
                    if (user?.gender == GENDER_FEMALE) {
                        afb?.genderFemaleRadioButton?.isChecked = true
                    }

                    if (user?.preferredGender == GENDER_MALE) {
                        afb?.interestedInMaleRadioButton?.isChecked = true
                    }
                    if (user?.preferredGender == GENDER_FEMALE) {
                        afb?.interestedInFemaleRadioButton?.isChecked = true
                    }

                    if(!user?.imageUrl.isNullOrEmpty()) {
                        populateImage(user?.imageUrl!!)
                    }
                    afb?.progressBar?.visibility = View.GONE
                    afb?.accountScrollView?.visibility = View.VISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                afb?.progressBar?.visibility = View.VISIBLE
                afb?.accountScrollView?.visibility = View.GONE
            }
        })
    }

    private fun onApply() {

        val email = afb?.emailTextInputEditText?.text.toString()
        val name = afb?.nameTextInputEditText?.text.toString()
        val age = afb?.ageTextInputEditText?.text.toString()
        val genderRadioGroup = afb?.yourGenderRadioGroup?.checkedRadioButtonId
        val interestedInRadioGroup = afb?.interestedInRadioGroup?.checkedRadioButtonId

        if (email.isEmpty() || name.isEmpty() || age.isEmpty() ||
                genderRadioGroup == -1 || interestedInRadioGroup == -1) {

            Toast.makeText(context, getString(R.string.error_profile_incomplete), Toast.LENGTH_LONG)
                .show()

        } else {
            val gender =
                if (afb?.genderFemaleRadioButton!!.isChecked) GENDER_FEMALE else GENDER_MALE

            val interestedIn =
                if (afb?.interestedInFemaleRadioButton!!.isChecked) GENDER_FEMALE else GENDER_MALE

            userDatabase.child(DATA_EMAIL).setValue(email)
            userDatabase.child(DATA_NAME).setValue(name)
            userDatabase.child(DATA_AGE).setValue(age)
            userDatabase.child(DATA_GENDER).setValue(gender)
            userDatabase.child(DATA_GENDER_PREFERENCE).setValue(interestedIn)

            callback?.profileComplete()
        }
    }

    fun updateImageUri(uri: String) {
        userDatabase.child(DATA_IMAGE_URL).setValue(uri)
        populateImage(uri)
    }

    fun populateImage(uri: String) {
        afb?.profileImage?.let {
            Glide.with(this)
                .load(uri)
                .into(it)
        }
    }


}