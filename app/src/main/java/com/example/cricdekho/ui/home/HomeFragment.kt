package com.example.cricdekho.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.HomeTrendingList
import com.example.cricdekho.data.model.getCricketMatches.Data
import com.example.cricdekho.data.model.getCricketMatches.ResponseHomeMatch
import com.example.cricdekho.data.model.getHomeNews.DataItem
import com.example.cricdekho.data.model.getHomeNews.ResponseHomeNews
import com.example.cricdekho.data.model.getHomeSidebarNews.NewsItem
import com.example.cricdekho.data.model.getHomeSidebarNews.ResponseLatestPopularNews
import com.example.cricdekho.data.remote.SocketManager
import com.example.cricdekho.databinding.FragmentHomeBinding
import com.example.cricdekho.ui.home.adapter.HomeExtraNewsAdapter
import com.example.cricdekho.ui.home.adapter.HomeFeatureAdapter
import com.example.cricdekho.ui.home.adapter.HomeMatchAdapter
import com.example.cricdekho.ui.home.adapter.HomeNewsAdapter
import com.example.cricdekho.ui.home.adapter.HomeTabAdapter
import com.example.cricdekho.ui.home.adapter.HomeTrendingAdapter
import com.example.cricdekho.util.DotsIndicatorDecoration
import com.example.cricdekho.util.hide
import com.example.cricdekho.util.show


