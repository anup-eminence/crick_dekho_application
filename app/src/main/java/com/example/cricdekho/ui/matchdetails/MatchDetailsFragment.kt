package com.example.cricdekho.ui.matchdetails

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.Squad
import com.example.cricdekho.data.remote.SocketManager
import com.example.cricdekho.databinding.FragmentMatchDetailsBinding
import com.example.cricdekho.theme.CurrentTheme
import com.example.cricdekho.ui.activity.HomeActivity
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.util.MatchStatus
import com.example.cricdekho.util.showToast
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MatchDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentMatchDetailsBinding
    private lateinit var matchDetailViewPagerAdapter: MatchDetailViewPagerAdapter
    private lateinit var matchDetailViewModel: MatchDetailViewModel
    private lateinit var matchId: String
    private lateinit var matchStatus: String
    private val responseSquad = ArrayList<Squad>()
    private var countDownTimer: CountDownTimer? = null
    private var currentViewPagerItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchId = arguments?.getString("id").toString()
        matchStatus = arguments?.getString("status").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()

        matchDetailViewModel.errorCaught.observe(viewLifecycleOwner){
            if (it){
                progressBarListener.hideProgressBar()
                requireContext().showToast("Something Went Wrong!")
            }
        }

        setOnClickListener()

    }

    private fun initView() {
        progressBarListener.showProgressBar()
        binding.tabLayout.getTabAt(0)?.select()
        setViewPagerAdapter()
        fetchMatchDetail()

//        view?.postDelayed({
//            selectFirstTab()
//        }, 100)
    }

    override fun onResume() {
        setOnClickListener()
        super.onResume()
    }

    private fun setOnClickListener() {
        println(">>>>>>>>>>....dfklnlmf;ldkf")
        if (responseSquad.isEmpty()) return
        if (responseSquad[0].score_strip[0].slug.isNullOrEmpty().not()) {
            binding.tvTitle1.setOnClickListener {
                val bundle = bundleOf("tournament_slug" to responseSquad[0].score_strip[0].slug)
                findNavController().navigate(
                    R.id.action_matchDetailsFragment_to_teamInfoFragment, bundle
                )
            }
        }

        println(">>>>>>>>>..jnfkjn ${responseSquad[0].score_strip[1].slug}")

        if (responseSquad[0].score_strip[1].slug.isNullOrEmpty().not()) {
            binding.tvTitle2.setOnClickListener {
                val bundle = bundleOf("tournament_slug" to responseSquad[0].score_strip[1].slug)
                findNavController().navigate(
                    R.id.action_matchDetailsFragment_to_teamInfoFragment, bundle
                )
            }
        }
    }

    private fun fetchDataFromApi() {
        matchDetailViewModel.liveMatchSore.observe(viewLifecycleOwner) {
            setMatchData(it.data)
            binding.clMain.isVisible = true
            progressBarListener.hideProgressBar()
        }
        matchDetailViewModel.getLiveMatchScore(matchId)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchMatchDetail() {
        matchDetailViewModel = ViewModelProvider(this)[MatchDetailViewModel::class.java]

        if (matchStatus != null && matchId != null && (matchStatus == MatchStatus.PRE.status || matchStatus == MatchStatus.POST.status)) {
            fetchDataFromApi()
        } else {
            fetchDataFromApi()
            Handler(Looper.getMainLooper()).postDelayed({
                matchDetailViewModel.emitSocketEvent(matchId)
            },20000)

            lifecycleScope.launch {
                matchDetailViewModel.observeLiveData.observe(viewLifecycleOwner) { data ->
                    binding.clMain.isVisible = true
                    progressBarListener.hideProgressBar()
                    if (data is Squad) {
                        setMatchData(data)
                    }
                }
            }
        }
    }

    private fun setMatchData(squad: Squad){
        responseSquad.clear()
        responseSquad.addAll(listOf(squad))
        setMatchData()
        setToolbar()
        matchDetailsData.postValue(responseSquad)
        matchDetailViewPagerAdapter.setSquadList(responseSquad)
        setOnClickListener()
    }

    private fun setViewPagerAdapter() {
        currentViewPagerItem = binding.viewPager.currentItem
        matchDetailViewPagerAdapter = MatchDetailViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle)
        binding.viewPager.adapter = matchDetailViewPagerAdapter
        binding.viewPager.setCurrentItem(currentViewPagerItem, false)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Info"
                1 -> tab.text = "Fantasy"
                2 -> tab.text = "Commentary"
                3 -> tab.text = "Scorecard"
                4 -> tab.text = "Trending"
            }
        }.attach()

        binding.tabLayout.getTabAt(0)?.view?.background =
            ContextCompat.getDrawable(
                requireContext(), R.drawable.bg_grey_shape
            )

        binding.viewPager.offscreenPageLimit = 4
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.view?.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_grey_shape
                )
                tab?.position?.let { binding.viewPager.currentItem = it }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.background = null
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.view?.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_grey_shape
                )
                tab?.position?.let { binding.viewPager.currentItem = it }
            }
        })
    }

    private fun setToolbar() {
        val yourActivity = activity as? HomeActivity
        yourActivity?.showToolBarMethod(
            title = responseSquad[0].short_title,
            menu = false,
            logo = false,
            search = false,
            setting = false,
            back = true,
            share = true
        )
    }

    private fun removeToolBar() {
        val yourActivity = activity as? HomeActivity
        yourActivity?.showToolBarMethod(
            title = "",
            menu = true,
            logo = true,
            search = true,
            setting = true,
            back = false,
            share = false
        )
    }

    private fun setMatchData() {
        binding.apply {
            tvTitle1.text = responseSquad[0].score_strip[0].name
            tvTitle2.text = responseSquad[0].score_strip[1].name

            if( responseSquad[0].score_strip[0].slug.isNullOrEmpty()){
                CurrentTheme.changeTextColor(tvTitle1,requireContext())
            }

            if( responseSquad[0].score_strip[1].slug.isNullOrEmpty()){
                CurrentTheme.changeTextColor(tvTitle2,requireContext())
            }
            CurrentTheme.changeTextColor(tvRuns1,requireContext())
            CurrentTheme.changeTextColor(tvRuns2,requireContext())
            CurrentTheme.changeTextColor(tvDecision,requireContext())
            CurrentTheme.changeTextColor(tvPlayer,requireContext())

            tvRuns1.text = responseSquad[0].score_strip[0].score
            Glide.with(requireContext()).load(responseSquad[0].score_strip[0].team_flag)
                .placeholder(R.drawable.ic_team_default).into(ivFlag1)
            tvRuns2.text = responseSquad[0].score_strip[1].score
            Glide.with(requireContext()).load(responseSquad[0].score_strip[1].team_flag)
                .placeholder(R.drawable.ic_team_default).into(ivFlag2)
            if (responseSquad[0].match_status != "pre") {
                tvDecision.text = responseSquad[0].info
                if (responseSquad[0].player_of_match.player_name.isNotEmpty()) {
                    tvPlayer.text =
                        "Player of the Match: ${responseSquad[0].player_of_match.player_name}"
                } else tvPlayer.visibility = View.GONE
            } else {
                setCountDownTimer(responseSquad[0].datetime.toLong())
                tvPlayer.setTypeface(null, Typeface.BOLD)
                tvPlayer.textSize = 14f
                tvDecision.visibility = View.GONE
            }
        }
    }

    private fun selectFirstTab() {
        val firstTab = binding.tabLayout.getTabAt(0)
        firstTab?.let {
            it.select()
            it.view.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_grey_shape)
        }
    }

    private fun setCountDownTimer(targetTimestamp: Long) {
        val currentTime = System.currentTimeMillis() / 1000
        val initialTimeRemaining = TimeUnit.SECONDS.toMillis(targetTimestamp - currentTime)

        countDownTimer = object : CountDownTimer(initialTimeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                binding.tvPlayer.text =
                    String.format("Starts in %02d hr %02d m %02d s", hours, minutes, seconds)
            }

            override fun onFinish() {
                Log.e("Countdown", "Countdown finished!")
            }
        }
        countDownTimer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        responseSquad.clear()
        SocketManager.removeEventListener(matchId)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
        responseSquad.clear()
        SocketManager.removeEventListener(matchId)
        removeToolBar()
    }

    companion object {
        var matchDetailsData = MutableLiveData<ArrayList<Squad>>()
        @JvmStatic
        fun newInstance() = MatchDetailsFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}