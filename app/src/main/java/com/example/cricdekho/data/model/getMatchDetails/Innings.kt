package com.example.cricdekho.data.model.getMatchDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Innings(
    val batting: @RawValue List<Batting>,
    val batting_team_id: Int,
    val bowling: @RawValue List<Bowling>,
    val bye: String,
    val extras: String,
    val fall_of_wickets_array: @RawValue List<FallOfWicketsArray>,
    val fours: String,
    val innings_no: Int,
    val legbye: String,
    val name: String,
    val noball: String,
    val overs: String,
    val penaties: String,
    val run_rate: Double,
    val runs: String,
    val short_name: String,
    val sixes: String,
    val wickets: String,
    val wide: String,
    val powerplay: @RawValue Any //@RawValue Powerplay
) : Parcelable