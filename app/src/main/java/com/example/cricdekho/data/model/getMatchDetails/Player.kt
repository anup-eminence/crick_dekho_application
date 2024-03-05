package com.example.cricdekho.data.model.getMatchDetails

data class Player(
    val delta: Int,
    val name: String,
    val position: String,
    val provider_id: String,
    val role: String,
    val sk_slug: String,
    val slug: String,
    var playerImages: String
)