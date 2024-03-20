package com.example.cricdekho.data.model.getMatchDetails

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class ScoreStrip(
    val currently_batting: Boolean,
    val name: String,
    val p_name: String,
    val run_rate: String,
    val run_rate_numeric: Double,
    var score: String,
    val short_name: String,
    val slug: String,
    val team_flag: String,
    val team_id: Int
) : Parcelable