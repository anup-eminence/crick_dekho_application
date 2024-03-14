package com.example.cricdekho.ui.playerdetails.stat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.data.model.getPlayerStats.TablesItem
import com.example.cricdekho.databinding.StatFragmentBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.playerdetails.PlayerInfoViewModel
import com.example.cricdekho.ui.playerdetails.stat.adapter.BattingStatAdapter
import com.example.cricdekho.ui.playerdetails.stat.adapter.BowlingStatAdapter
import com.example.cricdekho.ui.playerdetails.stat.adapter.VsStatAdapter
import com.example.cricdekho.ui.playerdetails.stat.adapter.layout_manager.BowlingFullScrollLayoutManager
import com.example.cricdekho.ui.playerdetails.stat.adapter.layout_manager.FullScrollLayoutManager
import com.example.cricdekho.ui.playerdetails.stat.adapter.layout_manager.RecentMatchFullScrollLayoutManager
import com.example.cricdekho.ui.playerdetails.stat.adapter.layout_manager.TournamentBaowlingFullScrollLayoutManager
import com.example.cricdekho.ui.playerdetails.stat.adapter.layout_manager.TournamentBattingFullScrollLayoutManager
import com.example.cricdekho.ui.playerdetails.stat.adapter.recent_match_score.RecentMatchScoreDataAdapter
import com.example.cricdekho.ui.playerdetails.stat.adapter.recent_match_score.RecentMatchVsStatAdapter
import com.example.cricdekho.ui.playerdetails.stat.adapter.tournaments_stat.TournamentScoreDataAdapter
import com.example.cricdekho.ui.playerdetails.stat.adapter.tournaments_stat.TournamentVsStatAdapter

