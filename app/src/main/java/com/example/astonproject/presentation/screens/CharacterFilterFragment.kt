package com.example.astonproject.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.astonproject.presentation.Navigator
import com.example.astonproject.databinding.FragmentCharacterFilterBinding

class CharacterFilterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigator = requireActivity() as Navigator
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigator.popUpToBackStack("Character")
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)

        binding.save.setOnClickListener {
            val name = binding.search.text.toString()
            var status = ""
            var gender = ""
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
                    putString("gender", gender)
                    putString("status", status)
                }
            )
            navigator.popUpToBackStack("Character")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharacterFilterFragment()
    }
}
