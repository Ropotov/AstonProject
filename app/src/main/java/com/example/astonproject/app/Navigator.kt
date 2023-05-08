package com.example.astonproject.app

import androidx.fragment.app.Fragment

interface Navigator {
    fun replaceFragment(fragment: Fragment)
    fun popUpToBackStack()
    fun removeFragment(fragment: Fragment)
}