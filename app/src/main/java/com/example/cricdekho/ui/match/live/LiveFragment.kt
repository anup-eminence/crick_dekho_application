package com.example.cricdekho.ui.match.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getLiveMatches.CricketMatch
import com.example.cricdekho.data.model.getUpcomingMatches.Tab
import com.example.cricdekho.data.remote.SocketManager
import com.example.cricdekho.databinding.FragmentLiveBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.match.MatchesViewModel
import com.example.cricdekho.ui.match.upcoming.TabsAdapter

class LiveFragment : BaseFragment() {
    private lateinit var binding: FragmentLiveBinding
    private lateinit var liveAdapter: LiveAdapter
    private lateinit var tabsAdapter: TabsAdapter
    private lateinit var matchesViewModel: MatchesViewModel
    private val responseLiveMatch = ArrayList<CricketMatch>()
    private val tabsList: MutableList<Tab> = mutableListOf()
    private var tournamentSlug: String? = null
    private val filterData = ArrayList<CricketMatch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    override fun onResume() {
        super.onResume()
        matchesViewModel.connectSocket()
    }

    override fun onPause() {
        super.onPause()
        matchesViewModel.disConnectSocket()
    }
    private fun initView() {
        progressBarListener.showProgressBar()
        matchesViewModel = ViewModelProvider(this)[MatchesViewModel::class.java]
        matchesViewModel.emitSocketEvent("LiveMatches")

        if (tournamentSlug?.isNotEmpty() == true) {
            tournamentSlug?.let { filterData(it) }
        } else {
            fetchLiveMatches()
        }
    }

    private fun fetchLiveMatches() {
        matchesViewModel.dataLiveTabs.observe(viewLifecycleOwner, Observer { tabs ->
            tabsList.clear()
            tabsList.addAll(tabs)
            setUpTabAdapter()
        })

        matchesViewModel.observeLiveData.observe(viewLifecycleOwner) { data ->
            if (data is List<*>) {
                responseLiveMatch.clear()
                responseLiveMatch.addAll(data.filterIsInstance<CricketMatch>())
                setUpLiveMatchAdapter()
                progressBarListener.hideProgressBar()
            }
        }
    }

    private fun setUpTabAdapter() {
        tabsAdapter = TabsAdapter()
        val recyclerViewState = binding.recyclerViewTabs.layoutManager?.onSaveInstanceState()
        binding.recyclerViewTabs.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        tabsAdapter.addAll(tabsList, false)
        binding.recyclerViewTabs.adapter = tabsAdapter
        tabsAdapter.notifyDataSetChanged()
        binding.recyclerViewTabs.layoutManager?.onRestoreInstanceState(recyclerViewState)

        tabsAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.tvText -> {
                    filterData(model.slug)
                }
            }
        }
    }

    private fun setUpLiveMatchAdapter() {
        liveAdapter = LiveAdapter()
        val recyclerViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        liveAdapter.addAll(responseLiveMatch, true)
        binding.recyclerView.adapter = liveAdapter
        liveAdapter.notifyDataSetChanged()
        binding.recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)

        liveAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.clItem -> {
                    val bundle = bundleOf("id" to model.id)
                    findNavController().navigate(
                        R.id.action_matchesFragment_to_matchDetailsFragment, bundle
                    )
                }
            }
        }
    }

    private fun filterData(tournamentSlug: String) {
        filterData.clear()
        for (i in 0 until responseLiveMatch.size) {
            if (tournamentSlug == responseLiveMatch[i].event_slug) {
                filterData.add(responseLiveMatch[i])
            }
        }
        responseLiveMatch.clear()
        responseLiveMatch.addAll(filterData)
        setUpLiveMatchAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketManager.disconnect()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LiveFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}