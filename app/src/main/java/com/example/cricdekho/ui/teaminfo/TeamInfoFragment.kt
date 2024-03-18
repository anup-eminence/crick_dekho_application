package com.example.cricdekho.ui.teaminfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.cricdekho.R
import com.example.cricdekho.databinding.FragmentTeamInfoBinding
import com.example.cricdekho.ui.activity.HomeActivity
import com.example.cricdekho.ui.home.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TeamInfoFragment : BaseFragment() {
    private lateinit var binding: FragmentTeamInfoBinding
    private lateinit var teamInfoViewPagerAdapter: TeamInfoViewPagerAdapter
    private lateinit var tournamentSlug: String
    private lateinit var seriesKeedaSlug: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tournamentSlug = it.getString("tournament_slug").toString()
            seriesKeedaSlug = it.getString("series_keeda_slug").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.tabLayout.getTabAt(0)?.select()
        setToolBar()
        setViewPagerAdapter()
    }

    private fun setToolBar() {
        val yourActivity = activity as? HomeActivity
        yourActivity?.showToolBarMethod(
            title = "",
            menu = false,
            logo = true,
            search = false,
            setting = false,
            back = true,
            share = false
        )
    }

    private fun setViewPagerAdapter() {
        teamInfoViewPagerAdapter = TeamInfoViewPagerAdapter(
            requireActivity().supportFragmentManager, lifecycle, tournamentSlug, seriesKeedaSlug
        )
        binding.viewPager.adapter = teamInfoViewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "About"
                1 -> tab.text = "Latest News"
                2 -> tab.text = "Squad"
            }
        }.attach()

        binding.tabLayout.getTabAt(0)?.view?.background = ContextCompat.getDrawable(
            requireContext(), R.drawable.bg_grey_shape
        )

        binding.viewPager.offscreenPageLimit = 3
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

    companion object {
        @JvmStatic
        fun newInstance() = TeamInfoFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}