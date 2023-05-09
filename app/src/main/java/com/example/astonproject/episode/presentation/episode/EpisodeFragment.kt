package com.example.astonproject.episode.presentation.episode

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
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
import com.example.astonproject.R
import com.example.astonproject.app.App
import com.example.astonproject.app.CustomizeAppBarTitle
import com.example.astonproject.app.Navigator
import com.example.astonproject.app.di.ViewModelFactory
import com.example.astonproject.databinding.FragmentEpisodesBinding
import com.example.astonproject.episode.domain.model.EpisodeFilter
import com.example.astonproject.episode.presentation.detail.EpisodeDetailFragment
import com.example.astonproject.episode.presentation.episode.adapter.EpisodeAdapter
import com.example.astonproject.episode.presentation.filter.EpisodeFilterFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeFragment : Fragment(), CustomizeAppBarTitle {

    private lateinit var binding: FragmentEpisodesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: EpisodeViewModel
    private val episodeAdapter by lazy {
        EpisodeAdapter()
    }
    private var filter = EpisodeFilter(
        EMPTY_STRING, EMPTY_STRING
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[EpisodeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigator = requireActivity() as Navigator
        binding.filterButton.setColorFilter(Color.WHITE)
        initRecyclerView()
        loadContent()
        swipeRefresh()
        addListeners(navigator)
    }

    private fun loadContent() {
        if (hasConnected(requireContext())) {
            errorConnectivity(true)
            loadCharacters()
        } else {
            errorConnectivity(false)
        }
    }

    private fun addListeners(navigator: Navigator) {
        binding.filterButton.setOnClickListener {
            navigator.replaceFragment(
                EpisodeFilterFragment.newInstance(filter)
            )
        }
        binding.errorBtn.setOnClickListener {
            loadContent()
        }

        episodeAdapter.onCharacterClickListener = {
            if (it != null) {
                navigator.replaceFragment(
                    EpisodeDetailFragment.newInstance(it.id)
                )
            }
        }
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                episodeAdapter.submitData(PagingData.empty())
                viewModel.episodeFlow.collectLatest(episodeAdapter::submitData)
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun loadCharacters() {
        lifecycleScope.launch {
            viewModel.load(filter.name, filter.episode)
            viewModel.episodeFlow.collectLatest(episodeAdapter::submitData)
        }
        lifecycleScope.launch {
            viewModel.loadCount(filter.name, filter.episode)
            viewModel.episodeCount.observe(viewLifecycleOwner) {
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
            binding.episodesRecyclerView.visibility = View.GONE
            binding.errorImage.visibility = View.VISIBLE
            binding.errorText.visibility = View.VISIBLE
        } else {
            binding.errorImage.visibility = View.GONE
            binding.errorText.visibility = View.GONE
            binding.episodesRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun errorConnectivity(boolean: Boolean) {
        if (boolean) {
            binding.episodesRecyclerView.visibility = View.VISIBLE
            binding.errorImage.visibility = View.GONE
            binding.errorText.visibility = View.GONE
            binding.errorBtn.visibility = View.GONE
            binding.filterButton.visibility = View.VISIBLE
            binding.errorText.text = getString(R.string.error_text)
        } else {
            binding.episodesRecyclerView.visibility = View.GONE
            binding.errorImage.visibility = View.VISIBLE
            binding.errorText.visibility = View.VISIBLE
            binding.errorBtn.visibility = View.VISIBLE
            binding.filterButton.visibility = View.GONE
            binding.errorText.text = getString(R.string.notConnected)
        }
    }

    private fun initRecyclerView() {
        recyclerView = binding.episodesRecyclerView
        recyclerView.apply {
            adapter = episodeAdapter
            addItemDecoration(
                DividerItemDecoration(
                    binding.episodesRecyclerView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            addItemDecoration(
                DividerItemDecoration(
                    binding.episodesRecyclerView.context,
                    DividerItemDecoration.HORIZONTAL
                )
            )
        }
        episodeAdapter.addLoadStateListener {
            binding.episodesRecyclerView.isVisible = it.refresh != LoadState.Loading
            binding.progressBar.isVisible = it.refresh == LoadState.Loading
        }
    }

    @Suppress("DEPRECATION")
    private fun hasConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    companion object {
        const val TAG = "Episode"
        private const val EMPTY_STRING = ""

        @JvmStatic
        fun newInstance() = EpisodeFragment()
    }

    override fun customTitle(): String {
        return TAG
    }
}