package com.example.cricdekho.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cricdekho.R
import com.example.cricdekho.databinding.FragmentMatchesBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MatchesFragment : Fragment() {
    private lateinit var binding: FragmentMatchesBinding
    private lateinit var matchViewPagerAdapter: MatchViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        view?.postDelayed({
            selectFirstTab()
        }, 100)
        setViewPagerAdapter()
    }

    private fun setViewPagerAdapter() {
        matchViewPagerAdapter = MatchViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = matchViewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Live"
                1 -> tab.text = "Upcoming"
                2 -> tab.text = "Result"
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
        fun newInstance() =
            MatchesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}