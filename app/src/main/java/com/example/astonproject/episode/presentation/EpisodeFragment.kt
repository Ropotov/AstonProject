package com.example.astonproject.episode.presentation

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
import com.example.astonproject.databinding.FragmentEpisodesBinding
import com.example.astonproject.app.di.ViewModelFactory
import com.example.astonproject.app.Navigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeFragment : Fragment() {

    private lateinit var binding: FragmentEpisodesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: EpisodeViewModel
    private val episodeAdapter by lazy {
        EpisodeAdapter()
    }

    private var name = EMPTY_STRING
    private var episode = EMPTY_STRING

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
            episode = bundle.getString("episode") ?: EMPTY_STRING
            lifecycleScope.launch {
                viewModel.load(name, episode)
                viewModel.episodeFlow.collectLatest(episodeAdapter::submitData)
            }
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
        loadCharacters()
        swipeRefresh()

        binding.filterButton.setOnClickListener {
            navigator.replaceFragment(
                EpisodeFilterFragment.newInstance(name, episode),
                EpisodeFilterFragment.TAG
            )
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
            viewModel.load(name, episode)
            viewModel.episodeFlow.collectLatest(episodeAdapter::submitData)
        }

        episodeAdapter.addLoadStateListener {
            binding.episodesRecyclerView.isVisible = it.refresh != LoadState.Loading
            binding.progressBar.isVisible = it.refresh == LoadState.Loading
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
    }

    companion object {
        const val TAG = "Episode"
        private const val EMPTY_STRING = ""

        @JvmStatic
        fun newInstance() = EpisodeFragment()
    }
}