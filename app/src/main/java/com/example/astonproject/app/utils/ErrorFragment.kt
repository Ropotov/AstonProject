package com.example.astonproject.app.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.astonproject.character.presentation.character.CharactersFragment
import com.example.astonproject.databinding.FragmentErrorBinding
import com.example.astonproject.episode.presentation.episode.EpisodeFragment
import com.example.astonproject.location.presentation.location.LocationFragment

class ErrorFragment : Fragment() {

    private lateinit var binding: FragmentErrorBinding
    private lateinit var navigator: Navigator
    private var tag: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentErrorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tag = arguments?.getString("tag")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = requireActivity() as Navigator
        binding.exit.setOnClickListener {
            requireActivity().finish()
        }
        binding.openFilter.setOnClickListener {
            when (tag) {
                "Character" -> navigator.replaceFragment(
                    CharactersFragment.newInstance(
                    )
                )
                "Location" -> navigator.replaceFragment(
                    LocationFragment.newInstance(
                    )
                )
                "Episode" -> navigator.replaceFragment(
                    EpisodeFragment.newInstance(
                    )
                )
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tag: String): ErrorFragment {
            return ErrorFragment().apply {
                arguments = bundleOf("tag" to tag)
            }
        }
    }
}
