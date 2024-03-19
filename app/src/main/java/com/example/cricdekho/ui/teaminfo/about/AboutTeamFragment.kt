package com.example.cricdekho.ui.teaminfo.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getTeamInfo.ResponseTeamInfo
import com.example.cricdekho.databinding.FragmentAboutTeamBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.teaminfo.TeamInfoViewModel

class AboutTeamFragment : BaseFragment() {
    private lateinit var binding: FragmentAboutTeamBinding
    private val teamInfoViewModel: TeamInfoViewModel by viewModels()
    private var responseTeamInfo: ArrayList<ResponseTeamInfo>? = ArrayList()
    private lateinit var tournamentSlug: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tournamentSlug = it.getString("tournament_slug").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        progressBarListener.showProgressBar()
        getTeamInfo()
    }

    private fun getTeamInfo() {
        teamInfoViewModel.getTeamInfo(tournamentSlug)
        teamInfoViewModel.teamInfo.observe(viewLifecycleOwner, Observer {
            responseTeamInfo?.clear()
            responseTeamInfo?.addAll(listOf(it))
            binding.clMain.isVisible = true
            setData()
            progressBarListener.hideProgressBar()
        })
    }

    private fun setData() {
        binding.apply {
            Glide.with(requireContext()).load(responseTeamInfo?.get(0)?.data?.img)
                .placeholder(R.drawable.ic_team_default).into(ivTeam)
            txtFullName.text = responseTeamInfo?.get(0)?.data?.name
            txtNickName.text = responseTeamInfo?.get(0)?.data?.teamInfo?.nickname
            txtFounded.text = responseTeamInfo?.get(0)?.data?.teamInfo?.founded
            txtGround.text = responseTeamInfo?.get(0)?.data?.teamInfo?.ground
            txtTeamOwner.text = responseTeamInfo?.get(0)?.data?.teamInfo?.ownerS
            txtProminentPLayers.text = ""
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tournamentSlug: String) =
            AboutTeamFragment().apply {
                arguments = Bundle().apply {
                    putString("tournament_slug", tournamentSlug)
                }
            }
    }
}