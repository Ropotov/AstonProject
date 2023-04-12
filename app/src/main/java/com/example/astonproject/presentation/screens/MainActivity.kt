package com.example.astonproject.presentation.screens

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.astonproject.R
import com.example.astonproject.databinding.ActivityMainBinding
import com.example.astonproject.presentation.screens.characterFragment.CharactersFragment
import com.example.astonproject.presentation.screens.episodeFragment.EpisodeFragment
import com.example.astonproject.presentation.screens.locationFragment.LocationFragment

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contentLayout.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.characters -> {
                    replaceFragment(CharactersFragment.newInstance())
                    binding.toolbar.title = getString(R.string.Characters)
                    true
                }
                R.id.location -> {
                    replaceFragment(LocationFragment.newInstance())
                    binding.toolbar.title = getString(R.string.location)
                    true
                }
                R.id.episodes -> {
                    replaceFragment(EpisodeFragment.newInstance())
                    binding.toolbar.title = getString(R.string.episodes)
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
