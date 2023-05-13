package com.example.astonproject.episode.presentation.episode

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
import com.example.astonproject.databinding.FragmentEpisodesBinding
import com.example.astonproject.episode.domain.model.EpisodeFilter
import com.example.astonproject.episode.presentation.detail.EpisodeDetailFragment
import com.example.astonproject.episode.presentation.episode.adapter.EpisodeAdapter
import com.example.astonproject.episode.presentation.filter.EpisodeFilterFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EpisodeFragment : Fragment(), CustomizeAppBarTitle {

    private lateinit var binding: FragmentEpisodesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: EpisodeViewModel
    private lateinit var navigator: Navigator
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
            @Suppress("DEPRECATION")
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
        navigator = requireActivity() as Navigator
        binding.filterButton.setColorFilter(Color.WHITE)
        initRecyclerView()
        loadEpisode()
        swipeRefresh()
        addListeners(navigator)
    }


    private fun addListeners(navigator: Navigator) {
        binding.filterButton.setOnClickListener {
            navigator.replaceFragment(
                EpisodeFilterFragment.newInstance(filter)
            )
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
            loadEpisode()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun loadEpisode() {
        lifecycleScope.launch {
            viewModel.load(filter.name, filter.episode)
            viewModel.episodeFlow.collectLatest(episodeAdapter::submitData)
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