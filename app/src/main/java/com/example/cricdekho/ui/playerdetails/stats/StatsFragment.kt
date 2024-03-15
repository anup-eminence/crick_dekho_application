package com.example.cricdekho.ui.playerdetails.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.cricdekho.data.model.getPlayerStats.ResponseStats
import com.example.cricdekho.databinding.FragmentStatsBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.playerdetails.PlayerInfoViewModel

class StatsFragment : BaseFragment() {
    private lateinit var binding: FragmentStatsBinding
    private val playerInfoViewModel: PlayerInfoViewModel by viewModels()
    private var responseStats: ArrayList<ResponseStats>? = ArrayList()
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
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        progressBarListener.showProgressBar()
        getPlayerStats()
    }

    private fun getPlayerStats() {
        playerInfoViewModel.getPlayerStats(playerSlug)

        playerInfoViewModel.playerStats.observe(viewLifecycleOwner, Observer {
            responseStats?.clear()
            responseStats?.addAll(listOf(it))
            progressBarListener.hideProgressBar()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(playerSlug: String) =
            StatsFragment().apply {
                arguments = Bundle().apply {
                    putString("sk_slug", playerSlug)
                }
            }
    }
}