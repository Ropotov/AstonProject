package com.example.astonproject.presentation.screens.locationFragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.App
import com.example.astonproject.databinding.FragmentLocationBinding
import com.example.astonproject.di.ViewModelFactory
import com.example.astonproject.presentation.Navigator
import com.example.astonproject.presentation.screens.LocationFilterFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: LocationViewModel
    private val locationAdapter by lazy {
        LocationAdapter()
    }

    private var name = EMPTY_STRING
    private var type = EMPTY_STRING
    private var dimension = EMPTY_STRING

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey") { _, bundle ->
            name = bundle.getString("name") ?: EMPTY_STRING
            type = bundle.getString("type") ?: EMPTY_STRING
            dimension = bundle.getString("dimension") ?: EMPTY_STRING
            lifecycleScope.launch {
                viewModel.load(name, type, dimension)
                viewModel.locationFlow.collectLatest(locationAdapter::submitData)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[LocationViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigator = requireActivity() as Navigator
        binding.filterButton.setColorFilter(Color.WHITE)
        initRecyclerView()
        loadCharacters()
        swipeRefresh()

        binding.filterButton.setOnClickListener {
            navigator.replaceFragment(
                LocationFilterFragment.newInstance(
                    name,
                    type,
                    dimension),
                LocationFilterFragment.TAG
            )
        }
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
            viewModel.load(name, type, dimension)
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
        const val TAG = "Location"
        private const val EMPTY_STRING = ""
    }
}