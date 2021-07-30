package ru.irinavb.tinderclone.interfaces

import com.google.firebase.database.DatabaseReference

interface TinderCallback  {

    fun onSingOut()
    fun onGetUserId(): String
    fun getUserDatabase(): DatabaseReference
    fun profileComplete()
}

/* FOR GETTING SHA FOR FIREBASE
*  keytool -list -v -keystore C:\Users\Bruker\.android\debug.keystore -alias androiddebugkey -storepass android -keypass android
*/