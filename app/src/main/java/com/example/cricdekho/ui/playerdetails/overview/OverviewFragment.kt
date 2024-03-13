package com.example.cricdekho.ui.playerdetails.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getPlayerInfo.ResponsePlayerInfo
import com.example.cricdekho.databinding.FragmentOverviewBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.playerdetails.PlayerInfoViewModel
import com.example.cricdekho.ui.playerdetails.overview.adapter.BattingStatsAdapter
import com.example.cricdekho.ui.playerdetails.overview.adapter.BowlingStatsAdapter
import com.example.cricdekho.ui.playerdetails.overview.adapter.MostRecentMatchesAdapter

class OverviewFragment : BaseFragment() {
    private lateinit var binding: FragmentOverviewBinding
    private lateinit var mostRecentMatchesAdapter: MostRecentMatchesAdapter
    private lateinit var battingStatsAdapter: BattingStatsAdapter
    private lateinit var bowlingStatsAdapter: BowlingStatsAdapter
    private val playerInfoViewModel: PlayerInfoViewModel by viewModels()
    private var responsePlayerInfo: ArrayList<ResponsePlayerInfo>? = ArrayList()
    private lateinit var playerSlug: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            playerSlug = it.getString("sk_slug").toString()
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
        setOnClickListener()
    }

    private fun initView() {
        progressBarListener.showProgressBar()
        getPlayerInfo()
    }

    private fun setOnClickListener() {
        binding.apply {
            viewAll1.setOnClickListener {  }
            viewAll2.setOnClickListener {  }
            viewAll3.setOnClickListener {  }
        }
    }

    private fun getPlayerInfo() {
        playerInfoViewModel.getPlayerInfo(playerSlug)

        playerInfoViewModel.playerInfo.observe(viewLifecycleOwner, Observer {
            responsePlayerInfo?.clear()
            responsePlayerInfo?.addAll(listOf(it))
            binding.clMain.isVisible = true
            setData()
            setMostRecentMatchesAdapter()
            setBattingStatsAdapter()
            setBowlingStatsAdapter()
            progressBarListener.hideProgressBar()
        })
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
        if (responsePlayerInfo?.get(0)?.data?.tables?.get(0)?.data?.isNotEmpty() == true) {
            binding.apply {
                tvMostRecentMatches.isVisible = true
                scrollView1.isVisible = true
                view1.isVisible = true
                viewAll1.isVisible = true
            }
        }
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
        if (responsePlayerInfo?.get(0)?.data?.tables?.get(1)?.data?.isNotEmpty() == true) {
            binding.apply {
                tvBattingStats.isVisible = true
                scrollView2.isVisible = true
                view2.isVisible = true
                viewAll2.isVisible = true
            }
        }
        battingStatsAdapter = BattingStatsAdapter()
        binding.recyclerViewBattingStats.layoutManager = LinearLayoutManager(requireContext())
        battingStatsAdapter.addAll(
            responsePlayerInfo?.get(0)?.data?.tables?.get(1)?.data, true
        )
        binding.recyclerViewBattingStats.adapter = battingStatsAdapter
        battingStatsAdapter.notifyDataSetChanged()
    }

    private fun setBowlingStatsAdapter() {
        if (responsePlayerInfo?.get(0)?.data?.tables?.get(2)?.data?.isNotEmpty() == true) {
            binding.apply {
                tvBowlingStats.isVisible = true
                scrollView3.isVisible = true
                view3.isVisible = true
                viewAll3.isVisible = true
            }
        }
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
        fun newInstance(playerSlug: String) = OverviewFragment().apply {
            arguments = Bundle().apply {
                putString("sk_slug", playerSlug)
            }
        }
    }
}