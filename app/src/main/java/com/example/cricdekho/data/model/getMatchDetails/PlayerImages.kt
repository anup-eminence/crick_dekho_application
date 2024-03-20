package com.example.cricdekho.data.model.getMatchDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerImages(
    val playerName: String,
    val playerImageURL: String
) : Parcelable
