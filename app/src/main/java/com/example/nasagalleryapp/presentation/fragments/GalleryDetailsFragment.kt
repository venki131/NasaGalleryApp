package com.example.nasagalleryapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasagalleryapp.databinding.FragmentGalleryDetailsBinding
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.example.nasagalleryapp.presentation.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryDetailsFragment : Fragment() {

    private var _binding: FragmentGalleryDetailsBinding? = null

    private val binding get() = _binding!!
    private var galleryList: MutableList<NasaGalleryDataItem> = arrayListOf()
    private var pos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            galleryList = it.getParcelableArrayList("GalleryItem")!!
            pos = it.getInt("position")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewpager = binding.viewPager
        val viewPagerAdapter = ViewPagerAdapter(requireContext(), galleryList)
        viewpager.adapter = viewPagerAdapter
        viewpager.currentItem = pos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}