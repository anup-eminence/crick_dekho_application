package com.example.cricdekho.util

interface ToolbarListener {
    fun showToolBarMethod(
        title: String,
        menu: Boolean,
        logo: Boolean,
        search: Boolean,
        setting: Boolean,
        back: Boolean,
        share: Boolean

    )
}


/*
    ivMenu ivLogo ivSearch ivSetting
    ivBack tvTitle ivShare
*/
