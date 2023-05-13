package com.example.astonproject.app

import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.astonproject.R
import com.example.astonproject.app.utils.CustomizeAppBarTitle
import com.example.astonproject.app.utils.ErrorFragment
import com.example.astonproject.app.utils.Navigator
import com.example.astonproject.character.presentation.character.CharactersFragment
import com.example.astonproject.databinding.ActivityMainBinding
import com.example.astonproject.episode.presentation.episode.EpisodeFragment
import com.example.astonproject.location.presentation.location.LocationFragment

class MainActivity : FragmentActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.container)!!

    private val component by lazy {
        (application as App).component
    }

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
        binding.contentLayout.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.characters -> {
                    if (currentFragment is CharactersFragment) {
                        true
                    } else {
                        replaceFragment(CharactersFragment.newInstance())
                        true
                    }
                }
                R.id.location -> {
                    if (currentFragment is LocationFragment) {
                        true
                    } else {
                        replaceFragment(LocationFragment.newInstance())
                        true
                    }
                }
                R.id.episodes -> {
                    if (currentFragment is EpisodeFragment) {
                        true
                    } else {
                        replaceFragment(EpisodeFragment.newInstance())
                        true
                    }
                }
                else -> false
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            popUpToBackStack()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when (currentFragment) {
            is CharactersFragment, is LocationFragment, is EpisodeFragment -> finish()
            else -> popUpToBackStack()
        }
    }

    private fun updateUi() {
        val fragment = currentFragment
        if (fragment is CustomizeAppBarTitle) {
            binding.toolbar.title = fragment.customTitle()
        }
        when (fragment) {
            is CharactersFragment, is LocationFragment, is EpisodeFragment -> {
                binding.toolbar.visibility = View.VISIBLE
                binding.toolbar.navigationIcon = null
                binding.contentLayout.bottomNavigation.visibility = View.VISIBLE
            }
            is ErrorFragment -> {
                binding.toolbar.visibility = View.GONE
                binding.contentLayout.bottomNavigation.visibility = View.GONE
            }
            else -> {
                binding.toolbar.visibility = View.VISIBLE
                binding.contentLayout.bottomNavigation.visibility = View.GONE
                binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
            }
        }
    }

    override fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }


    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun popUpToBackStack() {
        supportFragmentManager.popBackStack()
    }
}