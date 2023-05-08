package com.example.astonproject.location.presentation.location

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
import com.example.astonproject.app.App
import com.example.astonproject.app.CustomizeAppBarTitle
import com.example.astonproject.app.Navigator
import com.example.astonproject.app.di.ViewModelFactory
import com.example.astonproject.databinding.FragmentLocationBinding
import com.example.astonproject.location.domain.model.LocationFilter
import com.example.astonproject.location.presentation.detail.LocationDetailFragment
import com.example.astonproject.location.presentation.filter.LocationFilterFragment
import com.example.astonproject.location.presentation.location.adapter.LocationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationFragment : Fragment(), CustomizeAppBarTitle {

    private lateinit var binding: FragmentLocationBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: LocationViewModel
    private val locationAdapter by lazy {
        LocationAdapter()
    }
    private var filter = LocationFilter(
        EMPTY_STRING, EMPTY_STRING, EMPTY_STRING
    )

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
            filter = bundle.getParcelable("filter")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
        loadLocation()
        swipeRefresh()
        addListeners(navigator)
    }

    private fun addListeners(navigator: Navigator) {
        binding.filterButton.setOnClickListener {
            navigator.replaceFragment(
                LocationFilterFragment.newInstance(filter)
            )
        }
        locationAdapter.onCharacterClickListener = {
            if (it != null) {
                navigator.replaceFragment(
                    LocationDetailFragment.newInstance(it.id)
                )
            }
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

    private fun loadLocation() {
        lifecycleScope.launch {
            viewModel.load(filter.name, filter.type, filter.dimension)
            viewModel.locationFlow.collectLatest(locationAdapter::submitData)

        }
        lifecycleScope.launch {
            viewModel.loadCount(filter.name, filter.type, filter.dimension)
            viewModel.locationCount.observe(viewLifecycleOwner) {
                if (it.count == 0) {
                    errorVisibility(true)
                } else {
                    errorVisibility(false)
                }
            }
        }
    }

    private fun errorVisibility(boolean: Boolean) {
        if (boolean) {
            binding.locationRecyclerView.visibility = View.GONE
            binding.errorImage.visibility = View.VISIBLE
            binding.errorText.visibility = View.VISIBLE
        } else {
            binding.errorImage.visibility = View.GONE
            binding.errorText.visibility = View.GONE
            binding.locationRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerView() {
        recyclerView = binding.locationRecyclerView
        recyclerView.apply {
            adapter = locationAdapter
            addItemDecoration(
                DividerItemDecoration(
                    binding.locationRecyclerView.context, DividerItemDecoration.VERTICAL
                )
            )
            addItemDecoration(
                DividerItemDecoration(
                    binding.locationRecyclerView.context, DividerItemDecoration.HORIZONTAL
                )
            )
        }
        locationAdapter.addLoadStateListener {
            binding.locationRecyclerView.isVisible = it.refresh != LoadState.Loading
            binding.progressBar.isVisible = it.refresh == LoadState.Loading
        }
    }

    override fun customTitle(): String {
        return TAG
    }

    companion object {
        const val TAG = "Location"
        private const val EMPTY_STRING = ""

        @JvmStatic
        fun newInstance() = LocationFragment()
    }
}