package com.example.nasagalleryapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasagalleryapp.databinding.FragmentGalleryDetailsBinding
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.example.nasagalleryapp.presentation.adapters.ViewPagerAdapter
import com.example.nasagalleryapp.presentation.util.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryDetailsFragment : Fragment() {

    private var _binding: FragmentGalleryDetailsBinding? = null

    private val binding get() = _binding!!
    private var galleryList: MutableList<NasaGalleryDataItem> = arrayListOf()
    private var pos: Int = 0
    private lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            galleryList = it.getParcelableArrayList("GalleryItem")!!
            pos = it.getInt("position")
        }
        connectionLiveData = ConnectionLiveData(requireContext())
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

        connectionLiveData.observe(viewLifecycleOwner) { isNetworkAvailable ->
            isNetworkAvailable?.let {
                updateUi(it)
                initViewPagerAdapter()
            }
        }
    }

    private fun initViewPagerAdapter() {
        val viewpager = binding.viewPager
        val viewPagerAdapter = ViewPagerAdapter(requireContext(), galleryList)
        viewpager.adapter = viewPagerAdapter
        viewpager.currentItem = pos
    }

    private fun updateUi(isOnline: Boolean) {
        if (isOnline) {
            binding.viewPager.visibility = View.VISIBLE
            binding.supportLayout.visibility = View.GONE
        } else {
            binding.viewPager.visibility = View.GONE
            binding.supportLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}