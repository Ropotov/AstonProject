package com.example.astonproject.app

import androidx.fragment.app.Fragment

interface Navigator {
    fun replaceFragment(fragment: Fragment, tag: String)
    fun popUpToBackStack(tag: String)
    fun removeFragment(fragment: Fragment, tag: String)
}