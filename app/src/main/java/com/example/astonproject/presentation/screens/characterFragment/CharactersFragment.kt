package com.example.astonproject.presentation.screens.characterFragment

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
import com.example.astonproject.databinding.FragmentCharactersBinding
import com.example.astonproject.presentation.Navigator
import com.example.astonproject.presentation.screens.CharacterFilterFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CharacterViewModel
    private val characterAdapter by lazy {
        CharacterAdapter()
    }

    private var name = EMPTY_STRING
    private var status = EMPTY_STRING
    private var gender = EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey") { _, bundle ->
            name = bundle.getString("name") ?: EMPTY_STRING
            status = bundle.getString("status") ?: EMPTY_STRING
            gender = bundle.getString("gender") ?: EMPTY_STRING
            lifecycleScope.launch {
                viewModel.load(name, status, gender)
                viewModel.characterFlow.collectLatest(characterAdapter::submitData)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
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
                CharacterFilterFragment.newInstance(name, status, gender),
                CharacterFilterFragment.TAG
            )
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
            viewModel.load(name, gender, status)
            viewModel.characterFlow.collectLatest(characterAdapter::submitData)
        }
        characterAdapter.addLoadStateListener {
            binding.characterRecyclerView.isVisible = it.refresh != LoadState.Loading
            binding.progressBar.isVisible = it.refresh == LoadState.Loading
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
    }

    companion object {
        const val TAG = "Character"
        private const val EMPTY_STRING = ""

        @JvmStatic
        fun newInstance() = CharactersFragment()
    }
}
