package com.example.astonproject.episode.presentation.detail

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.app.App
import com.example.astonproject.app.di.ViewModelFactory
import com.example.astonproject.app.utils.CustomizeAppBarTitle
import com.example.astonproject.app.utils.Navigator
import com.example.astonproject.character.presentation.detail.CharacterDetailFragment
import com.example.astonproject.databinding.FragmentEpisodeDetailBinding
import com.example.astonproject.episode.domain.model.EpisodeResult
import com.example.astonproject.episode.presentation.detail.adapter.EpisodeDetailAdapter
import javax.inject.Inject

class EpisodeDetailFragment : Fragment(), CustomizeAppBarTitle {
    private lateinit var binding: FragmentEpisodeDetailBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: EpisodeDetailViewModel
    private lateinit var navigator: Navigator
    private var id = 0
    private var characters = ""

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val episodeAdapter by lazy {
        EpisodeDetailAdapter()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        navigator = requireActivity() as Navigator
        viewModel = ViewModelProvider(this, viewModelFactory)[EpisodeDetailViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvInit()
        loadContent()
        addClickListener()
    }

    private fun addClickListener() {
        episodeAdapter.onCharacterClickListener = {
            navigator.replaceFragment(
                CharacterDetailFragment.newInstance(it?.id!!)
            )
        }
    }

    private fun loadContent() {
        if (hasConnected(requireContext())) {
            viewModel.load(id)
            viewModel.episodeDetail.observe(viewLifecycleOwner) {
                content(it)
                toListStringNumber(it.characters)
                viewModel.loadListCharacter(characters)
                viewModel.listCharacters.observe(viewLifecycleOwner) { list ->
                    episodeAdapter.submitList(list)
                }
            }
        } else {
            viewModel.loadFromDb(id)
            viewModel.episodeDetail.observe(viewLifecycleOwner) {
                content(it)
                binding.coordinator.visibility = View.GONE
            }
        }

    }

    private fun rvInit() {
        recyclerView = binding.rvEpisodes

        recyclerView.apply {
            adapter = episodeAdapter
            addItemDecoration(
                DividerItemDecoration(
                    binding.rvEpisodes.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            addItemDecoration(
                DividerItemDecoration(
                    binding.rvEpisodes.context,
                    DividerItemDecoration.HORIZONTAL
                )
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun content(episodeResult: EpisodeResult) {
        binding.tvEpisode.text = "Episode: " + episodeResult.episode
        binding.tvEpisodeAirDate.text = "Air date: " + episodeResult.air_date
        binding.tvEpisodeName.text = episodeResult.name
        binding.tvEpisodeCreated.text = "Created: " + episodeResult.created.substring(0, 10)
        binding.tvCharactersTitle.text = "Characters: "
    }

    private fun toListStringNumber(list: List<String>) {
        for (i in list) {
            val newString = i.substring(i.lastIndexOf('/') + 1)
            characters += "$newString,"
        }
    }

    override fun customTitle(): String {
        return TAG
    }

    @Suppress("DEPRECATION")
    private fun hasConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = manager.activeNetworkInfo
        return network != null && network.isConnected
    }

    companion object {
        const val TAG = "Episode detail"

        @JvmStatic
        fun newInstance(id: Int): EpisodeDetailFragment {
            return EpisodeDetailFragment().apply {
                arguments = bundleOf("id" to id)
            }
        }
    }
}