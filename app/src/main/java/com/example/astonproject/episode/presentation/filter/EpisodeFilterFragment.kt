package com.example.astonproject.episode.presentation.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.astonproject.app.App
import com.example.astonproject.app.utils.CustomizeAppBarTitle
import com.example.astonproject.app.utils.Navigator
import com.example.astonproject.databinding.FragmentEpisodeFilterBinding
import com.example.astonproject.episode.domain.model.EpisodeFilter

class EpisodeFilterFragment : Fragment(), CustomizeAppBarTitle {

    private lateinit var binding: FragmentEpisodeFilterBinding
    private var filter: EpisodeFilter? = null

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        filter = arguments?.getParcelable("filter")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigator = requireActivity() as Navigator

        if (filter?.name?.isNotEmpty() == true) binding.search.setText(filter?.name)
        when (filter?.episode) {
            "S01" -> binding.season1.isChecked = true
            "S02" -> binding.season2.isChecked = true
            "S03" -> binding.season3.isChecked = true
            "S04" -> binding.season4.isChecked = true
            "S05" -> binding.season5.isChecked = true
        }

        binding.save.setOnClickListener {
            filter?.name = EMPTY_STRING
            filter?.episode = EMPTY_STRING
            filter?.name = binding.search.text.toString()
            if (binding.season1.isChecked) filter?.episode = "S01"
            if (binding.season2.isChecked) filter?.episode = "S02"
            if (binding.season3.isChecked) filter?.episode = "S03"
            if (binding.season4.isChecked) filter?.episode = "S04"
            if (binding.season5.isChecked) filter?.episode = "S05"


            setFragmentResult(
                "requestKey", Bundle().apply {
                    putParcelable("filter", filter)
                }
            )
            navigator.popUpToBackStack()
        }
    }

    companion object {
        const val TAG = "Filter"
        private const val EMPTY_STRING = ""

        @JvmStatic
        fun newInstance(filter: EpisodeFilter): EpisodeFilterFragment {
            return EpisodeFilterFragment().apply {
                arguments = bundleOf("filter" to filter)
            }
        }
    }

    override fun customTitle(): String {
        return TAG
    }
}
