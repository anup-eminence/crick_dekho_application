package com.example.cricdekho.data.model.getMatchDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Squad(
    val datetime: Int,
    val squad: List<SquadX>,

    val score_strip: @RawValue List<ScoreStrip>,
    val player_of_match: @RawValue PlayerOfMatch,
    val innings: List<Innings>,
    val commentary: @RawValue List<Commentary>,
    val message: String,

    val now_batting: @RawValue NowBatting,
    val now_bowling: @RawValue NowBowling,

    val away_team: String,
    val day: String,
    val day_remaining_over: String,
    val description: String,
    val endtime: Int,
    val game_format: Int,
    val game_state: Int,
    val game_type: String,
    val has_commentary: Boolean,
    val home_team: String,
    val in_play: Boolean,
    val info: String,
    val last_wkt: String,
    val last_wkt_min: String,
    val match_status: String,
    val mom: String,
    val referee: String,
    val secondary_info: String,
    val series: String,
    val series_keeda_slug: String,
    val session: String,
    val short_title: String,
    val stumps: Boolean,
    val title: String,
    val topic_slug: String,
    val umpires: String,
    val venue: String,
    val venue_country: String,
    val venue_location: String,
    val venue_stadium: String,
    val win_type_id: String,
    val winner_team: String,
    val auto_commentary: Boolean
) : Parcelable