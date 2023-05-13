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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.app.App
import com.example.astonproject.app.di.ViewModelFactory
import com.example.astonproject.app.utils.CustomizeAppBarTitle
import com.example.astonproject.app.utils.ErrorFragment
import com.example.astonproject.app.utils.Navigator
import com.example.astonproject.databinding.FragmentLocationBinding
import com.example.astonproject.location.domain.model.LocationFilter
import com.example.astonproject.location.presentation.detail.LocationDetailFragment
import com.example.astonproject.location.presentation.filter.LocationFilterFragment
import com.example.astonproject.location.presentation.location.adapter.LocationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LocationFragment : Fragment(), CustomizeAppBarTitle {

    private lateinit var binding: FragmentLocationBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: LocationViewModel
    private lateinit var navigator: Navigator
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
            @Suppress("DEPRECATION")
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
        navigator = requireActivity() as Navigator
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
            loadLocation()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun loadLocation() {
        lifecycleScope.launch {
            viewModel.load(filter.name, filter.type, filter.dimension)
            viewModel.locationFlow.collectLatest(locationAdapter::submitData)

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
            binding.progressBar.isVisible = it.refresh == LoadState.Loading
            val errorState = when {
                it.prepend is LoadState.Error -> it.prepend as LoadState.Error
                it.refresh is LoadState.Error -> it.refresh as LoadState.Error
                else -> null
            }
            when (errorState?.error) {
                is IOException -> navigator.replaceFragment(ErrorFragment.newInstance(TAG))
                is HttpException -> navigator.replaceFragment(ErrorFragment.newInstance(TAG))
            }
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