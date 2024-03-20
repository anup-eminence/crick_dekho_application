package com.example.cricdekho.util

import com.example.cricdekho.data.model.getMatchDetails.Squad

interface ToolbarListener {
    fun showToolBarMethod(
        title: String,
        menu: Boolean,
        logo: Boolean,
        search: Boolean,
        setting: Boolean,
        back: Boolean,
        share: Boolean,
        squad: Squad?=null

    )
}


/*
    ivMenu ivLogo ivSearch ivSetting
    ivBack tvTitle ivShare
*/
