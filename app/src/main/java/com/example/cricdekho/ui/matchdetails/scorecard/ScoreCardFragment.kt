package com.example.cricdekho.ui.matchdetails.scorecard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.InningTab
import com.example.cricdekho.data.model.getMatchDetails.Innings
import com.example.cricdekho.data.model.getMatchDetails.Squad
import com.example.cricdekho.databinding.FragmentScoreCardBinding
import com.example.cricdekho.ui.matchdetails.MatchDetailsFragment

class ScoreCardFragment : Fragment() {
    private lateinit var binding: FragmentScoreCardBinding
    private lateinit var scoreCardAdapter: ScoreCardAdapter
    private lateinit var totalScoreAdapter: TotalScoreAdapter
    private lateinit var bowlersAdapter: BowlersAdapter
    private lateinit var wicketsAdapter: WicketsAdapter
    private var squad = ArrayList<Squad>()
    private var innings = ArrayList<Innings>()
    private val inningTab = ArrayList<InningTab>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* arguments?.let {
            squad = it.getParcelableArrayList("squad")!!
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentScoreCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MatchDetailsFragment.matchDetailsData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) {
                squad = it
                initView()
            }
        }
    }

    private fun initView() {
        if (squad[0].innings.isNotEmpty()) {
            innings.clear()
            innings.addAll(squad[0].innings)
            for (i in innings) {
                inningTab.add(InningTab(i.innings_no, i.name))
            }
            setUpTabAdapter()
            setUpScoreAdapter(0)
            setUpBowlerAdapter(0)
            setUpWicketAdapter(0)
        } else {
            binding.apply {
                val viewsToHide = arrayOf(clTotalScore, clBowlers, clWickets)
                viewsToHide.forEach { it.visibility = View.GONE }
            }
        }
    }

    private fun setUpTabAdapter() {
        scoreCardAdapter = ScoreCardAdapter()
        val recyclerViewState = binding.recyclerViewTabs.layoutManager?.onSaveInstanceState()
        binding.recyclerViewTabs.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        scoreCardAdapter.addAll(inningTab, true)
        binding.recyclerViewTabs.adapter = scoreCardAdapter
        scoreCardAdapter.notifyDataSetChanged()
        binding.recyclerViewTabs.layoutManager?.onRestoreInstanceState(recyclerViewState)

        scoreCardAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.tvText -> {
                    setUpScoreAdapter(model.inningsNo - 1)
                    setUpBowlerAdapter(model.inningsNo - 1)
                    setUpWicketAdapter(model.inningsNo - 1)
                }
            }
        }
    }

    private fun setUpScoreAdapter(inningPos: Int) {
        binding.apply {
            txtTotalScore.text =
                "${innings[inningPos].runs}/${innings[inningPos].wickets} (${innings[inningPos].overs})"
            tvExtrasTxt1.text = innings[inningPos].extras
            tvExtrasTxt2.text =
                "(b ${innings[inningPos].bye}, lb ${innings[inningPos].legbye}, nb ${innings[inningPos].noball}, w ${innings[inningPos].wide})"
        }

        totalScoreAdapter = TotalScoreAdapter()
        val recyclerViewState = binding.recyclerViewTotalScore.layoutManager?.onSaveInstanceState()
        binding.recyclerViewTotalScore.layoutManager = LinearLayoutManager(requireContext())
        totalScoreAdapter.addAll(innings[inningPos].batting, true)
        binding.recyclerViewTotalScore.adapter = totalScoreAdapter
        totalScoreAdapter.notifyDataSetChanged()
        binding.recyclerViewTotalScore.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    private fun setUpBowlerAdapter(inningPos: Int) {
        bowlersAdapter = BowlersAdapter()
        val recyclerViewState = binding.recyclerViewBowlers.layoutManager?.onSaveInstanceState()
        binding.recyclerViewBowlers.layoutManager = LinearLayoutManager(requireContext())
        bowlersAdapter.addAll(innings[inningPos].bowling, true)
        binding.recyclerViewBowlers.adapter = bowlersAdapter
        bowlersAdapter.notifyDataSetChanged()
        binding.recyclerViewBowlers.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    private fun setUpWicketAdapter(inningPos: Int) {
        wicketsAdapter = WicketsAdapter()
        val recyclerViewState = binding.recyclerViewWickets.layoutManager?.onSaveInstanceState()
        binding.recyclerViewWickets.layoutManager = LinearLayoutManager(requireContext())
        wicketsAdapter.addAll(innings[inningPos].fall_of_wickets_array, true)
        binding.recyclerViewWickets.adapter = wicketsAdapter
        wicketsAdapter.notifyDataSetChanged()
        binding.recyclerViewWickets.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    companion object {
        @JvmStatic
        fun newInstance(squad: ArrayList<Squad>) = ScoreCardFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("squad", ArrayList(squad))
            }
        }
    }
}