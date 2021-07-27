package ru.irinavb.tinderclone.interfaces

import com.google.firebase.database.DatabaseReference

interface TinderCallback  {

    fun onSingOut()
    fun onGetUserId(): String
    fun getUserDatabase(): DatabaseReference

}