package com.example.nasagalleryapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nasagalleryapp.databinding.FragmentGalleryListBinding
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.example.nasagalleryapp.presentation.adapters.GalleryListAdapter


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GalleryListFragment : Fragment() {

    private var _binding: FragmentGalleryListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GalleryListAdapter
    private val galleryDataItemList: MutableList<NasaGalleryDataItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_GalleryListFragment_to_GalleryDetailsFragment)
        }*/
        //binding.recyclerview = this.requireView().findViewById(R.id.recyclerview)
        initRecyclerView()
        initView()
        binding.recyclerview.adapter = adapter
        //recyclerView.adapter = adapter
    }

    private fun initView() {
        //progressBar = this.requireView().findViewById(R.id.progressbar)
    }

    private fun initAdapter(list: ArrayList<NasaGalleryDataItem>?) {
        list?.let {
            galleryDataItemList.addAll(it)
        }
        recyclerView.adapter = adapter
    }

    private fun initRecyclerView() {
        //recyclerView = this.requireView().findViewById(R.id.recyclerview)
        recyclerView = binding.recyclerview


        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, 1)
        recyclerView.layoutManager = staggeredGridLayoutManager
        //recyclerView.adapter = adapter
        adapter = GalleryListAdapter(galleryDataItemList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}