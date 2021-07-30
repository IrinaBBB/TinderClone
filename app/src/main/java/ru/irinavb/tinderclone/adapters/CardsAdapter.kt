package ru.irinavb.tinderclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.irinavb.tinderclone.R
import ru.irinavb.tinderclone.util.User

class CardsAdapter(context: Context, resourceId: Int, users: ArrayList<User>) : ArrayAdapter<User>
    (context, resourceId, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val user = getItem(position)
        val finalView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item,
            parent, false)
        val image = finalView.findViewById<ImageView>(R.id.user_photo_image_view)
        val name = finalView.findViewById<TextView>(R.id.user_name_text_view)
        val age = finalView.findViewById<TextView>(R.id.user_age_text_view)

        name.text = "${user?.name}"
        "${user?.age} years old".also { age.text = it }

        Glide.with(context)
            .load(user?.imageUrl)
            .into(image)

        return finalView
    }
}