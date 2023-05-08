package com.example.astonproject.episode.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeFilter(
    var name: String,
    var episode:String
): Parcelable
