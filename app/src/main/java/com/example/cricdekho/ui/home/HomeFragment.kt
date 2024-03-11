package com.example.cricdekho.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.cricdekho.R
import com.example.cricdekho.data.model.HomeExtraNewsList
import com.example.cricdekho.data.model.HomeNewsList
import com.example.cricdekho.data.model.HomeTrendingList
import com.example.cricdekho.data.model.getCricketMatches.Data
import com.example.cricdekho.data.model.getCricketMatches.ResponseHomeMatch
import com.example.cricdekho.data.remote.SocketManager
import com.example.cricdekho.databinding.FragmentHomeBinding
import com.example.cricdekho.ui.home.adapter.HomeExtraNewsAdapter
import com.example.cricdekho.ui.home.adapter.HomeFeatureAdapter
import com.example.cricdekho.ui.home.adapter.HomeMatchAdapter
import com.example.cricdekho.ui.home.adapter.HomeNewsAdapter
import com.example.cricdekho.ui.home.adapter.HomeTrendingAdapter
import com.example.cricdekho.util.DotsIndicatorDecoration


class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeFeatureAdapter: HomeFeatureAdapter
    private lateinit var homeMatchAdapter: HomeMatchAdapter
    private lateinit var homeNewsAdapter: HomeNewsAdapter
    private lateinit var homeTrendingAdapter: HomeTrendingAdapter
    private lateinit var homeExtraNewsAdapter: HomeExtraNewsAdapter
    private var selectedTextView: TextView? = null

    private lateinit var homeViewModel: HomeFeatureViewModel
    private val homeFeatureViewModel: HomeFeatureViewModel by viewModels()

    private val responseHomeFeature =
        ArrayList<com.example.cricdekho.data.model.getCricketMainTabs.Data>()
    private val responseHomeMatch = ArrayList<ResponseHomeMatch>()
    private val responseMatch = ArrayList<Data>()
    private val tournamentSlug = "featured"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        setOnClickListener()
        initDotView()
        initMatchAdapter()
    }

    private fun initView() {
        progressBarListener.showProgressBar()
        SocketManager.connect()
        homeViewModel = ViewModelProvider(this)[HomeFeatureViewModel::class.java]

        callMatchApi()
        setUpNewsAdapter()
        setUpTrendingAdapter()
        setUpExtraNewsAdapter()
        selectTextView(binding.tvLatestNews)
    }

    private fun setOnClickListener() {
        binding.tvLatestNews.setOnClickListener {
            selectTextView(binding.tvLatestNews)
        }
        binding.tvMostPopular.setOnClickListener {
            selectTextView(binding.tvMostPopular)
        }
    }

    private fun callMatchApi() {
        homeFeatureViewModel.loadDataMatch(tournamentSlug)
        homeFeatureViewModel.dataTab.observe(viewLifecycleOwner, Observer {
            responseHomeFeature.clear()
            responseHomeFeature.addAll(it.data)
            setUpFeatureAdapter()
        })

        homeFeatureViewModel.dataMatch.observe(viewLifecycleOwner, Observer {
            progressBarListener.hideProgressBar()
            responseHomeMatch.clear()
            responseMatch.clear()
            responseHomeMatch.addAll(listOf(it))
            responseMatch.addAll(responseHomeMatch[0].data)
            setUpMatchAdapter()
            fetchSocketData(tournamentSlug)
        })
    }

    private fun fetchSocketData(tournamentSlug: String) {
        homeViewModel.emitSocketEvent(tournamentSlug)

        homeViewModel.observeLiveData.observe(viewLifecycleOwner) { data ->
            progressBarListener.hideProgressBar()
            if (data is ResponseHomeMatch) {
                responseHomeMatch.clear()
                responseHomeMatch.addAll(listOf(data))
                responseMatch.clear()
                responseMatch.addAll(responseHomeMatch[0].data)
                setUpMatchAdapter()
            }
        }
    }

    private fun setUpFeatureAdapter() {
        homeFeatureAdapter = HomeFeatureAdapter()
        binding.recyclerViewFeatures.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

//        homeFeatureAdapter.addAll(responseHomeFeature[0].data, false)
//        binding.recyclerViewFeatures.adapter = homeFeatureAdapter
//        homeFeatureAdapter.notifyDataSetChanged()

        homeFeatureAdapter.addAll(responseHomeFeature, true)
        binding.recyclerViewFeatures.adapter = homeFeatureAdapter
        homeFeatureAdapter.notifyDataSetChanged()

        homeFeatureAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.tvText -> {
                    fetchSocketData(model.slug)
                    progressBarListener.showProgressBar()
                }
            }
        }
    }

    private fun initMatchAdapter() {
        val snapHelper: SnapHelper = PagerSnapHelper()
        binding.recyclerView.onFlingListener = null;
        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.addItemDecoration(
            DotsIndicatorDecoration(
                colorInactive = ContextCompat.getColor(requireContext(), R.color.light_grey),
                colorActive = ContextCompat.getColor(requireContext(), R.color.black)
            )
        )
    }

    private fun setUpMatchAdapter() {
        homeMatchAdapter = HomeMatchAdapter()
        val recyclerViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homeMatchAdapter.addAll(responseMatch, true)
        binding.recyclerView.adapter = homeMatchAdapter
        homeMatchAdapter.notifyDataSetChanged()
        binding.recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)


        homeMatchAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.tvSchedule -> {
                    val bundle = bundleOf("tournament_slug" to model.event_slug)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_schedulePointsFragment, bundle
                    )
                }

                R.id.tvPoints -> {
                    val bundle = bundleOf("tournament_slug" to model.event_slug)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_schedulePointsFragment, bundle
                    )
                }

                R.id.clItem -> {
                    val bundle = bundleOf("id" to model.id, "status" to model.status)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_matchDetailsFragment, bundle
                    )
                    SocketManager.removeEventListener(tournamentSlug)
                }
            }
            fetchSocketData("featured")
        }
    }

    private fun setUpNewsAdapter() {
        homeNewsAdapter = HomeNewsAdapter()
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())
        val homeNewsList = ArrayList<HomeNewsList>()
        for (i in 1..5) {
            homeNewsList.add(
                HomeNewsList(
                    "$i",
                    "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content."
                )
            )
        }
        homeNewsAdapter.addAll(homeNewsList, true)
        binding.recyclerViewNews.adapter = homeNewsAdapter
        homeNewsAdapter.notifyDataSetChanged()
    }

    private fun setUpTrendingAdapter() {
        homeTrendingAdapter = HomeTrendingAdapter()
        binding.recyclerViewTrending.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val homeTrendingList = ArrayList<HomeTrendingList>()
        for (i in 1..20) {
            homeTrendingList.add(
                HomeTrendingList(
                    R.drawable.ic_player,
                    "Cricdekho",
                    "@cricdekho",
                    "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content.",
                    R.drawable.ic_image
                )
            )
        }
        homeTrendingAdapter.addAll(homeTrendingList, true)
        binding.recyclerViewTrending.adapter = homeTrendingAdapter
        homeTrendingAdapter.notifyDataSetChanged()
    }

    private fun setUpExtraNewsAdapter() {
        homeExtraNewsAdapter = HomeExtraNewsAdapter()
        binding.recyclerViewExtraNews.layoutManager = LinearLayoutManager(requireContext())
        val homeExtraNewsList = ArrayList<HomeExtraNewsList>()
        for (i in 1..30) {
            homeExtraNewsList.add(
                HomeExtraNewsList(
                    R.drawable.ic_image,
                    "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content.",
                    "35 minutes ago",
                    R.drawable.ic_image,
                    "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content.",
                    "35 minutes ago",
                    R.drawable.ic_image,
                    "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content.",
                    "35 minutes ago"
                )
            )
        }
        homeExtraNewsAdapter.addAll(homeExtraNewsList, false)
        binding.recyclerViewExtraNews.adapter = homeExtraNewsAdapter
        homeExtraNewsAdapter.notifyDataSetChanged()
    }

    private fun selectTextView(textView: TextView) {
        if (selectedTextView != textView) {
            selectedTextView?.let {
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                it.background = null
            }
            selectedTextView = textView
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            textView.setBackgroundResource(R.drawable.bg_grey_shape)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketManager.disconnect()
    }

    private fun initDotView() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}