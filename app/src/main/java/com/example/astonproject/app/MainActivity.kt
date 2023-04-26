package com.example.astonproject.app

import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.astonproject.R
import com.example.astonproject.databinding.ActivityMainBinding
import com.example.astonproject.character.presentation.CharactersFragment
import com.example.astonproject.episode.presentation.EpisodeFragment
import com.example.astonproject.location.presentation.LocationFragment

class MainActivity : FragmentActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        visibilityBottomNavigation("Character")
        binding.contentLayout.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.characters -> {
                    addFragment(CharactersFragment.newInstance(), "Character")
                    true
                }
                R.id.location -> {
                    addFragment(LocationFragment.newInstance(), "Location")
                    true
                }
                R.id.episodes -> {
                    addFragment(EpisodeFragment.newInstance(), "Episode")
                    true
                }
                else -> false
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            popUpToBackStack(CharactersFragment.TAG)
        }
    }

    private fun visibilityBottomNavigation(fragmentTag: String) {
        when (fragmentTag) {
            "Character" -> {
                binding.toolbar.title = fragmentTag
                binding.contentLayout.bottomNavigation.visibility = View.VISIBLE
                binding.toolbar.navigationIcon = null
            }
            "Location" -> {
                binding.toolbar.title = fragmentTag
                binding.contentLayout.bottomNavigation.visibility = View.VISIBLE
                binding.toolbar.navigationIcon = null
            }
            "Episode" -> {
                binding.toolbar.title = fragmentTag
                binding.toolbar.navigationIcon = null
                binding.contentLayout.bottomNavigation.visibility = View.VISIBLE
            }
            else -> {
                binding.toolbar.title = fragmentTag
                binding.contentLayout.bottomNavigation.visibility = View.GONE
                binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
            }
        }
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        visibilityBottomNavigation(tag)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, "$fragment")
            .commit()
    }

    override fun replaceFragment(fragment: Fragment, tag: String) {
        visibilityBottomNavigation(tag)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun popUpToBackStack(tag: String) {
        visibilityBottomNavigation(tag)
        supportFragmentManager.popBackStack()
    }

    override fun removeFragment(fragment: Fragment, tag: String) {
        visibilityBottomNavigation(tag)
        supportFragmentManager
            .beginTransaction()
            .remove(fragment)
            .commit()
    }
}
