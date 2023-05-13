package com.example.astonproject.character.presentation.character

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
import com.example.astonproject.character.domain.model.CharacterFilter
import com.example.astonproject.character.presentation.character.adapter.CharacterAdapter
import com.example.astonproject.character.presentation.detail.CharacterDetailFragment
import com.example.astonproject.character.presentation.filter.CharacterFilterFragment
import com.example.astonproject.databinding.FragmentCharactersBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharactersFragment : Fragment(), CustomizeAppBarTitle {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CharacterViewModel
    private lateinit var navigator: Navigator
    private val characterAdapter by lazy {
        CharacterAdapter()
    }
    private var filter = CharacterFilter(
        EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING
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
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[CharacterViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = requireActivity() as Navigator
        binding.filterButton.setColorFilter(Color.WHITE)
        initRecyclerView()
        loadCharacters()
        swipeRefresh()
        addListeners(navigator)
    }

    private fun addListeners(navigator: Navigator) {
        binding.filterButton.setOnClickListener {
            navigator.replaceFragment(
                CharacterFilterFragment.newInstance(filter)
            )
        }

        characterAdapter.onCharacterClickListener = {
            if (it != null) {
                navigator.replaceFragment(
                    CharacterDetailFragment.newInstance(it.id)
                )
            }
        }
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            loadCharacters()
            binding.swipeRefresh.isRefreshing = false
        }
    }


    private fun loadCharacters() {
        lifecycleScope.launch {
            viewModel.load(filter.name, filter.status, filter.gender, filter.species)
            viewModel.characterFlow.collectLatest(characterAdapter::submitData)
        }
    }

    private fun initRecyclerView() {
        recyclerView = binding.characterRecyclerView
        recyclerView.apply {
            adapter = characterAdapter
            addItemDecoration(
                DividerItemDecoration(
                    binding.characterRecyclerView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            addItemDecoration(
                DividerItemDecoration(
                    binding.characterRecyclerView.context,
                    DividerItemDecoration.HORIZONTAL
                )
            )
        }
        characterAdapter.addLoadStateListener {
            binding.progressBar.isVisible = it.refresh == LoadState.Loading
            val errorState = when {
                it.prepend is LoadState.Error -> it.prepend as LoadState.Error
                it.refresh is LoadState.Error -> it.refresh as LoadState.Error
                else -> null
            }
            when (errorState?.error) {
                is IOException -> navigator.addFragment(
                    ErrorFragment.newInstance(TAG)
                )
                is HttpException -> navigator.addFragment(
                    ErrorFragment
                        .newInstance(TAG)
                )
            }
        }
        characterAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun customTitle(): String {
        return TAG
    }

    companion object {
        const val TAG = "Character"
        private const val EMPTY_STRING = ""

        @JvmStatic
        fun newInstance() = CharactersFragment()
    }
}
