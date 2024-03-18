package com.example.cricdekho.ui.teaminfo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricdekho.ui.matchdetails.TrendingFragment
import com.example.cricdekho.ui.teaminfo.about.AboutTeamFragment
import com.example.cricdekho.ui.teaminfo.news.TeamNewsFragment
import com.example.cricdekho.ui.teaminfo.squad.SquadFragment

class TeamInfoViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle, private val tournamentSlug: String, private val seriesKeedaSlug: String
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AboutTeamFragment.newInstance(tournamentSlug)
            1 -> TeamNewsFragment.newInstance(seriesKeedaSlug)
            2 -> SquadFragment.newInstance(tournamentSlug)
            else -> throw IndexOutOfBoundsException("Invalid position $position")
        }
    }
}