package com.example.cricdekho.ui.matchdetails.commentary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.data.model.getMatchDetails.Commentary
import com.example.cricdekho.data.model.getMatchDetails.Squad
import com.example.cricdekho.databinding.FragmentCommentaryBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.matchdetails.MatchDetailViewModel
import com.example.cricdekho.ui.matchdetails.MatchDetailsFragment


class CommentaryFragment : BaseFragment() {
    private lateinit var binding: FragmentCommentaryBinding
    private lateinit var commentaryAdapter: CommentaryAdapter
    private var squad = ArrayList<Squad>()
    private var commentary = ArrayList<Commentary>()
    private val matchDetailViewModel: MatchDetailViewModel by viewModels()
    private lateinit var commentaryListAdapter: CommentaryListAdapter

    private var apiCallInProgress = false

    private lateinit var commentaryLayoutManager: LinearLayoutManager

    private var updateList = true

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
        setCommentaryAdapter()
        initPaginationCommentary()
        intiObserver()
        //setUpAdapter()
    }

    private fun intiObserver() {

        MatchDetailsFragment.matchDetailsData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                squad = it
                initView()
                if (squad[0].commentary.isNotEmpty()) {
                    if (updateList) {
                        commentary.addAll(it[0].commentary)
                        if (it[0].now_batting.team.name.isNotEmpty()) {
                            setData()
                        } else {
                            hideViews()
                        }
                        println(">>>>>>>>>>>>>>>>>.....data ${it[0].commentary.lastIndex}")
                        updateCommentry(it[0].commentary)
                        updateList = false
                    }
                    if (commentaryListAdapter.oldList[0].timestamp < it[0].commentary[0].timestamp) {
                        updateCommentry(it[0].commentary)
                        if (it[0].now_batting.team.name.isNotEmpty()) {
                            setData()
                        } else {
                            hideViews()
                        }
                    }
                }

            }
        }

        matchDetailViewModel.dataCommentary.observe(viewLifecycleOwner, Observer {
            commentary.addAll(it)
            commentaryListAdapter.updateData(it)
            apiCallInProgress = false
            progressBarListener.hideProgressBar()
        })
    }

    private fun initPaginationCommentary() {
        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1)
                        .measuredHeight - v.measuredHeight &&
                    scrollY > oldScrollY
                ) {
                    //code to fetch more data for endless scrolling
                    if (!apiCallInProgress) {
                         progressBarListener.showProgressBar()
                         apiCallInProgress = true
                        callCommentaryApi(
                            eventSlug = squad[0].topic_slug,
                            timestampOfComment = commentaryListAdapter.oldList.last().timestamp.toString()
                        )
                    }

                }
            }
        }
    }

    private fun updateCommentry(list: List<Commentary>) {
        commentaryListAdapter.setCommentaryData(list)

    }

    private fun initView() {
        if (squad[0].commentary.isNotEmpty()) {
            commentary.addAll(squad[0].commentary)
            // commentaryAdapter.addAll(commentary,true)
            //  commentaryListAdapter.setCommentaryData(newlist)
        } else {
            hideViews()
        }
    }

    private fun setCommentaryAdapter() {
        commentaryLayoutManager = LinearLayoutManager(requireContext())
        commentaryListAdapter = CommentaryListAdapter()
        //commentaryAdapter = CommentaryAdapter()
        binding.recyclerView.apply {
            layoutManager = commentaryLayoutManager
            adapter = commentaryListAdapter
            ViewCompat.setNestedScrollingEnabled(this, false);
            isNestedScrollingEnabled = false
            layoutManager?.isAutoMeasureEnabled = true;
            hasFixedSize()
        }
    }


    private fun callCommentaryApi(eventSlug: String, timestampOfComment: String) {
        matchDetailViewModel.getCommentary(eventSlug, timestampOfComment)
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
