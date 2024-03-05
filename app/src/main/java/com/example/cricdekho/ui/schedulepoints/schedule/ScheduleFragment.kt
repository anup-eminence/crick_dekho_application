package com.example.cricdekho.ui.schedulepoints.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getUpcomingMatches.ResponseUpcomingMatch
import com.example.cricdekho.databinding.FragmentScheduleBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.match.MatchesViewModel
import com.example.cricdekho.ui.match.upcoming.UpcomingAdapter

class ScheduleFragment : BaseFragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var upcomingAdapter: UpcomingAdapter
    private val matchViewModel: MatchesViewModel by viewModels()
    private val responseUpcomingMatch: MutableList<ResponseUpcomingMatch> = mutableListOf()
    private lateinit var tournamentSlug: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tournamentSlug = it.getString("tournament_slug").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        progressBarListener.showProgressBar()
        fetchUpcomingMatches(tournamentSlug)
    }

    private fun fetchUpcomingMatches(tournamentSlug: String) {
        matchViewModel.getUpcomingMatches(tournamentSlug)

        matchViewModel.dataUpcomingMatch.observe(viewLifecycleOwner, Observer { matches ->
            responseUpcomingMatch.clear()
            responseUpcomingMatch.addAll(matches)
            setUpAdapter()
            progressBarListener.hideProgressBar()
        })
    }

    private fun setUpAdapter() {
        upcomingAdapter = UpcomingAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        upcomingAdapter.addAll(responseUpcomingMatch, true)
        binding.recyclerView.adapter = upcomingAdapter
        upcomingAdapter.notifyDataSetChanged()

        upcomingAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.clItem -> {
                    findNavController().navigate(R.id.action_matchesFragment_to_matchDetailsFragment)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tournamentSlug: String) = ScheduleFragment().apply {
            arguments = Bundle().apply {
                putString("tournament_slug", tournamentSlug)
            }
        }
    }
}