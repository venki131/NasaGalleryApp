package com.example.nasagalleryapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.nasagalleryapp.R
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import java.util.*


class ViewPagerAdapter(
    private val context: Context,
    private val galleryList: MutableList<NasaGalleryDataItem>
) : PagerAdapter() {

    override fun getCount() = galleryList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = layoutInflater.inflate(R.layout.image_slider_item, container, false)
        val imageView = itemView.findViewById<ImageView>(R.id.albumImage)
        Glide.with(context)
            .load(galleryList[position].hdUrl)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .fitCenter()
            .placeholder(R.drawable.no_image)
            .dontAnimate()
            .into(imageView)
        Objects.requireNonNull(container).addView(itemView)

        val title = itemView.findViewById<TextView>(R.id.title)
        title.text = galleryList[position].title

        val description = itemView.findViewById<TextView>(R.id.description)
        description.text = galleryList[position].explanation

        return itemView
    }

    // on below line we are creating a destroy item method.
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // on below line we are removing view
        container.removeView(`object` as ConstraintLayout)
    }
}