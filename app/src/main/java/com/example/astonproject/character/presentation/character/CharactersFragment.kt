package com.example.astonproject.character.presentation.character

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
import com.example.astonproject.character.domain.model.CharacterFilter
import com.example.astonproject.character.presentation.character.adapter.CharacterAdapter
import com.example.astonproject.character.presentation.detail.CharacterDetailFragment
import com.example.astonproject.character.presentation.filter.CharacterFilterFragment
import com.example.astonproject.databinding.FragmentCharactersBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersFragment : Fragment(), CustomizeAppBarTitle {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CharacterViewModel
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

    private fun errorConnectivity(boolean: Boolean) {
        if (boolean) {
            binding.characterRecyclerView.visibility = View.VISIBLE
            binding.errorImage.visibility = View.GONE
            binding.errorText.visibility = View.GONE
            binding.errorBtn.visibility = View.GONE
            binding.filterButton.visibility = View.VISIBLE
            binding.errorText.text = getString(R.string.error_text)
        } else {
            binding.characterRecyclerView.visibility = View.GONE
            binding.errorImage.visibility = View.VISIBLE
            binding.errorText.visibility = View.VISIBLE
            binding.errorBtn.visibility = View.VISIBLE
            binding.filterButton.visibility = View.GONE
            binding.errorText.text = getString(R.string.notConnected)
        }
    }

    private fun addListeners(navigator: Navigator) {
        binding.filterButton.setOnClickListener {
            navigator.replaceFragment(
                CharacterFilterFragment.newInstance(filter),
            )
        }

        binding.errorBtn.setOnClickListener {
            loadContent()
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
            lifecycleScope.launch {
                characterAdapter.submitData(PagingData.empty())
                viewModel.characterFlow.collectLatest(characterAdapter::submitData)
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun loadCharacters() {
        lifecycleScope.launch {
            viewModel.load(filter.name, filter.status, filter.gender)
            viewModel.characterFlow.collectLatest(characterAdapter::submitData)
        }
        lifecycleScope.launch {
            viewModel.loadCharacterCount(filter.name, filter.status, filter.gender)
            viewModel.character.observe(viewLifecycleOwner) {
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
            binding.characterRecyclerView.visibility = View.GONE
            binding.errorImage.visibility = View.VISIBLE
            binding.errorText.visibility = View.VISIBLE
        } else {
            binding.errorImage.visibility = View.GONE
            binding.errorText.visibility = View.GONE
            binding.characterRecyclerView.visibility = View.VISIBLE
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
            binding.characterRecyclerView.isVisible = it.refresh != LoadState.Loading
            binding.progressBar.isVisible = it.refresh == LoadState.Loading
        }
        characterAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun customTitle(): String {
        return TAG
    }

    @Suppress("DEPRECATION")
    private fun hasConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    companion object {
        const val TAG = "Character"
        private const val EMPTY_STRING = ""

        @JvmStatic
        fun newInstance() = CharactersFragment()
    }
}
