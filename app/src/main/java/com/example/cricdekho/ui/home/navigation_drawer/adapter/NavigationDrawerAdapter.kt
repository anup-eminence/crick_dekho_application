package com.example.cricdekho.ui.home.navigation_drawer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.databinding.DrawerMenuBinding
import com.example.cricdekho.ui.home.navigation_drawer.NavigationItem

class NavigationDrawerAdapter : ListAdapter<NavigationItem, NavigationDrawerAdapter.NavDrawerVH>(NavDrawerDiffUtil()) {

    class NavDrawerVH(val binding : DrawerMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavDrawerVH {
        return NavDrawerVH(DrawerMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NavDrawerVH, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            itemImage.setImageDrawable(item.image)
            itemName.text = item.name
        }
    }

}