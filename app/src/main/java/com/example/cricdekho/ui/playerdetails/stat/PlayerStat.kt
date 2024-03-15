package com.example.cricdekho.ui.playerdetails.stat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getPlayerStats.DataItem
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
import com.example.cricdekho.ui.playerdetails.stats.StatsFragment

class PlayerStat : BaseFragment(), FullScrollLayoutManager.ScrollListener,
    BowlingFullScrollLayoutManager.BowlingScrollListener,
    TournamentBaowlingFullScrollLayoutManager.ScrollListenerTournamentBowling,
    TournamentBattingFullScrollLayoutManager.ScrollListenerTournamentBatting,
    RecentMatchFullScrollLayoutManager.RecentMatchScrollListener {

    private lateinit var binding: StatFragmentBinding
    private lateinit var battingPerformanceScoreAdapter: BattingStatAdapter
    private lateinit var battingPerformanceVsStatAdapter: VsStatAdapter

    private lateinit var bowlingPerformanceScoreAdapter: BowlingStatAdapter
    private lateinit var bowlingPerformanceVsStatAdapter: VsStatAdapter

    private lateinit var battingFullScrollLayoutManager: FullScrollLayoutManager
    private lateinit var bowlingFullScrollLayoutManager: BowlingFullScrollLayoutManager

    private lateinit var tournamentBattingFullScrollLayoutManager: TournamentBattingFullScrollLayoutManager
    private lateinit var tournamentBaowlingFullScrollLayoutManager: TournamentBaowlingFullScrollLayoutManager
    private lateinit var recentMatchFullScrollLayoutManager: RecentMatchFullScrollLayoutManager

    private lateinit var tournamentScoreDataAdapter: TournamentScoreDataAdapter
    private lateinit var tournamentScoreBowlingDataAdapter: TournamentScoreDataAdapter
    private lateinit var tournamentBattingVsStatAdapter: TournamentVsStatAdapter
    private lateinit var tournamentBowlingVsStatAdapter: TournamentVsStatAdapter

    private lateinit var recentMatchScoreDataAdapter: RecentMatchScoreDataAdapter
    private lateinit var recentMatchVsStatAdapter: RecentMatchVsStatAdapter

    private val playerInfoViewModel: PlayerInfoViewModel by viewModels()

    private var t20List = emptyList<DataItem?>()
    private var odiList = emptyList<DataItem?>()
    private var testList = emptyList<DataItem?>()

    private var bowlingT20List = emptyList<DataItem?>()
    private var bowlingOdiList = emptyList<DataItem?>()
    private var bowlingTestList = emptyList<DataItem?>()


    private var bowlingTournamentList = emptyList<TablesItem?>()
    private var battingTournamentList = emptyList<TablesItem?>()

    private var recentMatchScoreList = emptyList<TablesItem?>()

    private var player_slug = ""
    private var player_name = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
        intiClickListners()
    }

    private fun intiClickListners() {
        binding.t20Card.setOnClickListener {
            changeDataOnClick(ClickType.T20.name)
            changeBackgroundColor(ClickType.T20.name)
        }
        binding.odiCard.setOnClickListener {
            changeDataOnClick(ClickType.ODI.name)
            changeBackgroundColor(ClickType.ODI.name)
        }
        binding.testCard.setOnClickListener {
            changeDataOnClick(ClickType.TEST.name)
            changeBackgroundColor(ClickType.TEST.name)
        }
    }

    private fun changeBackgroundColor(clickType: String) {
        when(clickType) {
            ClickType.T20.name -> {
                binding.t20Card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
                binding.t20Txt.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.odiCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.odiTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                binding.testCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.testTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            ClickType.ODI.name -> {
                binding.odiCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
                binding.odiTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.t20Card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.t20Txt.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                binding.testCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.testTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            ClickType.TEST.name -> {
                binding.testCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
                binding.testTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.odiCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.odiTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                binding.t20Card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.t20Txt.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    private fun initObserver() {
        playerInfoViewModel.playerStats.observe(viewLifecycleOwner) { it ->
            it.data?.vSTeamStats?.forEach { vsItem ->
                if (vsItem?.title?.contains("Batting Performance") == true) {
                    vsItem.tables?.forEach { tableItem ->
                        if (tableItem?.type?.contains("t20i") == true) {
                            t20List = tableItem.data
                        } else if (tableItem?.type?.contains("odi") == true) {
                            odiList = tableItem.data
                        } else if (tableItem?.type?.contains("test") == true) {
                            testList = tableItem.data
                        }
                    }
                } else if (vsItem?.title?.contains("Bowling Performance") == true) {
                    binding.bowlingPerformanceLayout.battingTxt.text = vsItem.title ?: ""
                    vsItem.tables?.forEach { tableItem ->
                        if (tableItem?.type?.contains("t20i") == true) {
                            bowlingT20List = tableItem.data
                        } else if (tableItem?.type?.contains("odi") == true) {
                            bowlingOdiList = tableItem.data
                        } else if (tableItem?.type?.contains("test") == true) {
                            bowlingTestList = tableItem.data
                        }
                    }
                }

            }

            it.data?.tournamentStats?.forEach { tournamentStatsItem ->
                if (tournamentStatsItem?.title?.contains("Batting Statistics") == true) {
                    battingTournamentList = tournamentStatsItem.tables
                } else if (tournamentStatsItem?.title?.contains("Bowling Statistics") == true) {
                    bowlingTournamentList = tournamentStatsItem.tables
                }
            }

            it?.data?.mostRecentMatches?.forEach {
                if (it?.tables?.isNotEmpty() == true) {
                    recentMatchScoreList = it.tables

                }
            }

            if (recentMatchScoreList.isNotEmpty()) {
                recentMatchVsStatAdapter.setData(recentMatchScoreList)
                recentMatchScoreDataAdapter.setData(recentMatchScoreList)
            }

            if (battingTournamentList.isNotEmpty()) {
                tournamentScoreDataAdapter.setData(battingTournamentList)
                tournamentBattingVsStatAdapter.setData(battingTournamentList)
            }

            if (bowlingTournamentList.isNotEmpty()) {
                tournamentBowlingVsStatAdapter.setData(bowlingTournamentList)
                tournamentScoreBowlingDataAdapter.setData(bowlingTournamentList)
            }

            if (t20List.isNotEmpty()) {
                battingPerformanceScoreAdapter.setData(t20List)
                battingPerformanceVsStatAdapter.setData(t20List)
                calculateT20BattingData()
            }

            if (bowlingT20List.isNotEmpty()) {
                bowlingPerformanceScoreAdapter.setData(bowlingT20List)
                bowlingPerformanceVsStatAdapter.setData(bowlingT20List)
                calculateT20BowlingData()

            }
        }
        if (player_slug.isNullOrEmpty().not()){
            playerInfoViewModel.getPlayerStats(player_slug)
        }
        if (player_name.isNullOrEmpty().not()){
            binding.playerStatTxt.text = "$player_name Statistics"
        }
    }

    private fun calculateT20BattingData(){
        var inn : Int = 0
        var no : Int= 0
        var r : Int = 0
        var bf : Int = 0
        var t_100s : Int = 0
        var t_50s : Int = 0
        var t_4s : Int = 0
        var t_6s : Int = 0
        var t_sr : Float = 0F
        var avg : Float = 0F
        var h : Int = 0
        var b : Int = 0
        var w : Int = 0
        var er : Int = 0
        var t_5w : Int = 0
        var t_4w : Int = 0
        var t_10w : Int = 0

        t20List.forEach {
            inn += it?.inn?.toIntOrNull()?:0
            no  += it?.nO?.toIntOrNull()?:0
            r  += it?.r?.toIntOrNull()?:0
            bf  += it?.bF?.toIntOrNull()?:0
            t_100s  += it?.jsonMember100s?.toIntOrNull()?:0
            t_50s += it?.jsonMember50s?.toIntOrNull()?:0
            t_4s += it?.jsonMember4s?.toIntOrNull()?:0
            t_6s += it?.jsonMember6s?.toIntOrNull()?:0
            t_sr += it?.sR?.toFloat()?:0F
            avg += it?.avg?.toFloat()?:0F
            h += it?.h?.toIntOrNull()?:0
            b += it?.b?.toIntOrNull()?:0
            w += it?.w?.toIntOrNull()?:0
            er += it?.eR?.toIntOrNull()?:0
            t_5w += it?.jsonMember5w?.toIntOrNull()?:0
            t_10w += it?.jsonMember10w?.toIntOrNull()?:0
        }

        binding.battingPerformanceLayout.topInnLayoutData.apply {
            this.inn.text = inn.toString()
            this.no.text = no.toString()
            this.r.text = r.toString()
            this.bf.text = bf.toString()
            this.t100s.text = t_100s.toString()
            this.t50s.text = t_50s.toString()
            this.t4s.text = t_4s.toString()
            this.t6s.text = t_6s.toString()
            this.sr.text = t_sr.toString()
            this.avg.text = avg.toString()
            this.h.text = h.toString()
        }
    }

    private fun calculateT20BowlingData() {
        var inn : Float = 0F
        var no : Float = 0F
        var r : Float = 0F
        var bf : Int = 0
        var t_100s : Int = 0
        var t_50s : Int = 0
        var t_4s : Int = 0
        var t_6s : Int = 0
        var t_sr : Int = 0
        var avg : Float = 0F
        var h : Int = 0
        var b : Int = 0
        var w : Int = 0
        var er : Float = 0F
        var t_5w : Int = 0
        var t_4w : Int = 0
        var t_10w : Int = 0
        var dots : Int = 0
        var mdn : Int = 0


        bowlingT20List.forEach {
            inn += it?.inn?.toIntOrNull()?:0
            no  += it?.nO?.toIntOrNull()?:0
            r  += it?.r?.toIntOrNull()?:0
            bf  += it?.bF?.toIntOrNull()?:0
            t_100s  += it?.jsonMember100s?.toIntOrNull()?:0
            t_50s += it?.jsonMember50s?.toIntOrNull()?:0
            t_4s += it?.jsonMember4s?.toIntOrNull()?:0
            t_6s += it?.jsonMember6s?.toIntOrNull()?:0
            t_sr += it?.sR?.toIntOrNull()?:0
            avg += it?.avg?.toFloat()?:0F
            h += it?.h?.toIntOrNull()?:0
            b += it?.b?.toIntOrNull()?:0
            w += it?.w?.toIntOrNull()?:0
            println(">>>>>>>>>>>>>>.er ${it?.eR?.toIntOrNull()}")
            er += it?.eR?.toFloat()?:0F
            t_5w += it?.jsonMember5w?.toIntOrNull()?:0
            t_4w += it?.jsonMember4w?.toIntOrNull()?:0
            t_10w += it?.jsonMember10w?.toIntOrNull()?:0
            dots += it?.dots?.toIntOrNull()?:0
            mdn += it?.mdns?.toIntOrNull()?:0
        }

        binding.bowlingPerformanceLayout.topInnLayoutData.apply {
            this.inn.text = inn.toString()
            this.b.text = b.toString()
            this.r.text = r.toString()
            this.w.text = w.toString()
            this.er.text = er.toString()
            this.dots.text = dots.toString()
            this.mdns.text = mdn.toString()
            this.t4w.text = t_4w.toString()
            this.t5w.text = t_5w.toString()
            this.t10w.text = t_10w.toString()
            this.avg.text = avg.toString()
        }
    }

    private fun calculateOdiBattingData(){
        var inn : Int = 0
        var no : Int= 0
        var r : Int = 0
        var bf : Int = 0
        var t_100s : Int = 0
        var t_50s : Int = 0
        var t_4s : Int = 0
        var t_6s : Int = 0
        var t_sr : Float = 0F
        var avg : Float = 0F
        var h : Int = 0
        var b : Int = 0
        var w : Int = 0
        var er : Int = 0
        var t_5w : Int = 0
        var t_4w : Int = 0
        var t_10w : Int = 0

        odiList.forEach {
            inn += it?.inn?.toIntOrNull()?:0
            no  += it?.nO?.toIntOrNull()?:0
            r  += it?.r?.toIntOrNull()?:0
            bf  += it?.bF?.toIntOrNull()?:0
            t_100s  += it?.jsonMember100s?.toIntOrNull()?:0
            t_50s += it?.jsonMember50s?.toIntOrNull()?:0
            t_4s += it?.jsonMember4s?.toIntOrNull()?:0
            t_6s += it?.jsonMember6s?.toIntOrNull()?:0
            t_sr += it?.sR?.toFloat()?:0F
            avg += it?.avg?.toFloat()?:0F
            h += it?.h?.toIntOrNull()?:0
            b += it?.b?.toIntOrNull()?:0
            w += it?.w?.toIntOrNull()?:0
            er += it?.eR?.toIntOrNull()?:0
            t_5w += it?.jsonMember5w?.toIntOrNull()?:0
            t_10w += it?.jsonMember10w?.toIntOrNull()?:0
        }

        binding.battingPerformanceLayout.topInnLayoutData.apply {
            this.inn.text = inn.toString()
            this.no.text = no.toString()
            this.r.text = r.toString()
            this.bf.text = bf.toString()
            this.t100s.text = t_100s.toString()
            this.t50s.text = t_50s.toString()
            this.t4s.text = t_4s.toString()
            this.t6s.text = t_6s.toString()
            this.sr.text = t_sr.toString()
            this.avg.text = avg.toString()
            this.h.text = h.toString()
        }
    }

    private fun calculateOdiBowlingData() {
        var inn : Int = 0
        var no : Int= 0
        var r : Int = 0
        var bf : Int = 0
        var t_100s : Int = 0
        var t_50s : Int = 0
        var t_4s : Int = 0
        var t_6s : Int = 0
        var t_sr : Int = 0
        var avg : Float = 0F
        var h : Int = 0
        var b : Int = 0
        var w : Int = 0
        var er : Float = 0F
        var t_5w : Int = 0
        var t_4w : Int = 0
        var t_10w : Int = 0
        var dots : Int = 0
        var mdns : Int = 0


        bowlingOdiList.forEach {
            inn += it?.inn?.toIntOrNull()?:0
            no  += it?.nO?.toIntOrNull()?:0
            r  += it?.r?.toIntOrNull()?:0
            bf  += it?.bF?.toIntOrNull()?:0
            t_100s  += it?.jsonMember100s?.toIntOrNull()?:0
            t_50s += it?.jsonMember50s?.toIntOrNull()?:0
            t_4s += it?.jsonMember4s?.toIntOrNull()?:0
            t_6s += it?.jsonMember6s?.toIntOrNull()?:0
            t_sr += it?.sR?.toIntOrNull()?:0
            avg += it?.avg?.toFloat()?:0F
            h += it?.h?.toIntOrNull()?:0
            b += it?.b?.toIntOrNull()?:0
            w += it?.w?.toIntOrNull()?:0
            er = er.plus(it?.eR?.toFloat()?:0F)
            t_5w += it?.jsonMember5w?.toIntOrNull()?:0
            t_4w += it?.jsonMember4w?.toIntOrNull()?:0
            t_10w += it?.jsonMember10w?.toIntOrNull()?:0
            dots += it?.dots?.toIntOrNull()?:0
            mdns += it?.mdns?.toIntOrNull()?:0
        }

        binding.bowlingPerformanceLayout.topInnLayoutData.apply {
            this.inn.text = inn.toString()
            this.b.text = b.toString()
            this.r.text = r.toString()
            this.w.text = w.toString()
            this.er.text = er.toString()
            this.dots.text = dots.toString()
            this.mdns.text = mdns.toString()
            this.t4w.text = t_4w.toString()
            this.t5w.text = t_5w.toString()
            this.t10w.text = t_10w.toString()
            this.avg.text = avg.toString()
        }
    }

    private fun calculateTestBattingData(){
        var inn : Int = 0
        var no : Int= 0
        var r : Int = 0
        var bf : Int = 0
        var t_100s : Int = 0
        var t_50s : Int = 0
        var t_4s : Int = 0
        var t_6s : Int = 0
        var t_sr : Float = 0F
        var avg : Float = 0F
        var h : Int = 0
        var b : Int = 0
        var w : Int = 0
        var er : Int = 0
        var t_5w : Int = 0
        var t_4w : Int = 0
        var t_10w : Int = 0

        testList.forEach {
            inn += it?.inn?.toIntOrNull()?:0
            no  += it?.nO?.toIntOrNull()?:0
            r  += it?.r?.toIntOrNull()?:0
            bf  += it?.bF?.toIntOrNull()?:0
            t_100s  += it?.jsonMember100s?.toIntOrNull()?:0
            t_50s += it?.jsonMember50s?.toIntOrNull()?:0
            t_4s += it?.jsonMember4s?.toIntOrNull()?:0
            t_6s += it?.jsonMember6s?.toIntOrNull()?:0
            t_sr += it?.sR?.toFloat()?:0F
            avg += it?.avg?.toFloat()?:0F
            h += it?.h?.toIntOrNull()?:0
            b += it?.b?.toIntOrNull()?:0
            w += it?.w?.toIntOrNull()?:0
            er += it?.eR?.toIntOrNull()?:0
            t_5w += it?.jsonMember5w?.toIntOrNull()?:0
            t_10w += it?.jsonMember10w?.toIntOrNull()?:0
        }

        binding.battingPerformanceLayout.topInnLayoutData.apply {
            this.inn.text = inn.toString()
            this.no.text = no.toString()
            this.r.text = r.toString()
            this.bf.text = bf.toString()
            this.t100s.text = t_100s.toString()
            this.t50s.text = t_50s.toString()
            this.t4s.text = t_4s.toString()
            this.t6s.text = t_6s.toString()
            this.sr.text = t_sr.toString()
            this.avg.text = avg.toString()
            this.h.text = h.toString()
        }
    }

    private fun calculateTestBowlingData() {
        var inn : Int = 0
        var no : Int= 0
        var r : Int = 0
        var bf : Int = 0
        var t_100s : Int = 0
        var t_50s : Int = 0
        var t_4s : Int = 0
        var t_6s : Int = 0
        var t_sr : Int = 0
        var avg : Int = 0
        var h : Int = 0
        var b : Int = 0
        var w : Int = 0
        var er : Float = 0F
        var t_5w : Int = 0
        var t_4w : Int = 0
        var t_10w : Int = 0
        var dots : Int = 0
        var mdns : Int = 0


        bowlingTestList.forEach {
            inn += it?.inn?.toIntOrNull()?:0
            no  += it?.nO?.toIntOrNull()?:0
            r  += it?.r?.toIntOrNull()?:0
            bf  += it?.bF?.toIntOrNull()?:0
            t_100s  += it?.jsonMember100s?.toIntOrNull()?:0
            t_50s += it?.jsonMember50s?.toIntOrNull()?:0
            t_4s += it?.jsonMember4s?.toIntOrNull()?:0
            t_6s += it?.jsonMember6s?.toIntOrNull()?:0
            t_sr += it?.sR?.toIntOrNull()?:0
            avg += it?.avg?.toIntOrNull()?:0
            h += it?.h?.toIntOrNull()?:0
            b += it?.b?.toIntOrNull()?:0
            w += it?.w?.toIntOrNull()?:0
            er = er.plus(it?.eR?.toFloat()?:0F)
            t_5w += it?.jsonMember5w?.toIntOrNull()?:0
            t_4w += it?.jsonMember4w?.toIntOrNull()?:0
            t_10w += it?.jsonMember10w?.toIntOrNull()?:0
            dots += it?.dots?.toIntOrNull()?:0
            mdns += it?.mdns?.toIntOrNull()?:0
        }

        binding.bowlingPerformanceLayout.topInnLayoutData.apply {
            this.inn.text = inn.toString()
            this.b.text = b.toString()
            this.r.text = r.toString()
            this.w.text = w.toString()
            this.er.text = er.toString()
            this.dots.text = dots.toString()
            this.mdns.text = mdns.toString()
            this.t4w.text = t_4w.toString()
            this.t5w.text = t_5w.toString()
            this.t10w.text = t_10w.toString()
            this.avg.text = avg.toString()
        }
    }


    private fun changeDataOnClick(clickType: String) {
        when (clickType) {
            ClickType.T20.name -> {
                if (t20List.isNotEmpty()) {
                    battingPerformanceScoreAdapter.setData(t20List)
                    battingPerformanceVsStatAdapter.setData(t20List)
                }

                if (bowlingT20List.isNotEmpty()) {
                    bowlingPerformanceScoreAdapter.setData(bowlingT20List)
                    bowlingPerformanceVsStatAdapter.setData(bowlingT20List)

                }
                calculateT20BattingData()
                calculateT20BowlingData()
            }

            ClickType.ODI.name -> {
                if (odiList.isNotEmpty()) {
                    println(">>>>>>>>>>clciccccccc")
                    battingPerformanceScoreAdapter.setData(odiList)
                    battingPerformanceVsStatAdapter.setData(odiList)

                }

                if (bowlingOdiList.isNotEmpty()) {
                    bowlingPerformanceScoreAdapter.setData(bowlingOdiList)
                    bowlingPerformanceVsStatAdapter.setData(bowlingOdiList)

                }
                calculateOdiBattingData()
                calculateOdiBowlingData()
            }

            ClickType.TEST.name -> {
                if (testList.isNotEmpty()) {
                    battingPerformanceScoreAdapter.setData(testList)
                    battingPerformanceVsStatAdapter.setData(testList)

                }

                if (bowlingTestList.isNotEmpty()) {
                    bowlingPerformanceScoreAdapter.setData(bowlingTestList)
                    bowlingPerformanceVsStatAdapter.setData(bowlingTestList)
                }
                calculateTestBattingData()
                calculateTestBowlingData()
            }
        }
    }

    private fun initViews() {
        binding.t20Card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
        binding.t20Txt.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

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
        binding.battingPerformanceLayout.topInnLayout.smoothScrollTo(dx, 0)
        println(">>>>>>>>>>>>>>>.scroll $dx")
    }

    override fun scrollBowlingHeaderBy(dx: Int) {
        binding.bowlingPerformanceLayout.topInnLayout.smoothScrollTo(dx, 0)
    }

    override fun scrollTournamentBowlingBy(dx: Int) {
        binding.tournametBowlingPerformance.topInnLayout.smoothScrollTo(dx, 0)
    }

    override fun scrollTournamentBattingBy(dx: Int) {
        binding.tournametBattingPerformance.topInnLayout.smoothScrollTo(dx, 0)
    }

    override fun scrollRecentMatchHeaderBy(dx: Int) {
        binding.recentMatchLayout.topInnLayout.smoothScrollTo(dx, 0)
    }

    companion object {
        @JvmStatic
        fun newInstance(playerSlug: String,playerName : String) =
            PlayerStat().apply {
               player_name = playerName
                player_slug = playerSlug
            }
    }

}

enum class ClickType {
    T20,
    ODI,
    TEST
}