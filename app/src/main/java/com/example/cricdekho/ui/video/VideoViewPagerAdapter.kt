package com.example.cricdekho.ui.video

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricdekho.ui.video.reels.ReelsFragment
import com.example.cricdekho.ui.video.videos.VideoFragment

class VideoViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ReelsFragment()
            1 -> VideoFragment()
            else -> throw IndexOutOfBoundsException("Invalid position $position")
        }
    }
}
