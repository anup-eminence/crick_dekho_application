package com.example.cricdekho.ui.matchdetails.fantasy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.Squad
import com.example.cricdekho.data.model.getMatchDetails.SquadX
import com.example.cricdekho.data.model.getSeriesBestEconomy.ResponseEconomyRate
import com.example.cricdekho.data.model.getSeriesHighestStrikeRate.ResponseStrikeRate
import com.example.cricdekho.data.model.getSeriesMostRuns.ResponseMostRuns
import com.example.cricdekho.data.model.getSeriesMostWickets.ResponseMostWickets
import com.example.cricdekho.data.model.getUpcomingMatches.Tab
import com.example.cricdekho.databinding.FragmentFantasyMatchBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.match.upcoming.TabsAdapter
import com.example.cricdekho.ui.matchdetails.MatchDetailViewModel
import com.example.cricdekho.ui.matchdetails.MatchDetailsFragment
import com.example.cricdekho.ui.matchdetails.fantasy.adapter.EconomyRateAdapter
import com.example.cricdekho.ui.matchdetails.fantasy.adapter.MostRunsAdapter
import com.example.cricdekho.ui.matchdetails.fantasy.adapter.MostWicketAdapter
import com.example.cricdekho.ui.matchdetails.fantasy.adapter.StrikeRateAdapter
import com.example.cricdekho.ui.matchdetails.info.adapter.BenchTeam1Adapter
import com.example.cricdekho.ui.matchdetails.info.adapter.BenchTeam2Adapter
import com.example.cricdekho.ui.matchdetails.info.adapter.InfoTeam1Adapter
import com.example.cricdekho.ui.matchdetails.info.adapter.InfoTeam2Adapter

