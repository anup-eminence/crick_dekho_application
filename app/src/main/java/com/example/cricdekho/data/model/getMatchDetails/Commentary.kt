package com.example.cricdekho.data.model.getMatchDetails

data class Commentary(
    val _id: Id,
    val batsmen: List<Batsmen>,
    val bowler: String,
    val bowlers: List<Bowler>,
    val comment_edited: Boolean,
    var comment_text: String,
    val comment_type: String,
    val inning_number: String,
    val opta_ball_type: String,
    val over: String,
    val over_summary: String,
    var runs: Any,
    val score: String,
    val source: String,
    val timestamp: Long,
    val title: String,
    val custom_image: String,
    val custom_title: String,
    val custom_url: String
)