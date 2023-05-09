package com.example.astonproject.location.presentation.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.astonproject.app.App
import com.example.astonproject.app.CustomizeAppBarTitle
import com.example.astonproject.app.Navigator
import com.example.astonproject.databinding.FragmentLocationFilterBinding
import com.example.astonproject.location.domain.model.LocationFilter

class LocationFilterFragment : Fragment(), CustomizeAppBarTitle {

    private lateinit var binding: FragmentLocationFilterBinding
    private var filter: LocationFilter? = null

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
        binding = FragmentLocationFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filter = arguments?.getParcelable("filter")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigator = requireActivity() as Navigator

        if (filter?.name?.isNotEmpty() == true) binding.search.setText(filter?.name)
        when (filter?.type) {
            "Planet" -> binding.planet.isChecked = true
            "Cluster" -> binding.cluster.isChecked = true
            "Dream" -> binding.dream.isChecked = true
            "Space Station" -> binding.spaceStation.isChecked = true
            "Dimension" -> binding.dimension.isChecked = true
            "Game" -> binding.game.isChecked = true
        }

        when (filter?.dimension) {
            "Replacement Dimension" -> binding.replacementDimension.isChecked = true
            "Dimension C-137" -> binding.dimensionC137.isChecked = true
            "unknown" -> binding.unknownDimetsion.isChecked = true
        }

        binding.save.setOnClickListener {
            filter?.name = EMPTY_STRING
            filter?.type = EMPTY_STRING
            filter?.dimension = EMPTY_STRING
            filter?.name = binding.search.text.toString()
            if (binding.planet.isChecked) filter?.type = "Planet"
            if (binding.cluster.isChecked) filter?.type = "Cluster"
            if (binding.spaceStation.isChecked) filter?.type = "Space Station"
            if (binding.game.isChecked) filter?.type = "Game"
            if (binding.dream.isChecked) filter?.type = "Dream"
            if (binding.dimension.isChecked) filter?.type = "Dimension"
            if (binding.replacementDimension.isChecked) filter?.dimension = "Replacement Dimension"
            if (binding.dimensionC137.isChecked) filter?.dimension = "Dimension C-137"
            if (binding.unknownDimetsion.isChecked) filter?.dimension = "unknown"

            setFragmentResult(
                "requestKey", Bundle().apply {
                    putParcelable("filter", filter)
                }
            )
            navigator.popUpToBackStack()
        }
    }

    override fun customTitle(): String {
        return TAG
    }

    companion object {
        const val TAG = "Filter"
        private const val EMPTY_STRING = ""

        @JvmStatic
        fun newInstance(filter: LocationFilter): LocationFilterFragment {
            return LocationFilterFragment().apply {
                arguments = bundleOf("filter" to filter)
            }
        }
    }
}
