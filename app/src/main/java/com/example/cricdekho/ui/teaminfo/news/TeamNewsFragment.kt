package com.example.cricdekho.ui.teaminfo.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getTeamNews.NewsItem
import com.example.cricdekho.data.model.getTeamNews.ResponseTeamNews
import com.example.cricdekho.databinding.FragmentTeamNewsBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.playerdetails.PlayerInfoViewModel
import com.example.cricdekho.ui.teaminfo.TeamInfoViewModel

class TeamNewsFragment : BaseFragment(), TeamNewsAdapter.NewsAdapterClickListener {
    private lateinit var binding: FragmentTeamNewsBinding
    private val teamInfoViewModel: TeamInfoViewModel by viewModels()
    private val playerInfoViewModel: PlayerInfoViewModel by viewModels()
    private lateinit var teamNewsAdapter: TeamNewsAdapter
    private var responseTeamNews: ArrayList<ResponseTeamNews>? = ArrayList()
    private lateinit var tournamentSlug: String
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tournamentSlug = it.getString("tournament_slug").toString()
            type = it.getString("type").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        progressBarListener.showProgressBar()
        if (type == "team") {
            getTeamNews()
        } else {
            getPlayerNews()
        }
    }

    private fun getTeamNews() {
        teamInfoViewModel.getTeamNews(tournamentSlug)
        teamInfoViewModel.teamNews.observe(viewLifecycleOwner, Observer {
            responseTeamNews?.clear()
            responseTeamNews?.addAll(listOf(it))
            progressBarListener.hideProgressBar()
            setUpNewsAdapter()
        })
    }

    private fun getPlayerNews() {
        playerInfoViewModel.getPlayerNews(tournamentSlug)
        playerInfoViewModel.playerNews.observe(viewLifecycleOwner, Observer {
            responseTeamNews?.clear()
            responseTeamNews?.addAll(listOf(it))
            progressBarListener.hideProgressBar()
            setUpNewsAdapter()
        })
    }

    private fun setUpNewsAdapter() {
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())
        teamNewsAdapter = TeamNewsAdapter(responseTeamNews?.get(0)?.data?.news)
        teamNewsAdapter.setNewsAdapterListener(this@TeamNewsFragment)
        binding.recyclerViewNews.adapter = teamNewsAdapter
    }

    override fun onAdapterItemClick(item: NewsItem) {
        val bundle = bundleOf("link" to item.link)
        if (type == "team") {
            findNavController().navigate(
                R.id.action_teamInfoFragment_to_newsDetailFragment, bundle
            )
        } else {
            findNavController().navigate(
                R.id.action_playerDetailsFragment_to_newsDetailFragment, bundle
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tournamentSlug: String, type: String) = TeamNewsFragment().apply {
            arguments = Bundle().apply {
                putString("tournament_slug", tournamentSlug)
                putString("type", type)
            }
        }
    }
}