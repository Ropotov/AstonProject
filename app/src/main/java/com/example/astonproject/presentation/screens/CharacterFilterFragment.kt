package com.example.astonproject.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.astonproject.databinding.FragmentCharacterFilterBinding
import com.example.astonproject.presentation.Navigator
import com.example.astonproject.presentation.screens.characterFragment.CharactersFragment

class CharacterFilterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterFilterBinding
    private var name = EMPTY_STRING
    private var status = EMPTY_STRING
    private var gender = EMPTY_STRING

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("name") ?: EMPTY_STRING
        status = arguments?.getString("status") ?: EMPTY_STRING
        gender = arguments?.getString("gender") ?: EMPTY_STRING
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigator = requireActivity() as Navigator
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigator.popUpToBackStack(CharactersFragment.TAG)
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)

        if (name.isNotEmpty()) binding.search.setText(name)
        when (status) {
            "Alive" -> binding.alive.isChecked = true
            "Dead" -> binding.dead.isChecked = true
            "unknown" -> binding.unknownStatus.isChecked = true
        }

        when (gender) {
            "Male" -> binding.male.isChecked = true
            "Female" -> binding.female.isChecked = true
            "Genderless" -> binding.genderless.isChecked = true
            "unknown" -> binding.unknownGender.isChecked = true
        }

        binding.save.setOnClickListener {
            name = EMPTY_STRING
            status = EMPTY_STRING
            gender = EMPTY_STRING
            name = binding.search.text.toString()
            if (binding.alive.isChecked) status = "Alive"
            if (binding.dead.isChecked) status = "Dead"
            if (binding.unknownStatus.isChecked) status = "unknown"
            if (binding.male.isChecked) gender = "Male"
            if (binding.female.isChecked) gender = "Female"
            if (binding.unknownGender.isChecked) gender = "unknown"
            if (binding.genderless.isChecked) gender = "Genderless"

            setFragmentResult(
                "requestKey", Bundle().apply {
                    putString("name", name)
                    putString("status", status)
                    putString("gender", gender)
                }
            )
            navigator.popUpToBackStack(CharactersFragment.TAG)
        }
    }

    companion object {
        const val TAG = "Filter"
        private const val EMPTY_STRING = ""
        @JvmStatic
        fun newInstance(name: String, status: String, gender: String): CharacterFilterFragment {
            return CharacterFilterFragment().apply {
                arguments = Bundle().apply {
                    putString("name", name)
                    putString("status", status)
                    putString("gender", gender)
                }
            }
        }
    }
}
