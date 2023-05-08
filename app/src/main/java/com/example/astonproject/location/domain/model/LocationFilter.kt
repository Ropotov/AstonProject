package com.example.astonproject.location.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationFilter(
    var name: String,
    var type: String,
    var dimension: String,
): Parcelable
