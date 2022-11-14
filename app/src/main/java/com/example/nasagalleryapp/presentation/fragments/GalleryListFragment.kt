package com.example.nasagalleryapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nasagalleryapp.R
import com.example.nasagalleryapp.databinding.FragmentGalleryListBinding
import com.example.nasagalleryapp.domain.data.NasaGalleryDataItem
import com.example.nasagalleryapp.presentation.adapters.GalleryListAdapter
import com.example.nasagalleryapp.presentation.util.ConnectionLiveData
import com.example.nasagalleryapp.presentation.view_model.NasaGalleryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryListFragment : Fragment(), (Int) -> Unit {

    private var _binding: FragmentGalleryListBinding? = null

    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GalleryListAdapter
    private val galleryDataItemList: MutableList<NasaGalleryDataItem> = arrayListOf()
    private val viewModel: NasaGalleryViewModel by viewModels()
    private lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(requireContext())
        viewModel.getGalleryList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectionLiveData.observe(viewLifecycleOwner) { isNetworkAvailable ->
            isNetworkAvailable?.let {
                updateUI(it)
            }
        }
        initObserver()
        initRecyclerView()

    }

    private fun initObserver() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            progressVisibility(it.isLoading)
            handleError(it.error)
            it?.nasaGalleryList?.let { list ->
                initAdapter(list)
            }
        }
    }

    private fun initAdapter(list: ArrayList<NasaGalleryDataItem>?) {
        galleryDataItemList.clear()
        list?.let {
            galleryDataItemList.addAll(it)
        }
        binding.recyclerview.adapter = adapter
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerview

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, LinearLayout.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        adapter = GalleryListAdapter(galleryDataItemList, this)
    }

    private fun progressVisibility(isVisible: Boolean) {
        if (isVisible)
            binding.progressCircular.visibility = View.VISIBLE
        else
            binding.progressCircular.visibility = View.GONE
    }

    private fun handleError(message: String?) {
        message?.let {
            Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun invoke(pos: Int) {
        findNavController().navigate(
            R.id.action_GalleryListFragment_to_GalleryDetailsFragment, bundleOf(
                "GalleryItem" to galleryDataItemList as ArrayList,
                "position" to pos
            )
        )
    }

    private fun updateUI(isOnline: Boolean) {
        if (isOnline) {
            binding.recyclerview.visibility = View.VISIBLE
            binding.supportLayout.visibility = View.GONE
        } else {
            binding.recyclerview.visibility = View.GONE
            binding.supportLayout.visibility = View.VISIBLE
            progressVisibility(false)
        }
    }
}