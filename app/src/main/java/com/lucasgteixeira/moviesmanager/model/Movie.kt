package com.lucasgteixeira.moviesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val name: String,
    var releaseYear: String,
    var studio: String,
    var duration: String,
    var flag: String,
    var rating: String,
    var genra: String,
): Parcelable