package com.example.astonproject.app.utils

import androidx.fragment.app.Fragment

interface Navigator {
    fun addFragment(fragment: Fragment)
    fun replaceFragment(fragment: Fragment)
    fun popUpToBackStack()
}