class HomeFragment : BaseFragment(), HomeNewsAdapter.NewsAdapterClickListener, HomeExtraNewsAdapter.ExtraNewsAdapterClickListener,
    HomeTabAdapter.OnFeatureItemClick {
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
    private var tournamentSlug = "featured"
    private val responseHomeNews = ArrayList<ResponseHomeNews>()
    private val responseLatestPopularNews = ArrayList<ResponseLatestPopularNews>()

    private lateinit var homeTabAdapter : HomeTabAdapter

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
        setUpHomeTabAdapter()
        setUpNewsAdapter()
    }

    private fun initView() {
        progressBarListener.showProgressBar()
        homeViewModel = ViewModelProvider(this)[HomeFeatureViewModel::class.java]

        callMatchApi()
        setUpTrendingAdapter()
        selectTextView(binding.tvLatestNews)
    }

    private fun setOnClickListener() {
        binding.tvLatestNews.setOnClickListener {
            selectTextView(binding.tvLatestNews)
            updateNewsAdapter(responseLatestPopularNews[0].data?.latest)
        }
        binding.tvMostPopular.setOnClickListener {
            selectTextView(binding.tvMostPopular)
            updateNewsAdapter(responseLatestPopularNews[0].data?.popular)
        }
    }

    private fun callMatchApi() {
        homeFeatureViewModel.loadDataMatch(tournamentSlug)
        homeFeatureViewModel.dataTab.observe(viewLifecycleOwner, Observer {
            responseHomeFeature.clear()
            responseHomeFeature.addAll(it.data)
           // setUpFeatureAdapter()
            homeTabAdapter.setData(it.data)
        })

        homeFeatureViewModel.dataMatch.observe(viewLifecycleOwner, Observer {
            binding.clMain.isVisible = true
            progressBarListener.hideProgressBar()
            responseHomeMatch.clear()
            responseMatch.clear()
            responseHomeMatch.addAll(listOf(it))
            responseMatch.addAll(responseHomeMatch[0].data)
            setUpMatchAdapter()
            fetchSocketData(tournamentSlug)
        })

        homeFeatureViewModel.dataHomeNews.observe(viewLifecycleOwner, Observer {
            responseHomeNews.clear()
            responseHomeNews.addAll(listOf(it))
            setData()
            setUpExtraNewsAdapter()
        })

        homeFeatureViewModel.dataLatestPopularNews.observe(viewLifecycleOwner, Observer {
            responseLatestPopularNews.clear()
            responseLatestPopularNews.addAll(listOf(it))
            updateNewsAdapter(responseLatestPopularNews[0].data?.latest)
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

    private fun setData() {
        binding.apply {
            Glide.with(requireContext()).load(responseHomeNews[0].data?.get(0)?.img).into(ivImage)
            tvImage.text = responseHomeNews[0].data?.get(0)?.title
            tvTime.text = responseHomeNews[0].data?.get(0)?.time

            ivImage.setOnClickListener {
                val bundle = bundleOf("link" to responseHomeNews[0].data?.get(0)?.link)
                findNavController().navigate(
                    R.id.action_homeFragment_to_newsDetailFragment, bundle
                )
            }
        }
    }

    private fun setUpHomeTabAdapter() {
        homeTabAdapter = HomeTabAdapter()
        homeTabAdapter.setOnTabItemClick(this@HomeFragment)
        binding.recyclerViewFeatures.apply {
            layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeTabAdapter
        }
    }

    private fun setUpFeatureAdapter() {
        homeFeatureAdapter = HomeFeatureAdapter()
        binding.recyclerViewFeatures.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homeFeatureAdapter.addAll(responseHomeFeature, true)
        binding.recyclerViewFeatures.adapter = homeFeatureAdapter
        homeFeatureAdapter.notifyDataSetChanged()

        homeFeatureAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.tvText -> {
                    println(">>>>>>>>slug $tournamentSlug")
                    SocketManager.removeEventListener(tournamentSlug)
                    tournamentSlug = model.slug
                    println(">>>>>>>>>>>>newslug $tournamentSlug>>")
                    fetchSocketData(model.slug)
                    progressBarListener.showProgressBar()
                }
            }
        }
    }

    private fun initMatchAdapter() {
        val snapHelper: SnapHelper = PagerSnapHelper()
        homeMatchAdapter = HomeMatchAdapter()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = homeMatchAdapter
        binding.recyclerView.onFlingListener = null;
        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.addItemDecoration(
            DotsIndicatorDecoration(
                colorInactive = ContextCompat.getColor(requireContext(), R.color.light_grey),
                colorActive = ContextCompat.getColor(requireContext(), R.color.black)
            )
        )
    }

    override fun onResume() {
        super.onResume()
        SocketManager.connect()
    }

    private fun setUpMatchAdapter() {
        homeMatchAdapter.clear(true)
        homeMatchAdapter.addAll(responseMatch, true)
        binding.matchFeatureProgress.hide()
        homeMatchAdapter.notifyDataSetChanged()

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
                    SocketManager.removeEventListener(tournamentSlug)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_matchDetailsFragment, bundle
                    )
                }
            }
        }
    }

    private fun setUpNewsAdapter() {
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())
        homeNewsAdapter = HomeNewsAdapter()
        homeNewsAdapter.setNewsAdapterListener(this@HomeFragment)
        binding.recyclerViewNews.adapter = homeNewsAdapter
    }

    private fun updateNewsAdapter(list: List<NewsItem?>?) {
        homeNewsAdapter.newsItem = list
        homeNewsAdapter.notifyDataSetChanged()
    }

    override fun onNewsAdapterItemClick(item: NewsItem) {
        val bundle = bundleOf("link" to item.link)
        findNavController().navigate(
            R.id.action_homeFragment_to_newsDetailFragment, bundle
        )
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
        binding.recyclerViewExtraNews.layoutManager = LinearLayoutManager(requireContext())
        homeExtraNewsAdapter = HomeExtraNewsAdapter(responseHomeNews[0].data)
        homeExtraNewsAdapter.setExtraNewsAdapterListener(this@HomeFragment)
        binding.recyclerViewExtraNews.adapter = homeExtraNewsAdapter
    }

    override fun onAdapterItemClick(item: DataItem) {
        val bundle = bundleOf("link" to item.link)
        findNavController().navigate(
            R.id.action_homeFragment_to_newsDetailFragment, bundle
        )
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


    override fun onPause() {
        super.onPause()
        SocketManager.disconnect()
        println(">>>>>>>>>>>onpausecalled ")
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

    override fun onTabItemClick(model: com.example.cricdekho.data.model.getCricketMainTabs.Data) {
        println(">>>>>>>>slug $tournamentSlug")
        binding.matchFeatureProgress.show()
        SocketManager.removeEventListener(tournamentSlug)
        tournamentSlug = model.slug
        println(">>>>>>>>>>>>newslug $tournamentSlug>>")
        fetchSocketData(model.slug)
        progressBarListener.showProgressBar(progressColor = R.color.red)
    }
}