package com.example.astonproject.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.astonproject.databinding.FragmentCharacterFilterBinding
import com.example.astonproject.databinding.FragmentEpisodeFilterBinding
import com.example.astonproject.presentation.Navigator

class EpisodeFilterFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeFilterBinding
    private var name = EMPTY_STRING
    private var episode = EMPTY_STRING


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("name") ?: EMPTY_STRING
        episode = arguments?.getString("episode") ?: EMPTY_STRING

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigator = requireActivity() as Navigator
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigator.popUpToBackStack("Episode")
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)

        if (name.isNotEmpty()) binding.search.setText(name)
        when (episode) {
            "S01" -> binding.season1.isChecked = true
            "S02" -> binding.season2.isChecked = true
            "S03" -> binding.season3.isChecked = true
            "S04" -> binding.season4.isChecked = true
            "S05" -> binding.season5.isChecked = true
        }

        binding.save.setOnClickListener {
            name = EMPTY_STRING
            episode = EMPTY_STRING
            name = binding.search.text.toString()
            if (binding.season1.isChecked) episode = "S01"
            if (binding.season2.isChecked) episode = "S02"
            if (binding.season3.isChecked) episode = "S03"
            if (binding.season4.isChecked) episode = "S04"
            if (binding.season5.isChecked) episode = "S05"


            setFragmentResult(
                "requestKey", Bundle().apply {
                    putString("name", name)
                    putString("episode", episode)
                }
            )
            navigator.popUpToBackStack("Episode")
        }
    }

    companion object {
        const val TAG = "Filter"
        private const val EMPTY_STRING = ""
        @JvmStatic
        fun newInstance(name: String, episode: String): EpisodeFilterFragment {
            return EpisodeFilterFragment().apply {
                arguments = Bundle().apply {
                    putString("name", name)
                    putString("episode", episode)
                }
            }
        }
    }
}