class FantasyMatchFragment : BaseFragment() {
    private lateinit var binding: FragmentFantasyMatchBinding
    private lateinit var infoTeam1Adapter: InfoTeam1Adapter
    private lateinit var infoTeam2Adapter: InfoTeam2Adapter
    private lateinit var benchTeam1Adapter: BenchTeam1Adapter
    private lateinit var benchTeam2Adapter: BenchTeam2Adapter
    private val matchDetailViewModel: MatchDetailViewModel by viewModels()
    private lateinit var mostRunsAdapter: MostRunsAdapter
    private lateinit var mostWicketAdapter: MostWicketAdapter
    private lateinit var strikeRateAdapter: StrikeRateAdapter
    private lateinit var economyRateAdapter: EconomyRateAdapter
    private var responseMostRuns: ArrayList<ResponseMostRuns>? = ArrayList()
    private var responseMostWickets: ArrayList<ResponseMostWickets>? = ArrayList()
    private var responseStrikeRate: ArrayList<ResponseStrikeRate>? = ArrayList()
    private var responseEconomyRate: ArrayList<ResponseEconomyRate>? = ArrayList()
    private lateinit var tabsAdapter: TabsAdapter
    private val tabsList: ArrayList<Tab> = arrayListOf()
    private var squad = ArrayList<Squad>()
    private var squadX = ArrayList<SquadX>()
    private var tournamentSlug: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            squad = it.getParcelableArrayList("squad")!!
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFantasyMatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MatchDetailsFragment.matchDetailsData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) {
                squad = it
                initView()
            }
        }
    }

    private fun initView() {
        squadX.clear()
        squadX.addAll(squad[0].squad)
        if (squadX.isNotEmpty()) {
            setPlayerImages(squadX[0])
            setPlayerImages(squadX[1])
        }
        tournamentSlug = squad[0].series_keeda_slug
        binding.apply {
            Glide.with(requireContext()).load(squadX[0].team_flag).into(ivFlag1)
            Glide.with(requireContext()).load(squadX[1].team_flag).into(ivFlag2)
            tvTeam1.text = squadX[0].team_shortname
            tvTeam2.text = squadX[1].team_shortname
        }
        if (squad[0].match_status != "pre") {
            setUpTeam1Adapter()
            setUpTeam2Adapter()
            setUpBenchTeam1Adapter()
            setUpBenchTeam2Adapter()
        } else {
            binding.apply {
                clTeams.visibility = View.GONE
                tvText.text = getString(R.string.player_stats_to_be_updated_after_toss)
                tvText.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            }
        }
        tabsList.clear()
        tabsList.addAll(
            listOf(
                Tab("Most Runs", ""),
                Tab("Most Wickets", ""),
                Tab("Best Strike rate", ""),
                Tab("Best Eco rate", "")
            )
        )
        setUpTabAdapter()
    }

    private fun setPlayerImages(squad: SquadX) {
        squad.players.forEach { player ->
            val matchingPlayerImage =
                squadX[0].playerImages.find { it.playerName == player.sk_slug }
            player.playerImages = matchingPlayerImage?.playerImageURL.toString()
        }

        squad.bench_players.forEach { benchPlayer ->
            val matchingPlayerImage =
                squadX[0].playerImages.find { it.playerName == benchPlayer.sk_slug }
            benchPlayer.playerImages = matchingPlayerImage?.playerImageURL.toString()
        }
    }

    private fun setUpTeam1Adapter() {
        infoTeam1Adapter = InfoTeam1Adapter()
        val recyclerViewState = binding.recyclerViewTeam1.layoutManager?.onSaveInstanceState()
        binding.recyclerViewTeam1.layoutManager = LinearLayoutManager(requireContext())
        infoTeam1Adapter.addAll(squadX[0].players, true)
        binding.recyclerViewTeam1.adapter = infoTeam1Adapter
        infoTeam1Adapter.notifyDataSetChanged()
        binding.recyclerViewTeam1.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    private fun setUpTeam2Adapter() {
        infoTeam2Adapter = InfoTeam2Adapter()
        val recyclerViewState = binding.recyclerViewTeam2.layoutManager?.onSaveInstanceState()
        binding.recyclerViewTeam2.layoutManager = LinearLayoutManager(requireContext())
        infoTeam2Adapter.addAll(squadX[1].players, true)
        binding.recyclerViewTeam2.adapter = infoTeam2Adapter
        infoTeam2Adapter.notifyDataSetChanged()
        binding.recyclerViewTeam2.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    private fun setUpBenchTeam1Adapter() {
        benchTeam1Adapter = BenchTeam1Adapter()
        val recyclerViewState = binding.recyclerViewBenchTeam1.layoutManager?.onSaveInstanceState()
        binding.recyclerViewBenchTeam1.layoutManager = LinearLayoutManager(requireContext())
        benchTeam1Adapter.addAll(squadX[0].bench_players, true)
        binding.recyclerViewBenchTeam1.adapter = benchTeam1Adapter
        benchTeam1Adapter.notifyDataSetChanged()
        binding.recyclerViewBenchTeam1.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    private fun setUpBenchTeam2Adapter() {
        benchTeam2Adapter = BenchTeam2Adapter()
        val recyclerViewState = binding.recyclerViewBenchTeam2.layoutManager?.onSaveInstanceState()
        binding.recyclerViewBenchTeam2.layoutManager = LinearLayoutManager(requireContext())
        benchTeam2Adapter.addAll(squadX[1].bench_players, true)
        binding.recyclerViewBenchTeam2.adapter = benchTeam2Adapter
        benchTeam2Adapter.notifyDataSetChanged()
        binding.recyclerViewBenchTeam2.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    private fun setUpTabAdapter() {
        tabsAdapter = TabsAdapter()
        val recyclerViewState = binding.recyclerViewTabs.layoutManager?.onSaveInstanceState()
        binding.recyclerViewTabs.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        tabsAdapter.addAll(tabsList, true)
        binding.recyclerViewTabs.adapter = tabsAdapter
        tabsAdapter.notifyDataSetChanged()
        binding.recyclerViewTabs.layoutManager?.onRestoreInstanceState(recyclerViewState)

        tabsAdapter.setRecyclerViewItemClick { itemView, model ->
            if (squad[0].match_status != "pre") {
                when (itemView.id) {
                    R.id.tvText -> handleTabSelection(model.name)
                }
            }
        }
    }

    private fun handleTabSelection(tabName: String) {
        progressBarListener.showProgressBar()
        when (tabName) {
            "Most Runs" -> {
                matchDetailViewModel.getSeriesMostRuns(tournamentSlug)
                matchDetailViewModel.mostRuns.observe(viewLifecycleOwner, Observer {
                    progressBarListener.hideProgressBar()
                    responseMostRuns?.clear()
                    responseMostRuns?.addAll(listOf(it))
                    mostRunsAdapter()
                })
            }

            "Most Wickets" -> {
                matchDetailViewModel.getSeriesMostWickets(tournamentSlug)
                matchDetailViewModel.mostWickets.observe(viewLifecycleOwner, Observer {
                    progressBarListener.hideProgressBar()
                    responseMostWickets?.clear()
                    responseMostWickets?.addAll(listOf(it))
                    mostWicketsAdapter()
                })
            }

            "Best Strike rate" -> {
                matchDetailViewModel.getSeriesHighestStrikeRate(tournamentSlug)
                matchDetailViewModel.strikeRate.observe(viewLifecycleOwner, Observer {
                    progressBarListener.hideProgressBar()
                    responseStrikeRate?.clear()
                    responseStrikeRate?.addAll(listOf(it))
                    strikeRateAdapter()
                })
            }

            "Best Eco rate" -> {
                matchDetailViewModel.getSeriesBestEconomy(tournamentSlug)
                matchDetailViewModel.economyRate.observe(viewLifecycleOwner, Observer {
                    progressBarListener.hideProgressBar()
                    responseEconomyRate?.clear()
                    responseEconomyRate?.addAll(listOf(it))
                    economyRateAdapter()
                })
            }
        }
    }

    private fun mostRunsAdapter() {
        mostRunsAdapter = MostRunsAdapter()
        binding.recyclerViewMost.layoutManager = LinearLayoutManager(requireContext())
        mostRunsAdapter.addAll(responseMostRuns?.get(0)?.data?.take(5), true)
        binding.recyclerViewMost.adapter = mostRunsAdapter
        mostRunsAdapter.notifyDataSetChanged()
    }

    private fun mostWicketsAdapter() {
        mostWicketAdapter = MostWicketAdapter()
        binding.recyclerViewMost.layoutManager = LinearLayoutManager(requireContext())
        mostWicketAdapter.addAll(responseMostWickets?.get(0)?.data?.take(5), true)
        binding.recyclerViewMost.adapter = mostWicketAdapter
        mostWicketAdapter.notifyDataSetChanged()
    }

    private fun strikeRateAdapter() {
        strikeRateAdapter = StrikeRateAdapter()
        binding.recyclerViewMost.layoutManager = LinearLayoutManager(requireContext())
        strikeRateAdapter.addAll(responseStrikeRate?.get(0)?.data?.take(5), true)
        binding.recyclerViewMost.adapter = strikeRateAdapter
        strikeRateAdapter.notifyDataSetChanged()
    }

    private fun economyRateAdapter() {
        economyRateAdapter = EconomyRateAdapter()
        binding.recyclerViewMost.layoutManager = LinearLayoutManager(requireContext())
        economyRateAdapter.addAll(responseEconomyRate?.get(0)?.data?.take(5), true)
        binding.recyclerViewMost.adapter = economyRateAdapter
        economyRateAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(squad: ArrayList<Squad>) = FantasyMatchFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("squad", ArrayList(squad))
            }
        }
    }
}