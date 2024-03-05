package com.example.cricdekho.ui.matchdetails.commentary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.data.model.getMatchDetails.Commentary
import com.example.cricdekho.data.model.getMatchDetails.Squad
import com.example.cricdekho.databinding.FragmentCommentaryBinding
import com.example.cricdekho.ui.matchdetails.MatchDetailViewModel
import com.example.cricdekho.ui.matchdetails.MatchDetailsFragment
import easyadapter.dc.com.library.EasyAdapter

class CommentaryFragment : Fragment() {
    private lateinit var binding: FragmentCommentaryBinding
    private lateinit var commentaryAdapter: CommentaryAdapter
    private var squad = ArrayList<Squad>()
    private var commentary = ArrayList<Commentary>()
    private val matchDetailViewModel: MatchDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            squad = it.getParcelableArrayList("squad")!!
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentaryBinding.inflate(inflater, container, false)
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
        if (squad[0].commentary.isNotEmpty()) {
            commentary.clear()
            commentary.addAll(squad[0].commentary)
            setUpAdapter()
            if (squad[0].now_batting.team.name.isNotEmpty()) {
                setData()
            } else {
                hideViews()
            }
        } else {
            hideViews()
        }
    }

    private fun setUpAdapter() {
        commentaryAdapter = CommentaryAdapter()
        val recyclerViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        /*binding.recyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            stackFromEnd = true
        }*/
        commentaryAdapter.addAll(commentary, true)
        binding.recyclerView.adapter = commentaryAdapter
        commentaryAdapter.notifyDataSetChanged()
        binding.recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)

//        commentaryAdapter.setOnLoadMoreListener(
//            binding.recyclerView,
//            EasyAdapter.OnLoadMoreListener {
//                val lastVisibleItemPosition =
//                    (binding.recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//                if (lastVisibleItemPosition == commentary.size - 1) {
//                    val timestampOfComment =
//                        commentary[lastVisibleItemPosition].timestamp.toString()
//                    callCommentaryApi(squad[0].topic_slug, timestampOfComment) //Your Method
//                    return@OnLoadMoreListener true // Returns True if you have more data
//                }
//                return@OnLoadMoreListener false // Return false if you don't have more data
//            })

    }

    private fun callCommentaryApi(eventSlug: String, timestampOfComment: String) {
        matchDetailViewModel.getCommentary(eventSlug, timestampOfComment)

        matchDetailViewModel.dataCommentary.observe(viewLifecycleOwner, Observer {
            commentary.addAll(it)
            setUpAdapter()
        })
        Log.e("params", "$eventSlug,    $timestampOfComment")
    }

    private fun setData() {
        binding.apply {
            tvPlayer1.text = squad[0].now_batting.b1.name
            tvR1.text = squad[0].now_batting.b1.stats.runs
            tvB1.text = squad[0].now_batting.b1.stats.balls
            tv4s1.text = squad[0].now_batting.b1.stats.fours
            tv6s1.text = squad[0].now_batting.b1.stats.sixes
            tvSR1.text = squad[0].now_batting.b1.stats.strike_rate.toString()

            tvPlayer2.text = squad[0].now_batting.b2.name
            tvR2.text = squad[0].now_batting.b2.stats.runs
            tvB2.text = squad[0].now_batting.b2.stats.balls
            tv4s2.text = squad[0].now_batting.b2.stats.fours
            tv6s2.text = squad[0].now_batting.b2.stats.sixes
            tvSR2.text = squad[0].now_batting.b2.stats.strike_rate.toString()

            tvBowlers1.text = squad[0].now_bowling.b1.name
            tvO1.text = squad[0].now_bowling.b1.stats.overs
            tvM1.text = squad[0].now_bowling.b1.stats.maiden_overs
            tvRBowler1.text = squad[0].now_bowling.b1.stats.runs
            tvW1.text = squad[0].now_bowling.b1.stats.wickets
            tvEco1.text = squad[0].now_bowling.b1.stats.economy.toString()

            tvBowlers2.text = squad[0].now_bowling.b2.name
            tvO2.text = squad[0].now_bowling.b2.stats.overs
            tvM2.text = squad[0].now_bowling.b2.stats.maiden_overs
            tvRBowler2.text = squad[0].now_bowling.b2.stats.runs
            tvW2.text = squad[0].now_bowling.b2.stats.wickets
            tvEco2.text = squad[0].now_bowling.b2.stats.economy.toString()
        }
    }

    private fun hideViews() {
        binding.apply {
            val viewsToHide = arrayOf(
                tvBatters, tvR, tvB, tv4s, tv6s, tvSR,
                tvBowlers, tvO, tvM, tvRBowler, tvW, tvEco,
                clBatters, clBowlers,
                tvPlayer1, tvR1, tvB1, tv4s1, tv6s1, tvSR1,
                tvPlayer2, tvR2, tvB2, tv4s2, tv6s2, tvSR2,
                tvBowlers1, tvO1, tvM1, tvRBowler1, tvW1, tvEco1,
                tvBowlers2, tvO2, tvM2, tvRBowler2, tvW2, tvEco2
            )
            viewsToHide.forEach { it.visibility = View.GONE }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(squad: ArrayList<Squad>) =
            CommentaryFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("squad", ArrayList(squad))
                }
            }
    }
}
