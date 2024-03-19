package com.example.cricdekho.ui.home.navigation_drawer

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.cricdekho.R

data class NavigationItem(
    val image: Drawable?,
    val name: String?
)

object NavUtils {
    fun provideNavigationList(context: Context): List<NavigationItem> {
        return listOf(
            NavigationItem(
                image = ContextCompat.getDrawable(context, R.drawable.info),
                name = ContextCompat.getString(context, R.string.about)
            ),
            NavigationItem(
                image = ContextCompat.getDrawable(context, R.drawable.bell),
                name = ContextCompat.getString(context, R.string.notification)
            ),
            NavigationItem(
                image = ContextCompat.getDrawable(context, R.drawable.edit_tools),
                name = ContextCompat.getString(context, R.string.editorial)
            ),
            NavigationItem(
                image = ContextCompat.getDrawable(context, R.drawable.gear),
                name = ContextCompat.getString(context, R.string.setting)
            )
        )
    }

}

