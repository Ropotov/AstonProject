package com.example.astonproject.presentation.screens.locationFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.databinding.FragmentLocationBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: LocationViewModel
    private val locationAdapter by lazy {
        LocationAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        loadCharacters()
        swipeRefresh()
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                locationAdapter.submitData(PagingData.empty())
                viewModel.locationFlow.collectLatest(locationAdapter::submitData)
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun loadCharacters() {
        lifecycleScope.launch {
            viewModel.locationFlow.collectLatest(locationAdapter::submitData)
        }

        locationAdapter.addLoadStateListener {
            binding.locationRecyclerView.isVisible = it.refresh != LoadState.Loading
            binding.progressBar.isVisible = it.refresh == LoadState.Loading
        }
    }

    private fun initRecyclerView() {
        recyclerView = binding.locationRecyclerView
        recyclerView.apply {
            adapter = locationAdapter
            addItemDecoration(
                DividerItemDecoration(
                    binding.locationRecyclerView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            addItemDecoration(
                DividerItemDecoration(
                    binding.locationRecyclerView.context,
                    DividerItemDecoration.HORIZONTAL
                )
            )
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = LocationFragment()
        const val TAG = "LocationFragment"
    }
}