class PlayerStat  : BaseFragment(), FullScrollLayoutManager.ScrollListener,
    BowlingFullScrollLayoutManager.BowlingScrollListener,
    TournamentBaowlingFullScrollLayoutManager.ScrollListenerTournamentBowling,
    TournamentBattingFullScrollLayoutManager.ScrollListenerTournamentBatting,
    RecentMatchFullScrollLayoutManager.RecentMatchScrollListener {

    private lateinit var binding : StatFragmentBinding
    private lateinit var battingPerformanceScoreAdapter : BattingStatAdapter
    private lateinit var battingPerformanceVsStatAdapter : VsStatAdapter

    private lateinit var bowlingPerformanceScoreAdapter : BowlingStatAdapter
    private lateinit var bowlingPerformanceVsStatAdapter : VsStatAdapter

    private lateinit var battingFullScrollLayoutManager: FullScrollLayoutManager
    private lateinit var bowlingFullScrollLayoutManager: BowlingFullScrollLayoutManager

    private lateinit var tournamentBattingFullScrollLayoutManager: TournamentBattingFullScrollLayoutManager
    private lateinit var tournamentBaowlingFullScrollLayoutManager: TournamentBaowlingFullScrollLayoutManager
    private lateinit var recentMatchFullScrollLayoutManager: RecentMatchFullScrollLayoutManager

    private lateinit var tournamentScoreDataAdapter: TournamentScoreDataAdapter
    private lateinit var tournamentScoreBowlingDataAdapter: TournamentScoreDataAdapter
    private lateinit var tournamentBattingVsStatAdapter: TournamentVsStatAdapter
    private lateinit var tournamentBowlingVsStatAdapter: TournamentVsStatAdapter

    private lateinit var recentMatchScoreDataAdapter : RecentMatchScoreDataAdapter
    private lateinit var recentMatchVsStatAdapter: RecentMatchVsStatAdapter


    private val playerInfoViewModel : PlayerInfoViewModel by viewModels()

    private var t20List = emptyList<TablesItem?>()
    private var odiList = emptyList<TablesItem?>()
    private var testList = emptyList<TablesItem?>()

    private var bowlingT20List = emptyList<TablesItem?>()
    private var bowlingOdiList = emptyList<TablesItem?>()
    private var bowlingTestList = emptyList<TablesItem?>()


    private var bowlingTournamentList = emptyList<TablesItem?>()
    private var battingTournamentList = emptyList<TablesItem?>()

    private var recentMatchScoreList = emptyList<TablesItem?>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
    }

    private fun initObserver() {
        playerInfoViewModel.playerStats.observe(viewLifecycleOwner) { it ->
            println(">>>>>>>>>>>>>>>..it ${it.data}")
            it.data?.vSTeamStats?.forEach { vsItem ->
                if (vsItem?.title?.contains("Batting Performance") == true) {
                    vsItem.tables?.forEach { tableItem ->
                        if (tableItem?.type?.contains("t20i") == true) {
                            t20List = vsItem.tables
                        } else if (tableItem?.type?.contains("odi") == true) {
                            odiList = vsItem.tables
                        } else if (tableItem?.type?.contains("test") == true) {
                            testList = vsItem.tables
                        }
                    }
                } else if (vsItem?.title?.contains("Bowling Performance") == true){
                    binding.bowlingPerformanceLayout.battingTxt.text = vsItem.title?:""
                    vsItem.tables?.forEach { tableItem ->
                        if (tableItem?.type?.contains("t20i") == true) {
                            bowlingT20List = vsItem.tables
                        } else if (tableItem?.type?.contains("odi") == true) {
                            bowlingOdiList = vsItem.tables
                        } else if (tableItem?.type?.contains("test") == true) {
                            bowlingTestList = vsItem.tables
                        }
                    }
                }

            }

            it.data?.tournamentStats?.forEach {tournamentStatsItem ->
                if (tournamentStatsItem?.title?.contains("Batting Statistics") == true){
                    battingTournamentList = tournamentStatsItem.tables
                } else if (tournamentStatsItem?.title?.contains("Bowling Statistics") == true){
                    bowlingTournamentList = tournamentStatsItem.tables
                }
            }

            it?.data?.mostRecentMatches?.forEach {
                if (it?.tables?.isNotEmpty() == true){
                    recentMatchScoreList = it.tables

                }
            }

            if (recentMatchScoreList.isNotEmpty()){
                recentMatchVsStatAdapter.setData(recentMatchScoreList)
                recentMatchScoreDataAdapter.setData(recentMatchScoreList)
            }

            if (battingTournamentList.isNotEmpty()){
                tournamentScoreDataAdapter.setData(battingTournamentList)
                tournamentBattingVsStatAdapter.setData(battingTournamentList)
            }

            if (bowlingTournamentList.isNotEmpty()){
                tournamentBowlingVsStatAdapter.setData(bowlingTournamentList)
                tournamentScoreBowlingDataAdapter.setData(bowlingTournamentList)
            }

            if (t20List.isNotEmpty()){
                t20List[0]?.data?.let { it1 ->
                    battingPerformanceScoreAdapter.setData(it1)
                    battingPerformanceVsStatAdapter.setData(it1)

                }
            }

            if (bowlingT20List.isNotEmpty()){
                bowlingT20List[0]?.data?.let { data->
                    bowlingPerformanceScoreAdapter.setData(data)
                    bowlingPerformanceVsStatAdapter.setData(data)
                }
            }
        }

        playerInfoViewModel.getPlayerStats("virat-kohli")
    }

    private fun initViews() {
        battingPerformanceScoreAdapter = BattingStatAdapter()
        battingPerformanceVsStatAdapter = VsStatAdapter()

        bowlingPerformanceScoreAdapter = BowlingStatAdapter()
        bowlingPerformanceVsStatAdapter = VsStatAdapter()

        tournamentBattingVsStatAdapter = TournamentVsStatAdapter()
        tournamentBowlingVsStatAdapter = TournamentVsStatAdapter()
        tournamentScoreDataAdapter = TournamentScoreDataAdapter()
        tournamentScoreBowlingDataAdapter = TournamentScoreDataAdapter()

        recentMatchScoreDataAdapter = RecentMatchScoreDataAdapter()
        recentMatchVsStatAdapter = RecentMatchVsStatAdapter()

        battingFullScrollLayoutManager =
            FullScrollLayoutManager(
                requireContext(),
                this@PlayerStat
            )
        bowlingFullScrollLayoutManager =
            BowlingFullScrollLayoutManager(
                requireContext(),
                this@PlayerStat
            )

        tournamentBaowlingFullScrollLayoutManager =
            TournamentBaowlingFullScrollLayoutManager(
                requireContext(),
                this@PlayerStat
            )

        tournamentBattingFullScrollLayoutManager =
            TournamentBattingFullScrollLayoutManager(
                requireContext(),
                this@PlayerStat
            )

        recentMatchFullScrollLayoutManager =
            RecentMatchFullScrollLayoutManager(
                requireContext(),
                this@PlayerStat
            )


        binding.battingPerformanceLayout.leftRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = battingPerformanceVsStatAdapter
        }

        binding.battingPerformanceLayout.rightRv.apply {
            layoutManager = battingFullScrollLayoutManager
            adapter = battingPerformanceScoreAdapter
        }

        binding.bowlingPerformanceLayout.leftRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bowlingPerformanceVsStatAdapter
        }

        binding.bowlingPerformanceLayout.rightRv.apply {
            layoutManager = bowlingFullScrollLayoutManager
            adapter = bowlingPerformanceScoreAdapter
        }

        binding.tournametBattingPerformance.tounamentLeftRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tournamentBattingVsStatAdapter
        }

        binding.tournametBattingPerformance.tounamentRightRv.apply {
            layoutManager = tournamentBattingFullScrollLayoutManager
            adapter = tournamentScoreDataAdapter
        }

        binding.tournametBowlingPerformance.tounamentLeftRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tournamentBowlingVsStatAdapter
        }

        binding.tournametBowlingPerformance.tounamentRightRv.apply {
            layoutManager = tournamentBaowlingFullScrollLayoutManager
            adapter = tournamentScoreBowlingDataAdapter
        }

        binding.recentMatchLayout.tounamentLeftRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentMatchVsStatAdapter
        }

        binding.recentMatchLayout.tounamentRightRv.apply {
            layoutManager = recentMatchFullScrollLayoutManager
            adapter = recentMatchScoreDataAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun scrollBy(dx: Int) {
        binding.battingPerformanceLayout.topInnLayout.smoothScrollTo(dx,0)
        println(">>>>>>>>>>>>>>>.scroll $dx")
    }

    override fun scrollBowlingHeaderBy(dx: Int) {
        binding.bowlingPerformanceLayout.topInnLayout.smoothScrollTo(dx,0)
    }

    override fun scrollTournamentBowlingBy(dx: Int) {
        binding.tournametBowlingPerformance.topInnLayout.smoothScrollTo(dx,0)
    }

    override fun scrollTournamentBattingBy(dx: Int) {
        binding.tournametBattingPerformance.topInnLayout.smoothScrollTo(dx,0)
    }

    override fun scrollRecentMatchHeaderBy(dx: Int) {
        binding.recentMatchLayout.topInnLayout.smoothScrollTo(dx,0)
    }
}