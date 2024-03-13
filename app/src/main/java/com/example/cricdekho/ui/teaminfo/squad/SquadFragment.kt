package com.example.cricdekho.ui.teaminfo.squad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getTeamInfo.PlayersItem
import com.example.cricdekho.data.model.getTeamInfo.ResponseTeamInfo
import com.example.cricdekho.databinding.FragmentSquadBinding
import com.example.cricdekho.ui.InfoAdapter
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.teaminfo.TeamInfoViewModel

class SquadFragment : BaseFragment(), SquadAdapter.SquadAdapterClickListener {
    private lateinit var binding: FragmentSquadBinding
    private lateinit var squadAdapter: SquadAdapter
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
        binding = FragmentSquadBinding.inflate(inflater, container, false)
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
            setUpAdapter()
            progressBarListener.hideProgressBar()
        })
    }

    private fun setUpAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        squadAdapter = responseTeamInfo!![0].data?.squad!![0]?.let { SquadAdapter(it.players) }!!
        squadAdapter.setSquadAdapterListener(this@SquadFragment)
        binding.recyclerView.adapter = squadAdapter
    }

    override fun onAdapterItemClick(item: PlayersItem) {
        val bundle = bundleOf("sk_slug" to item.slug, "name" to item.name)
        findNavController().navigate(
            R.id.action_teamInfoFragment_to_playerDetailsFragment, bundle
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(tournamentSlug: String) =
            SquadFragment().apply {
                arguments = Bundle().apply {
                    putString("tournament_slug", tournamentSlug)
                }
            }
    }
}