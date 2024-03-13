package com.example.cricdekho.ui.playerdetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricdekho.ui.matchdetails.TrendingFragment
import com.example.cricdekho.ui.playerdetails.overview.OverviewFragment
import com.example.cricdekho.ui.playerdetails.stats.StatsFragment

class PDViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle, private val playerSlug: String
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverviewFragment.newInstance(playerSlug)
            1 -> StatsFragment.newInstance(playerSlug)
            2 -> TrendingFragment()
            else -> throw IndexOutOfBoundsException("Invalid position $position")
        }
    }
}