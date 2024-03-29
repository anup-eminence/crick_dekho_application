package com.example.cricdekho.data.model.getMatchDetails

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class PlayerOfMatch(
    val batting_stat: String,
    val bowling_stat: String,
    val player_name: String,
    val player_slug: String
) : Parcelable