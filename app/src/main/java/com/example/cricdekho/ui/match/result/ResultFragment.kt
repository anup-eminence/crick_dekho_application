package com.example.cricdekho.ui.match.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getResultMatches.ResponseResultMatch
import com.example.cricdekho.data.model.getResultMatches.Tab
import com.example.cricdekho.databinding.FragmentResultBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.match.MatchesViewModel

class ResultFragment : BaseFragment() {
    private lateinit var binding: FragmentResultBinding
    private lateinit var resultAdapter: ResultAdapter
    private lateinit var tabsAdapter: TabsAdapter
    private val matchViewModel: MatchesViewModel by viewModels()
    private val responseResultMatch: MutableList<ResponseResultMatch> = mutableListOf()
    private val tabsList: MutableList<Tab> = mutableListOf()
    private val tournamentSlug: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        progressBarListener.showProgressBar()
        fetchResultMatches(tournamentSlug)
    }

    private fun fetchResultMatches(tournamentSlug: String) {
        matchViewModel.getResultMatches(tournamentSlug)

        matchViewModel.dataResultMatch.observe(viewLifecycleOwner, Observer { matches ->
            responseResultMatch.clear()
            responseResultMatch.addAll(matches)
            setUpResultMatchAdapter()
            progressBarListener.hideProgressBar()
        })
        matchViewModel.dataResultTab.observe(viewLifecycleOwner, Observer { tabs ->
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

        tabsAdapter.addAll(tabsList, true)
        binding.recyclerViewTabs.adapter = tabsAdapter
        tabsAdapter.notifyDataSetChanged()
        binding.recyclerViewTabs.layoutManager?.onRestoreInstanceState(recyclerViewState)

        tabsAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.tvText -> {
                    fetchResultMatches(model.slug)
                }
            }
        }
    }

    private fun setUpResultMatchAdapter() {
        resultAdapter = ResultAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        resultAdapter.addAll(responseResultMatch, true)
        binding.recyclerView.adapter = resultAdapter
        resultAdapter.notifyDataSetChanged()

        resultAdapter.setRecyclerViewItemClick { itemView, model ->
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
        fun newInstance() =
            ResultFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}


/*
**** CALL API
try {
    val resultMatchList = mutableListOf<ResponseResultMatch>()
    val response = matchesRepository.getResultMatches(tournamentSlug)
    val responseBody = response.body()?.string()
    val jsonObject = responseBody?.let { JSONObject(it) }

    Log.e("json_object", jsonObject.toString())

    val gson = Gson()

    val matchesObjectTab = jsonObject?.getJSONObject("data")?.getJSONArray("tabs")
    val resultMatchesTabs: MutableList<com.example.cricdekho.data.model.getResultMatches.Tab> =
        gson.fromJson(
            matchesObjectTab.toString(),
            object :
                TypeToken<MutableList<Tab>>() {}.type
        )

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

                val matches: ArrayList<ResponseResultMatch> = gson.fromJson(
                    matchesArray.toString(),
                    object : TypeToken<List<ResponseResultMatch>>() {}.type
                )
                resultMatchList.addAll(matches)
            }
        }
        _dataResultMatch.postValue(resultMatchList)
        _dataResultTabs.postValue(resultMatchesTabs)
    }
} catch (e: Exception) {
    Log.e("Exception_Result", e.message.toString())
}*/
