package com.example.cricdekho.ui.match.upcoming

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
import com.example.cricdekho.databinding.FragmentUpcomingBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.match.MatchesViewModel

class UpcomingFragment : BaseFragment() {
    private lateinit var binding: FragmentUpcomingBinding
    private lateinit var upcomingAdapter: UpcomingAdapter
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
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        matchViewModel.dataUpcomingTabs.observe(viewLifecycleOwner, Observer { tabs ->
            tabsList.clear()
            tabsList.addAll(tabs)
            setUpTabAdapter()
        })
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
                    fetchUpcomingMatches(model.slug)
                }
            }
        }
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
                    val bundle = bundleOf("id" to model.id, "status" to model.status)
                    findNavController().navigate(
                        R.id.action_matchesFragment_to_matchDetailsFragment, bundle)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = UpcomingFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}


/*
**** CALL API
 try {
                val matchList = mutableListOf<ResponseUpcomingMatch>()
                val response = matchesRepository.getUpcomingMatches(tournamentSlug)
                val responseBody = response.body()?.string()
//                val jsonParser = Gson()
//                val jsonObject: JsonObject = jsonParser.fromJson(responseBody, JsonObject::class.java)
                val jsonObject = responseBody?.let { JSONObject(it) }

                Log.e("json_object", jsonObject.toString())
//                val matchesObject = jsonObject?.getJSONObject("data")?.getJSONObject("matches")?.getJSONObject("16/02/2024")?.getJSONArray("KCC T20 Challenge Cup")

                val gson = Gson()
//                val matches: List<ResponseUpcomingMatch> = gson.fromJson(matchesObject.toString(),
//                    object : TypeToken<List<ResponseUpcomingMatch>>() {}.type
//                )

                val matchesObjectTab = jsonObject?.getJSONObject("data")?.getJSONArray("tabs")
                val matchesTabs: MutableList<Tab> = gson.fromJson(
                    matchesObjectTab.toString(),
                    object : TypeToken<MutableList<Tab>>() {}.type
                )

//                matchTab.addAll(matchesTabs)
//                Log.e("matches", matches.toString())
//                Log.e("tabs", matchesTabs.toString())
//                Log.d("ParsedJsonObject", jsonObject.toString())


                val dateKeys = jsonObject?.getJSONObject("data")?.getJSONObject("matches")?.keys()
                dateKeys?.let { keys ->
                    while (keys.hasNext()) {
                        val dateKey = keys.next()
                        val matchesObject =
                            jsonObject.getJSONObject("data").getJSONObject("matches")
                                .getJSONObject(dateKey.toString())

                        val tournamentKeys = matchesObject.keys()
                        while (tournamentKeys.hasNext()) {
                            val tournamentKey = tournamentKeys.next().toString()
                            val matchesArray = matchesObject.getJSONArray(tournamentKey)


                            val matches: ArrayList<ResponseUpcomingMatch> = gson.fromJson(
                                matchesArray.toString(),
                                object : TypeToken<List<ResponseUpcomingMatch>>() {}.type
                            )
                            matchList.addAll(matches)

//                            Log.d("DynamicKeys", "Date: $dateKey, Tournament: $tournamentKey, Matches: $matchesArray")
                        }
                    }
                    Log.e("matchList", matchList.toString())
                    Log.e("matchTabs", matchesTabs.toString())
                    _dataUpcomingMatch.postValue(matchList)
                    _dataUpcomingTabs.postValue(matchesTabs)

                }
            } catch (e: Exception) {
                Log.e("Exception_Upcoming", e.message.toString())
            }*/