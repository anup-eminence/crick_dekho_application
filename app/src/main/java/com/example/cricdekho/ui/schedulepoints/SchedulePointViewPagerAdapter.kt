package com.example.cricdekho.ui.schedulepoints

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricdekho.ui.schedulepoints.points.PointsTableFragment
import com.example.cricdekho.ui.schedulepoints.schedule.ScheduleFragment

class SchedulePointViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val tournamentSlug: String
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ScheduleFragment.newInstance(tournamentSlug)
            1 -> PointsTableFragment.newInstance(tournamentSlug)
            else -> throw IndexOutOfBoundsException("Invalid position $position")
        }
    }
}