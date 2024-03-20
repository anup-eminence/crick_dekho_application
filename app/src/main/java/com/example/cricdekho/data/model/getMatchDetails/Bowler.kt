package com.example.cricdekho.data.model.getMatchDetails

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Bowler(
    val name: String,
    val player_id: Int,
    val runs_conceded: Int,
    val wickets: Int
) : Parcelable