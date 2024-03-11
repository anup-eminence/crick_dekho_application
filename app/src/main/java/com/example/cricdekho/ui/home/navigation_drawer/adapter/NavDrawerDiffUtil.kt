package com.example.cricdekho.ui.home.navigation_drawer.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.cricdekho.ui.home.navigation_drawer.NavigationItem

class NavDrawerDiffUtil : DiffUtil.ItemCallback<NavigationItem>() {
    override fun areItemsTheSame(oldItem: NavigationItem, newItem: NavigationItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: NavigationItem, newItem: NavigationItem): Boolean {
       return oldItem == newItem
    }
}