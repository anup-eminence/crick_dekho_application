package com.example.cricdekho.ui.fantasy

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
import com.example.cricdekho.data.model.getUpcomingMatches.ResponseUpcomingMatch
import com.example.cricdekho.data.model.getUpcomingMatches.Tab
import com.example.cricdekho.databinding.FragmentFantasyBinding
import com.example.cricdekho.ui.fantasy.adapter.FantasyMatchesAdapter
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.match.MatchesViewModel
import com.example.cricdekho.ui.match.upcoming.TabsAdapter

class FantasyFragment : BaseFragment() {
    private lateinit var binding: FragmentFantasyBinding
    private lateinit var fantasyMatchesAdapter: FantasyMatchesAdapter
    private lateinit var tabsAdapter: TabsAdapter
    private val matchViewModel: MatchesViewModel by viewModels()
    private val responseUpcomingMatch: MutableList<ResponseUpcomingMatch> = mutableListOf()
    private val tabsList: MutableList<Tab> = mutableListOf()
    private val tournamentSlug: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFantasyBinding.inflate(inflater, container, false)
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

        matchViewModel.dataUpcomingTabs.observe(viewLifecycleOwner, Observer { tabs ->
            tabsList.clear()
            tabsList.addAll(tabs)
            setUpTabAdapter()
        })

        matchViewModel.dataUpcomingMatch.observe(viewLifecycleOwner, Observer { matches ->
            responseUpcomingMatch.clear()
            responseUpcomingMatch.addAll(matches)
            setUpMatchAdapter()
            progressBarListener.hideProgressBar()
        })
    }

    private fun setUpTabAdapter() {
        tabsAdapter = TabsAdapter()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        tabsAdapter.addAll(tabsList, false)
        binding.recyclerView.adapter = tabsAdapter
        tabsAdapter.notifyDataSetChanged()

        tabsAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.tvText -> {
                    fetchUpcomingMatches(model.slug)
                }
            }
        }
    }

    private fun setUpMatchAdapter() {
        fantasyMatchesAdapter = FantasyMatchesAdapter()
        binding.recyclerViewMatches.layoutManager = LinearLayoutManager(requireContext())
        fantasyMatchesAdapter.addAll(responseUpcomingMatch, true)
        binding.recyclerViewMatches.adapter = fantasyMatchesAdapter
        fantasyMatchesAdapter.notifyDataSetChanged()

        fantasyMatchesAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.clItem -> {
                    val bundle = bundleOf("id" to model.id)
                    findNavController().navigate(
                        R.id.action_fantasyFragment_to_matchDetailsFragment, bundle
                    )
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FantasyFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}