package com.example.cricdekho.ui.playerdetails


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricdekho.ui.playerdetails.overview.OverviewFragment
import com.example.cricdekho.ui.playerdetails.stat.PlayerStat
import com.example.cricdekho.ui.teaminfo.news.TeamNewsFragment

class PDViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle,
    private val playerSlug: String,
    private val playerName: String
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverviewFragment.newInstance(playerSlug)
            1 -> PlayerStat.newInstance(playerSlug,playerName)
            2 -> TeamNewsFragment.newInstance(playerSlug,"player")
            else -> throw IndexOutOfBoundsException("Invalid position $position")
        }
    }
}