package com.example.cricdekho.data.model.getMatchDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BenchPlayer(
    val delta: Int,
    val name: String,
    val position: String,
    val provider_id: String,
    val role: String,
    val sk_slug: String,
    val slug: String,
    var playerImages: String
): Parcelable