package com.example.cricdekho.ui.teaminfo.news

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
import com.example.cricdekho.data.model.getSeriesNews.NewsItem
import com.example.cricdekho.data.model.getSeriesNews.ResponseTeamNews
import com.example.cricdekho.databinding.FragmentTeamNewsBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.teaminfo.TeamInfoViewModel

class TeamNewsFragment : BaseFragment(), TeamNewsAdapter.NewsAdapterClickListener {
    private lateinit var binding: FragmentTeamNewsBinding
    private val teamInfoViewModel: TeamInfoViewModel by viewModels()
    private lateinit var teamNewsAdapter: TeamNewsAdapter
    private var responseTeamNews: ArrayList<ResponseTeamNews>? = ArrayList()
    private lateinit var seriesKeedaSlug: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            seriesKeedaSlug = it.getString("series_keeda_slug").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        progressBarListener.showProgressBar()
        getSeriesNews()
    }

    private fun getSeriesNews() {
        teamInfoViewModel.getSeriesNews(seriesKeedaSlug)
        teamInfoViewModel.teamNews.observe(viewLifecycleOwner, Observer {
            responseTeamNews?.clear()
            responseTeamNews?.addAll(listOf(it))
            progressBarListener.hideProgressBar()
            setUpNewsAdapter()
        })
    }

    private fun setUpNewsAdapter() {
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())
        teamNewsAdapter = TeamNewsAdapter(responseTeamNews?.get(0)?.data?.news)
        teamNewsAdapter.setNewsAdapterListener(this@TeamNewsFragment)
        binding.recyclerViewNews.adapter = teamNewsAdapter
    }

    override fun onAdapterItemClick(item: NewsItem) {
        val bundle = bundleOf("link" to item.link)
        findNavController().navigate(
            R.id.action_teamInfoFragment_to_newsDetailFragment, bundle
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(seriesKeedaSlug: String) = TeamNewsFragment().apply {
            arguments = Bundle().apply {
                putString("series_keeda_slug", seriesKeedaSlug)
            }
        }
    }
}