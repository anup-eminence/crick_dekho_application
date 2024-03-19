package com.example.cricdekho.theme

import MySharedPreferences
import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.cricdekho.R
import com.example.cricdekho.util.Constants
import org.w3c.dom.Text


// themeId 1 meads darkmode

object CurrentTheme {
    var currentTheme = ""
    var textColor: Int? = null
    var themeId: Int? = null
    fun setCurrentTheme(textColor: Int, themeId: Int) {
        MySharedPreferences.writeInt(Constants.PrefConstantas.THEME_ID,themeId)
        this.textColor = textColor
        this.themeId = themeId
    }

    fun changeCardViewBackgroundColor(cardView: CardView, context: Context){
        if (themeId != null && themeId == 1) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.black))
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.white))
        }
    }
    fun changeTextColor(list: TextView, context: Context) {
        if (themeId != null && themeId == 1) {
            list.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            list.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

    fun changeIconColor(imageView: ImageView,  context: Context){
        if (themeId != null && themeId == 1) {
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.white))
        } else {
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.black))
        }
    }

    fun changeBackgroundColor(list: View, context: Context) {
        if (themeId != null && themeId == 1) {
            list.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
        } else {
            list.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }
}