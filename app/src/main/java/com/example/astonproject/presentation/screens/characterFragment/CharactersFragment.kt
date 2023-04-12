package com.example.astonproject.presentation.screens.characterFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.astonproject.databinding.FragmentCharactersBinding
import kotlinx.coroutines.launch


class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CharacterViewModel
    private val characterAdapter by lazy {
        CharacterAdapter()
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
        initRecyclerView()
        loadCharacters()
    }

    private fun loadCharacters() {
        lifecycleScope.launch {
            viewModel.characterFlow.collect {
                characterAdapter.submitData(it)
            }
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
        @JvmStatic
        fun newInstance() = CharactersFragment()
    }
}
