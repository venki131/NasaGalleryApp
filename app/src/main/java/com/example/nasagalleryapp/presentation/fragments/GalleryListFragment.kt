package com.example.nasagalleryapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nasagalleryapp.databinding.FragmentGalleryListBinding
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.example.nasagalleryapp.presentation.adapters.GalleryListAdapter
import com.example.nasagalleryapp.presentation.view_model.NasaGalleryViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class GalleryListFragment : Fragment() {

    private var _binding: FragmentGalleryListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GalleryListAdapter
    private val galleryDataItemList: MutableList<NasaGalleryDataItem> = arrayListOf()
    private val viewModel: NasaGalleryViewModel by viewModels()

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
        initObserver()
        initRecyclerView()
        viewModel.getGalleryList()
    }

    private fun initObserver() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            //progressVisibility(it.isLoading)
            handleError(it.error)
            it?.nasaGalleryList?.let { list->
                initAdapter(list)
            }
        }
    }

    private fun initAdapter(list: ArrayList<NasaGalleryDataItem>?) {
        list?.let {
            galleryDataItemList.addAll(it)
        }
        binding.recyclerview.adapter = adapter
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerview

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, 1)
        recyclerView.layoutManager = staggeredGridLayoutManager
        adapter = GalleryListAdapter(galleryDataItemList)
    }

    /*private fun progressVisibility(isVisible: Boolean) {
        if (isVisible)
            //progressBar.visibility = View.VISIBLE
        //progressBar.visibility = View.GONE
    }*/

    private fun handleError(message: String?) {
        message?.let {
            Log.e("Error", it)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}