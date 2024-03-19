package com.example.cricdekho.theme

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.cricdekho.R

class DarkTheme : MyAppTheme {

    companion object {
        val ThemeId = 1
    }
    override fun activityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.black)
    }

    override fun activityImageRes(context: Context): Int {
        return R.drawable.app_logo
    }

    override fun activityIconColor(context: Context): Int {
        return  ContextCompat.getColor(context, R.color.white)
    }

    override fun activityTextColor(context: Context): Int {
      return  ContextCompat.getColor(context, R.color.white)
    }


    override fun id(): Int {
        return ThemeId
    }
}