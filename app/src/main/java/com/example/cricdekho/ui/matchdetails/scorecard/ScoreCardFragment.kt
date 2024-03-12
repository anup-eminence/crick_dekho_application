package com.example.cricdekho.ui.matchdetails.scorecard

import android.annotation.SuppressLint
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
import com.example.cricdekho.ui.matchdetails.scorecard.adapter.BowlersNewAdapter
import com.example.cricdekho.ui.matchdetails.scorecard.adapter.ScoreCardNewAdapter
import com.example.cricdekho.ui.matchdetails.scorecard.adapter.TotalScoreNewAdapter
import com.example.cricdekho.ui.matchdetails.scorecard.adapter.WicketsNewAdapter

class ScoreCardFragment : Fragment(), ScoreCardNewAdapter.ScoreCardListener {
    private lateinit var binding: FragmentScoreCardBinding
    private var squad = ArrayList<Squad>()
    private var innings = ArrayList<Innings>()
    private val inningTab = ArrayList<InningTab>()

    private var updateInningTab = true
    private var inningsTabPos = 0

    // new adapter with diffUtils
    private lateinit var bowlersNewAdapter: BowlersNewAdapter
    private lateinit var totalScoreNewAdapter: TotalScoreNewAdapter
    private lateinit var wicketsNewAdapter: WicketsNewAdapter
    private lateinit var scoreCardNewAdapter: ScoreCardNewAdapter

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
        setUpAdapters()
        MatchDetailsFragment.matchDetailsData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) {
                squad = it
                initView()
                updateDataInRecyclerView(it,inningsTabPos)

            }
        }
    }

    private fun initView() {
        if (squad[0].innings.isNotEmpty()) {
            innings.clear()
            inningTab.clear()
            innings.addAll(squad[0].innings)
            for (i in innings) {
                inningTab.add(InningTab(i.innings_no, i.name))
            }
            if (updateInningTab){
                scoreCardNewAdapter.setData(inningTab)
                updateInningTab = false
            }
            if (scoreCardNewAdapter.oldList.isNotEmpty() && scoreCardNewAdapter.oldList[0].name != inningTab[0].name){
                scoreCardNewAdapter.setData(inningTab)
            }
        } else {
            binding.apply {
                val viewsToHide = arrayOf(clTotalScore, clBowlers, clWickets)
                viewsToHide.forEach { it.visibility = View.GONE }
            }
        }
    }


    private fun setUpAdapters() {
        bowlersNewAdapter = BowlersNewAdapter()
        totalScoreNewAdapter = TotalScoreNewAdapter()
        wicketsNewAdapter = WicketsNewAdapter()
        scoreCardNewAdapter = ScoreCardNewAdapter()
        scoreCardNewAdapter.setScoreCardListener(this@ScoreCardFragment)


        binding.recyclerViewBowlers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bowlersNewAdapter
        }

        binding.recyclerViewTotalScore.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = totalScoreNewAdapter
        }

        binding.recyclerViewWickets.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = wicketsNewAdapter
        }

        binding.recyclerViewTabs.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = scoreCardNewAdapter
        }
    }

    private fun updateDataInRecyclerView(it : List<Squad>,inningPos: Int) {
        val innings = it[0].innings
        if (innings.isEmpty()) return
        binding.apply {
            txtTotalScore.text =
                "${innings[inningPos].runs}/${innings[inningPos].wickets} (${innings[inningPos].overs})"
            tvExtrasTxt1.text = innings[inningPos].extras
            tvExtrasTxt2.text =
                "(b ${innings[inningPos].bye}, lb ${innings[inningPos].legbye}, nb ${innings[inningPos].noball}, w ${innings[inningPos].wide})"
        }
        totalScoreNewAdapter.setData(innings[inningPos].batting)
        bowlersNewAdapter.setData(innings[inningPos].bowling)
        wicketsNewAdapter.setData(innings[inningPos].fall_of_wickets_array)

    }

    companion object {
        @JvmStatic
        fun newInstance(squad: ArrayList<Squad>) = ScoreCardFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("squad", ArrayList(squad))
            }
        }
    }

    override fun onInningTabClick(inningTab: InningTab) {
        inningsTabPos = inningTab.inningsNo.minus(1)
        updateDataInRecyclerView(squad,inningsTabPos)
    }
}