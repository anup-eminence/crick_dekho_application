package com.example.cricdekho.ui.match

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricdekho.ui.match.live.LiveFragment
import com.example.cricdekho.ui.match.result.ResultFragment
import com.example.cricdekho.ui.match.upcoming.UpcomingFragment

class MatchViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LiveFragment()
            1 -> UpcomingFragment()
            2 -> ResultFragment()
            else -> throw IndexOutOfBoundsException("Invalid position $position")
        }
    }
}