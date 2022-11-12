package com.example.nasagalleryapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.nasagalleryapp.R
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.example.nasagalleryapp.presentation.view_holders.GalleryViewHolder

class GalleryListAdapter(
    private val galleryItemList: MutableList<NasaGalleryDataItem>,
    val clickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(galleryItemList[position].url)
            .placeholder(R.drawable.no_image)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .fitCenter()
            .dontAnimate()
            .into(holder.albumImage)

        holder.albumImage.setOnClickListener {
            clickListener(position)
        }
    }

    override fun getItemCount() = galleryItemList.size
}