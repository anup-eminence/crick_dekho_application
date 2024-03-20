package com.example.cricdekho.ui.matchdetails.fantasy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.BenchPlayer
import com.example.cricdekho.data.model.getMatchDetails.Player
import com.example.cricdekho.data.model.getMatchDetails.Squad
import com.example.cricdekho.data.model.getMatchDetails.SquadX
import com.example.cricdekho.data.model.getSeriesBestEconomy.ResponseEconomyRate
import com.example.cricdekho.data.model.getSeriesHighestStrikeRate.ResponseStrikeRate
import com.example.cricdekho.data.model.getSeriesMostRuns.ResponseMostRuns
import com.example.cricdekho.data.model.getSeriesMostWickets.ResponseMostWickets
import com.example.cricdekho.data.model.getUpcomingMatches.Tab
import com.example.cricdekho.databinding.FragmentFantasyMatchBinding
import com.example.cricdekho.theme.CurrentTheme
import com.example.cricdekho.ui.BenchPlayerListAdapter
import com.example.cricdekho.ui.InfoAdapter
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
import com.example.cricdekho.util.hide
import com.example.cricdekho.util.show

class FantasyMatchFragment : BaseFragment(), BenchPlayerListAdapter.BenchPlayerAdapterListener,
    InfoAdapter.InfoAdapterClickListener, TabsAdapter.OnItemClick {
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

    private lateinit var listAdapter : InfoAdapter
    private lateinit var listAdapter2 : InfoAdapter
    private lateinit var listAdapter3 : BenchPlayerListAdapter
    private lateinit var listAdapter4 : BenchPlayerListAdapter

    private var toggle = false

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
        setUpadpater()
        setUpTabAdapter()
        initTabAdapter()
        initTextColor()
        changeTogel()
        MatchDetailsFragment.matchDetailsData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) {
                squad = it
                initView()
            }
        }
    }

    private fun changeTogel() {
        binding.playerLayout.setOnClickListener {
            toggle = !toggle
            if (toggle){
                binding.playerData.show()
                binding.toggleBtn.rotation = 180F
            } else {
                binding.playerData.hide()
                binding.toggleBtn.rotation = 360F
            }
        }
    }

    private fun initTextColor() {
        CurrentTheme.changeTextColor(binding.tvPlaying,requireContext())
        CurrentTheme.changeTextColor(binding.tvTeam1,requireContext())
        CurrentTheme.changeTextColor(binding.tvTeam2,requireContext())
        CurrentTheme.changeTextColor(binding.tvBench,requireContext())
        CurrentTheme.changeTextColor(binding.tvSuggestion,requireContext())
        CurrentTheme.changeTextColor(binding.tvSuggestionText,requireContext())
        CurrentTheme.changeTextColor(binding.tvPlayerStats,requireContext())
        CurrentTheme.changeIconColor(binding.toggleBtn,requireContext())

    }

    private fun setUpadpater() {
        listAdapter = InfoAdapter()
        listAdapter.setInfoAdapterListener(this@FantasyMatchFragment)
        listAdapter2 = InfoAdapter()
        listAdapter2.setInfoAdapterListener(this@FantasyMatchFragment)
        listAdapter3 = BenchPlayerListAdapter()
        listAdapter3.setBenchAdapterListener(this@FantasyMatchFragment)
        listAdapter4 = BenchPlayerListAdapter()
        listAdapter4.setBenchAdapterListener(this@FantasyMatchFragment)

        binding.recyclerViewTeam1.adapter = listAdapter
        binding.recyclerViewTeam2.adapter = listAdapter2
        binding.recyclerViewBenchTeam1.adapter = listAdapter3
        binding.recyclerViewBenchTeam2.adapter = listAdapter4

        binding.recyclerViewTeam1.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTeam2.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBenchTeam1.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBenchTeam2.layoutManager = LinearLayoutManager(requireContext())
    }


    private fun initView() {
        squadX.clear()
        if (squad[0].squad.isNullOrEmpty()) return
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
            listAdapter.setData(squadX[0].players)
            listAdapter2.setData(squadX[1].players)
            listAdapter3.setData(squadX[0].bench_players)
            listAdapter4.setData(squadX[1].bench_players)
           /* setUpTeam1Adapter()
            setUpTeam2Adapter()
            setUpBenchTeam1Adapter()
            setUpBenchTeam2Adapter()*/
        } else {
            binding.apply {
                clTeams.visibility = View.GONE
                tvText.text = getString(R.string.player_stats_to_be_updated_after_toss)
                tvText.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            }
        }

    }

   private fun initTabAdapter() {
       tabsList.addAll(
           listOf(
               Tab("Most Runs", ""),
               Tab("Most Wickets", ""),
               Tab("Best Strike rate", ""),
               Tab("Best Eco rate", "")
           )
       )
       tabsAdapter.setData(tabsList)
    }

    private fun setPlayerImages(squad: SquadX) {
        if (!squad.players.isNullOrEmpty()) {
            squad.players.forEach { player ->
                val matchingPlayerImage =
                    squadX[0]?.playerImages?.find { it.playerName == player.sk_slug }
                player.playerImages = matchingPlayerImage?.playerImageURL.toString()
            }
        }

        if (!squad.bench_players.isNullOrEmpty()) {
            squad.bench_players.forEach { benchPlayer ->
                val matchingPlayerImage =
                    squadX[0]?.playerImages?.find { it.playerName == benchPlayer.sk_slug }
                benchPlayer.playerImages = matchingPlayerImage?.playerImageURL.toString()
            }
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
        tabsAdapter.setOnTabItemClick(this@FantasyMatchFragment)
        binding.recyclerViewTabs.apply {
            layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = tabsAdapter
        }
    }

    override fun onTabItemClick(tab: Tab) {
        if (squad[0].match_status != "pre") {
            handleTabSelection(tab.name)
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

    override fun onAdapterItemClick(player: Player) {
        val bundle = bundleOf("sk_slug" to player.sk_slug, "name" to player.name)
        findNavController().navigate(
            R.id.action_matchDetailsFragment_to_playerDetailsFragment, bundle
        )
    }

    override fun onBenchPlayerClick(player: BenchPlayer) {
        val bundle = bundleOf("sk_slug" to player.sk_slug, "name" to player.name)
        findNavController().navigate(
            R.id.action_matchDetailsFragment_to_playerDetailsFragment, bundle
        )
    }
}