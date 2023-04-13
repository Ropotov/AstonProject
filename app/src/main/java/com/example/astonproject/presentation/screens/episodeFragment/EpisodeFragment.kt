package com.example.astonproject.presentation.screens.episodeFragment

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
import com.example.astonproject.databinding.FragmentEpisodesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeFragment : Fragment() {

    private lateinit var binding: FragmentEpisodesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: EpisodeViewModel
    private val episodeAdapter by lazy {
        EpisodeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[EpisodeViewModel::class.java]
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
                episodeAdapter.submitData(PagingData.empty())
                viewModel.episodeFlow.collectLatest(episodeAdapter::submitData)
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun loadCharacters() {
        lifecycleScope.launch {
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
        @JvmStatic
        fun newInstance() = EpisodeFragment()
        const val TAG = "EpisodeFragment"
    }
}