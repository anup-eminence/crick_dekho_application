package com.example.cricdekho.theme

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.cricdekho.R

class LightTheme : MyAppTheme {

    companion object {
        val ThemeId = 0
    }
    override fun activityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.white)
    }

    override fun activityImageRes(context: Context): Int {
        return R.drawable.app_logo
    }

    override fun activityIconColor(context: Context): Int {
        return  ContextCompat.getColor(context, R.color.black)
    }

    override fun activityTextColor(context: Context): Int {
      return  ContextCompat.getColor(context, R.color.text_color_black)
    }


    override fun id(): Int {
        return ThemeId
    }
}