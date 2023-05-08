package com.example.astonproject.character.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterFilter(
    var name: String,
    var gender:String,
    val species: String,
    var status: String
): Parcelable
