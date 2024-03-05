package com.example.cricdekho.data.model.getMatchDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class SquadX(
    val bench_players: @RawValue List<BenchPlayer>,
    val impact_players: @RawValue Any,
    val players: @RawValue List<Player>,
    val squad: List<String>,
    val substitute_players: @RawValue Any,
    val team: String,
    val team_flag: String,
    val team_id: Int,
    val team_shortname: String,
    val team_slug: String,
    var playerImages: @RawValue List<PlayerImages>
) : Parcelable