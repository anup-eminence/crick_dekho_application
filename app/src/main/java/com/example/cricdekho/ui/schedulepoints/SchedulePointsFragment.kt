package com.example.cricdekho.ui.schedulepoints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cricdekho.R
import com.example.cricdekho.databinding.FragmentSchedulePointsBinding
import com.example.cricdekho.ui.activity.HomeActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SchedulePointsFragment : Fragment() {
    private lateinit var binding: FragmentSchedulePointsBinding
    private lateinit var schedulePointViewPagerAdapter: SchedulePointViewPagerAdapter
    private lateinit var tournamentSlug: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tournamentSlug = arguments?.getString("tournament_slug").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSchedulePointsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        view?.postDelayed({
            selectFirstTab()
        }, 100)

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
        setViewPagerAdapter()
    }

    private fun setViewPagerAdapter() {
        schedulePointViewPagerAdapter =
            SchedulePointViewPagerAdapter(requireActivity(), tournamentSlug)
        binding.viewPager.adapter = schedulePointViewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Schedule"
                1 -> tab.text = "Points Table"
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.view?.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.bg_grey_shape
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.background = null
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun selectFirstTab() {
        val firstTab = binding.tabLayout.getTabAt(0)
        firstTab?.let {
            it.select()
            it.view.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_grey_shape)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SchedulePointsFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}