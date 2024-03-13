package com.example.cricdekho.ui.matchdetails.info

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.BenchPlayer
import com.example.cricdekho.data.model.getMatchDetails.Player
import com.example.cricdekho.data.model.getMatchDetails.Squad
import com.example.cricdekho.data.model.getMatchDetails.SquadX
import com.example.cricdekho.databinding.FragmentInfoBinding
import com.example.cricdekho.ui.BenchPlayerListAdapter
import com.example.cricdekho.ui.InfoAdapter
import com.example.cricdekho.ui.matchdetails.MatchDetailsFragment
import com.example.cricdekho.ui.matchdetails.info.adapter.BenchTeam1Adapter
import com.example.cricdekho.ui.matchdetails.info.adapter.BenchTeam2Adapter
import com.example.cricdekho.ui.matchdetails.info.adapter.InfoTeam1Adapter
import com.example.cricdekho.ui.matchdetails.info.adapter.InfoTeam2Adapter
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class InfoFragment : Fragment(), InfoAdapter.InfoAdapterClickListener,
    BenchPlayerListAdapter.BenchPlayerAdapterListener {
    private lateinit var binding: FragmentInfoBinding
    private lateinit var infoTeam1Adapter: InfoTeam1Adapter
    private lateinit var infoTeam2Adapter: InfoTeam2Adapter
    private lateinit var benchTeam1Adapter: BenchTeam1Adapter
    private lateinit var benchTeam2Adapter: BenchTeam2Adapter
    private var squad = ArrayList<Squad>()
    private var squadX = ArrayList<SquadX>()
    private var playerList = ArrayList<Player>()
    private var playerList2 = ArrayList<Player>()

    private lateinit var listAdapter: InfoAdapter
    private lateinit var listAdapter2: InfoAdapter
    private lateinit var listAdapter3: BenchPlayerListAdapter
    private lateinit var listAdapter4: BenchPlayerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            squad = it.getParcelableArrayList("squad")!!
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpadpater()
        MatchDetailsFragment.matchDetailsData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                squad = it
                initView()
                binding.clMain.isVisible = true
                println(">>>>>>>>>>>>>>>>>.printdata")
            }
        }
        setOnClickListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {
        squadX.addAll(squad[0].squad)
        if (squadX.isNotEmpty()) {
            setPlayerImages(squadX[0])
            setPlayerImages(squadX[1])
        }

        binding.apply {
            txtToss.text = squad[0].secondary_info
            txtMatch.text = "${squad[0].description}\n${squad[0].series}"
            txtDateTime.text = convertUnixTimestamp(squad[0].datetime.toLong())
            txtVenue.text = squad[0].venue
            txtUmpires.text = squad[0].umpires

            Glide.with(requireContext()).load(squadX[0].team_flag).into(ivFlag1)
            Glide.with(requireContext()).load(squadX[1].team_flag).into(ivFlag2)
            tvTeam1.text = squadX[0].team_shortname
            tvTeam2.text = squadX[1].team_shortname
        }
        if (squad[0].match_status != "pre") {
            playerList.clear()
            playerList.addAll(squadX[0].players)
            listAdapter.setData(playerList)
            listAdapter2.setData(squadX[1].players)
            listAdapter3.setData(squadX[0].bench_players)
            listAdapter4.setData(squadX[1].bench_players)
            /*setUpTeam1Adapter()*/
            /* setUpTeam2Adapter()
             setUpBenchTeam1Adapter()
             setUpBenchTeam2Adapter()*/
        } else {
            binding.apply {
                if (!squadX[0].players.isNullOrEmpty() || !squadX[0].bench_players.isNullOrEmpty()) {
                    tvBench.visibility = View.GONE
                    tvPlaying.text = getString(R.string.squad)
                    tvLineup.visibility = View.GONE
                } else {
                    tvBench.visibility = View.GONE
                    ivFlag1.visibility = View.GONE
                    ivFlag2.visibility = View.GONE
                    tvTeam1.visibility = View.GONE
                    tvTeam2.visibility = View.GONE
                    clTeams.background = null
                    tvPlaying.text = getString(R.string.squad)
                    tvLineup.text = getString(R.string.to_be_announced)
                    tvLineup.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
                }
            }
            /*setUpBenchTeam1Adapter()
            setUpBenchTeam2Adapter()*/
            listAdapter3.setData(squadX[0].bench_players)
            listAdapter4.setData(squadX[1].bench_players)
        }
    }

    private fun setOnClickListener() {
        binding.tvSchedule.setOnClickListener {
            val bundle = bundleOf("tournament_slug" to squad[0].series_keeda_slug)
            findNavController().navigate(
                R.id.action_matchDetailsFragment_to_schedulePointsFragment,
                bundle
            )
        }
    }

    private fun setPlayerImages(squad: SquadX) {
        squad.players.forEach { player ->
            val matchingPlayerImage =
                squadX[0]?.playerImages?.find { it.playerName == player.sk_slug }
            player.playerImages = matchingPlayerImage?.playerImageURL.toString()
        }

        squad.bench_players.forEach { benchPlayer ->
            val matchingPlayerImage =
                squadX[0]?.playerImages?.find { it.playerName == benchPlayer.sk_slug }
            benchPlayer.playerImages = matchingPlayerImage?.playerImageURL.toString()
        }
    }


    private fun setUpadpater() {
        listAdapter = InfoAdapter()
        listAdapter.setInfoAdapterListener(this@InfoFragment)
        listAdapter2 = InfoAdapter()
        listAdapter2.setInfoAdapterListener(this@InfoFragment)
        listAdapter3 = BenchPlayerListAdapter()
        listAdapter3.setBenchAdapterListener(this@InfoFragment)
        listAdapter4 = BenchPlayerListAdapter()
        listAdapter4.setBenchAdapterListener(this@InfoFragment)

        binding.recyclerViewTeam1.adapter = listAdapter
        binding.recyclerViewTeam2.adapter = listAdapter2
        binding.recyclerViewBenchTeam1.adapter = listAdapter3
        binding.recyclerViewBenchTeam2.adapter = listAdapter4

        binding.recyclerViewTeam1.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTeam2.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBenchTeam1.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBenchTeam2.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setUpTeam1Adapter() {
        infoTeam1Adapter = InfoTeam1Adapter()
        val recyclerViewState = binding.recyclerViewTeam1.layoutManager?.onSaveInstanceState()
        binding.recyclerViewTeam1.layoutManager = LinearLayoutManager(requireContext())
        infoTeam1Adapter.addAll(squadX[0].players, true)
        binding.recyclerViewTeam1.adapter = infoTeam1Adapter
        infoTeam1Adapter.notifyDataSetChanged()
        binding.recyclerViewTeam1.layoutManager?.onRestoreInstanceState(recyclerViewState)

        infoTeam1Adapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.clItem -> {
                    val bundle = bundleOf("sk_slug" to model.sk_slug, "name" to model.name)
                    findNavController().navigate(
                        R.id.action_matchDetailsFragment_to_playerDetailsFragment, bundle
                    )
                }
            }
        }
    }

    private fun setUpTeam2Adapter() {
        infoTeam2Adapter = InfoTeam2Adapter()
        val recyclerViewState = binding.recyclerViewTeam2.layoutManager?.onSaveInstanceState()
        binding.recyclerViewTeam2.layoutManager = LinearLayoutManager(requireContext())
        infoTeam2Adapter.addAll(squadX[1].players, true)
        binding.recyclerViewTeam2.adapter = infoTeam2Adapter
        infoTeam2Adapter.notifyDataSetChanged()
        binding.recyclerViewTeam2.layoutManager?.onRestoreInstanceState(recyclerViewState)

        infoTeam2Adapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.clItem -> {
                    val bundle = bundleOf("sk_slug" to model.sk_slug, "name" to model.name)
                    findNavController().navigate(
                        R.id.action_matchDetailsFragment_to_playerDetailsFragment, bundle
                    )
                }
            }
        }
    }

    private fun setUpBenchTeam1Adapter() {
        benchTeam1Adapter = BenchTeam1Adapter()
        val recyclerViewState = binding.recyclerViewBenchTeam1.layoutManager?.onSaveInstanceState()
        binding.recyclerViewBenchTeam1.layoutManager = LinearLayoutManager(requireContext())
        benchTeam1Adapter.addAll(squadX[0].bench_players, true)
        binding.recyclerViewBenchTeam1.adapter = benchTeam1Adapter
        benchTeam1Adapter.notifyDataSetChanged()
        binding.recyclerViewBenchTeam1.layoutManager?.onRestoreInstanceState(recyclerViewState)

        benchTeam1Adapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.clItem -> {
                    val bundle = bundleOf("sk_slug" to model.sk_slug, "name" to model.name)
                    findNavController().navigate(
                        R.id.action_matchDetailsFragment_to_playerDetailsFragment, bundle
                    )
                }
            }
        }
    }

    private fun setUpBenchTeam2Adapter() {
        benchTeam2Adapter = BenchTeam2Adapter()
        val recyclerViewState = binding.recyclerViewBenchTeam2.layoutManager?.onSaveInstanceState()
        binding.recyclerViewBenchTeam2.layoutManager = LinearLayoutManager(requireContext())
        benchTeam2Adapter.addAll(squadX[1].bench_players, true)
        binding.recyclerViewBenchTeam2.adapter = benchTeam2Adapter
        benchTeam2Adapter.notifyDataSetChanged()
        binding.recyclerViewBenchTeam2.layoutManager?.onRestoreInstanceState(recyclerViewState)

        benchTeam2Adapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.clItem -> {
                    val bundle = bundleOf("sk_slug" to model.sk_slug, "name" to model.name)
                    findNavController().navigate(
                        R.id.action_matchDetailsFragment_to_playerDetailsFragment, bundle
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertUnixTimestamp(timestamp: Long): String {
        val instant = Instant.ofEpochSecond(timestamp)
        val zoneIdIST = ZoneId.of("Asia/Kolkata") // Use the time zone for IST
        val zonedDateTime = ZonedDateTime.ofInstant(instant, zoneIdIST)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM, EEE hh:mm a z", Locale.getDefault())
        return formatter.format(zonedDateTime)
    }


    companion object {
        @JvmStatic
        fun newInstance(squad: ArrayList<Squad>) = InfoFragment().apply {
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