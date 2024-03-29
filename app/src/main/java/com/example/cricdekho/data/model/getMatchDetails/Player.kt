package com.example.cricdekho.data.model.getMatchDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    var delta: Int,
    var name: String,
    var position: String,
    var provider_id: String,
    var role: String,
    var sk_slug: String,
    var slug: String,
    var playerImages: String
) : Parcelable