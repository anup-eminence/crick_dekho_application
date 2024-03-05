package com.example.cricdekho.ui.playerdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getPlayerInfo.ResponsePlayerInfo
import com.example.cricdekho.databinding.FragmentOverviewBinding
import com.example.cricdekho.ui.activity.HomeActivity
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.playerdetails.adapter.BattingStatsAdapter
import com.example.cricdekho.ui.playerdetails.adapter.BowlingStatsAdapter
import com.example.cricdekho.ui.playerdetails.adapter.MostRecentMatchesAdapter

class OverviewFragment : BaseFragment() {
    private lateinit var binding: FragmentOverviewBinding
    private lateinit var mostRecentMatchesAdapter: MostRecentMatchesAdapter
    private lateinit var battingStatsAdapter: BattingStatsAdapter
    private lateinit var bowlingStatsAdapter: BowlingStatsAdapter
    private val playerInfoViewModel: PlayerInfoViewModel by viewModels()
    private var responsePlayerInfo: ArrayList<ResponsePlayerInfo>? = ArrayList()
    private lateinit var playerSlug: String
    private lateinit var playerName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            playerSlug = it.getString("sk_slug").toString()
            playerName = it.getString("name").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        progressBarListener.showProgressBar()
        getPlayerInfo()
    }

    private fun getPlayerInfo() {
        playerInfoViewModel.getPlayerInfo(playerSlug)

        playerInfoViewModel.playerInfo.observe(viewLifecycleOwner, Observer {
            responsePlayerInfo?.clear()
            responsePlayerInfo?.addAll(listOf(it))
            setToolBar()
            setData()
            setMostRecentMatchesAdapter()
            setBattingStatsAdapter()
            setBowlingStatsAdapter()
            progressBarListener.hideProgressBar()
        })
    }

    private fun setToolBar() {
        val yourActivity = activity as? HomeActivity
        yourActivity?.showToolBarMethod(
            title = playerName,
            menu = false,
            logo = false,
            search = false,
            setting = false,
            back = true,
            share = false
        )
    }

    private fun setData() {
        binding.apply {
            Glide.with(requireContext()).load(responsePlayerInfo?.get(0)?.data?.img)
                .error(R.drawable.ic_player).into(ivPlayer)
            tvTeam.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.nationality
            tvPlayerRole.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.role
            txtFullName.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.fullName
            txtDOB.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.dateOfBirth
            txtAge.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.age
            txtNation.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.nationality
            txtBirthPlace.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.birthPlace
            txtHeight.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.height
            txtCurrentTeam.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.currentTeamS
            txtRole.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.role
            txtBattingStyle.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.battingStyle
            txtBowlingStyle.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.bowlingStyle
            txtDebut.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.debut
            txtJersey.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.jerseyNo
            txtFamily.text = responsePlayerInfo?.get(0)?.data?.personalInfo?.family
        }
    }

    private fun setMostRecentMatchesAdapter() {
        mostRecentMatchesAdapter = MostRecentMatchesAdapter()
        binding.recyclerViewRecentMatch.layoutManager = LinearLayoutManager(requireContext())
        mostRecentMatchesAdapter.addAll(
            responsePlayerInfo?.get(0)?.data?.tables?.get(0)?.data,
            true
        )
        binding.recyclerViewRecentMatch.adapter = mostRecentMatchesAdapter
        mostRecentMatchesAdapter.notifyDataSetChanged()
    }

    private fun setBattingStatsAdapter() {
        battingStatsAdapter = BattingStatsAdapter()
        binding.recyclerViewBattingStats.layoutManager = LinearLayoutManager(requireContext())
        battingStatsAdapter.addAll(
            responsePlayerInfo?.get(0)?.data?.tables?.get(1)?.data, true
        )
        binding.recyclerViewBattingStats.adapter = battingStatsAdapter
        battingStatsAdapter.notifyDataSetChanged()
    }

    private fun setBowlingStatsAdapter() {
        bowlingStatsAdapter = BowlingStatsAdapter()
        binding.recyclerViewBowlingStats.layoutManager = LinearLayoutManager(requireContext())
        bowlingStatsAdapter.addAll(
            responsePlayerInfo?.get(0)?.data?.tables?.get(2)?.data, true
        )
        binding.recyclerViewBowlingStats.adapter = bowlingStatsAdapter
        bowlingStatsAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            OverviewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}