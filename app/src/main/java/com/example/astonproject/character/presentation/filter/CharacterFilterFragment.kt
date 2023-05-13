package com.example.astonproject.character.presentation.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.astonproject.app.App
import com.example.astonproject.app.utils.CustomizeAppBarTitle
import com.example.astonproject.app.utils.Navigator
import com.example.astonproject.character.domain.model.CharacterFilter
import com.example.astonproject.databinding.FragmentCharacterFilterBinding

class CharacterFilterFragment : Fragment(), CustomizeAppBarTitle {

    private lateinit var binding: FragmentCharacterFilterBinding
    private var filter: CharacterFilter? = null

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
        binding = FragmentCharacterFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filter = arguments?.getParcelable("filter")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigator = requireActivity() as Navigator

        if (filter?.name?.isNotEmpty() == true) binding.search.setText(filter!!.name)
        when (filter?.status) {
            "Alive" -> binding.alive.isChecked = true
            "Dead" -> binding.dead.isChecked = true
            "unknown" -> binding.unknownStatus.isChecked = true
        }

        when (filter?.gender) {
            "Male" -> binding.male.isChecked = true
            "Female" -> binding.female.isChecked = true
            "Genderless" -> binding.genderless.isChecked = true
            "unknown" -> binding.unknownGender.isChecked = true
        }

        when (filter?.species) {
            "Human" -> binding.human.isChecked = true
            "Alien" -> binding.alien.isChecked = true
            "Humanoid" -> binding.humanoid.isChecked = true
            "Robot" -> binding.robot.isChecked = true
            "unknown" -> binding.unknownHero.isChecked = true
            "Poopybutthole" -> binding.poopybutthole.isChecked = true
            "Mythological" -> binding.Mythological.isChecked = true
            "Animal" -> binding.Animal.isChecked = true
            "Disease" -> binding.Disease.isChecked = true
        }

        binding.save.setOnClickListener {
            filter?.name = EMPTY_STRING
            filter?.status = EMPTY_STRING
            filter?.gender = EMPTY_STRING
            filter?.species = EMPTY_STRING
            filter?.name = binding.search.text.toString()
            if (binding.alive.isChecked) filter?.status = "Alive"
            if (binding.dead.isChecked) filter?.status = "Dead"
            if (binding.unknownStatus.isChecked) filter?.status = "unknown"
            if (binding.male.isChecked) filter?.gender = "Male"
            if (binding.female.isChecked) filter?.gender = "Female"
            if (binding.unknownGender.isChecked) filter?.gender = "unknown"
            if (binding.genderless.isChecked) filter?.gender = "Genderless"
            if (binding.human.isChecked) filter?.species = "Human"
            if (binding.alien.isChecked) filter?.species = "Alien"
            if (binding.humanoid.isChecked) filter?.species = "Humanoid"
            if (binding.robot.isChecked) filter?.species = "Robot"
            if (binding.unknownHero.isChecked) filter?.species = "unknown"
            if (binding.poopybutthole.isChecked) filter?.species = "Poopybutthole"
            if (binding.Mythological.isChecked) filter?.species = "Mythological"
            if (binding.Animal.isChecked) filter?.species = "Animal"
            if (binding.Disease.isChecked) filter?.species = "Disease"

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
        fun newInstance(filter: CharacterFilter): CharacterFilterFragment {
            return CharacterFilterFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("filter", filter)
                }
            }
        }
    }
}
