package com.example.cricdekho.ui.matchdetails

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cricdekho.data.model.getMatchDetails.Squad
import com.example.cricdekho.ui.matchdetails.commentary.CommentaryFragment
import com.example.cricdekho.ui.matchdetails.fantasy.FantasyMatchFragment
import com.example.cricdekho.ui.matchdetails.info.InfoFragment
import com.example.cricdekho.ui.matchdetails.scorecard.ScoreCardFragment
import com.example.cricdekho.ui.playerdetails.stat.PlayerStat

class MatchDetailViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var squad : ArrayList<Squad> = arrayListOf()

     fun setSquadList(squad: ArrayList<Squad>){
        this.squad = squad
    }
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InfoFragment.newInstance(squad)
            1 -> PlayerStat()
            2 -> CommentaryFragment.newInstance(squad)
            3 -> ScoreCardFragment.newInstance(squad)
            4 -> TrendingFragment()
            else -> throw IndexOutOfBoundsException("Invalid position $position")
        }
    }
}