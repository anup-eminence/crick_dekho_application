package com.example.cricdekho.data.model.getMatchDetails

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Batsmen(
    val balls: Int,
    val is_playing: Boolean,
    var name: String,
    val player_id: Int,
    val runs: Int
) : Parcelable