package com.example.nasagalleryapp.presentation.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nasagalleryapp.R

class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var albumImage: ImageView
    var userName: TextView

    init {
        albumImage = view.findViewById(R.id.albumImage)
        userName = view.findViewById(R.id.userName)
    }
}