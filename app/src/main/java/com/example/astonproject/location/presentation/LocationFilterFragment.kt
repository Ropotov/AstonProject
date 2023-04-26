package com.example.astonproject.location.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.astonproject.app.App
import com.example.astonproject.databinding.FragmentLocationFilterBinding
import com.example.astonproject.app.Navigator

class LocationFilterFragment : Fragment() {

    private lateinit var binding: FragmentLocationFilterBinding
    private var name = EMPTY_STRING
    private var type = EMPTY_STRING
    private var dimension = EMPTY_STRING

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
        name = arguments?.getString("name") ?: EMPTY_STRING
        type = arguments?.getString("type") ?: EMPTY_STRING
        dimension = arguments?.getString("dimension") ?: EMPTY_STRING
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigator = requireActivity() as Navigator
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigator.popUpToBackStack(LocationFragment.TAG)
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)

        if (name.isNotEmpty()) binding.search.setText(name)
        when (type) {
            "Planet" -> binding.planet.isChecked = true
            "Cluster" -> binding.cluster.isChecked = true
            "Dream" -> binding.dream.isChecked = true
            "Space Station" -> binding.spaceStation.isChecked = true
            "Dimension" -> binding.dimension.isChecked = true
            "Game" -> binding.game.isChecked = true
        }

        when (dimension) {
            "Replacement Dimension" -> binding.replacementDimension.isChecked = true
            "Dimension C-137" -> binding.dimensionC137.isChecked = true
            "unknown" -> binding.unknownDimetsion.isChecked = true
        }

        binding.save.setOnClickListener {
            name = EMPTY_STRING
            type = EMPTY_STRING
            dimension = EMPTY_STRING
            name = binding.search.text.toString()
            if (binding.planet.isChecked) type = "Planet"
            if (binding.cluster.isChecked) type = "Cluster"
            if (binding.spaceStation.isChecked) type = "Space Station"
            if (binding.game.isChecked) type = "Game"
            if (binding.dream.isChecked) type = "Dream"
            if (binding.dimension.isChecked) type = "Dimension"
            if (binding.replacementDimension.isChecked) dimension = "Replacement Dimension"
            if (binding.dimensionC137.isChecked) dimension = "Dimension C-137"
            if (binding.unknownDimetsion.isChecked) dimension = "unknown"

            setFragmentResult(
                "requestKey", Bundle().apply {
                    putString("name", name)
                    putString("type", type)
                    putString("dimension", dimension)
                }
            )
            navigator.popUpToBackStack(LocationFragment.TAG)
        }
    }

    companion object {
        const val TAG = "Filter"
        private const val EMPTY_STRING = ""
        @JvmStatic
        fun newInstance(name: String, type: String, dimension: String): LocationFilterFragment {
            return LocationFilterFragment().apply {
                arguments = Bundle().apply {
                    putString("name", name)
                    putString("type", type)
                    putString("dimension", dimension)
                }
            }
        }
    }
}
