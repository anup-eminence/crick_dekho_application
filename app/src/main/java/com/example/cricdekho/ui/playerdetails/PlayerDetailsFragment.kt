package com.example.cricdekho.ui.playerdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cricdekho.R
import com.example.cricdekho.databinding.FragmentPlayerDetailsBinding
import com.example.cricdekho.ui.activity.HomeActivity
import com.example.cricdekho.ui.home.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PlayerDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentPlayerDetailsBinding
    private lateinit var pdViewPagerAdapter: PDViewPagerAdapter
    private lateinit var playerSlug: String
    private lateinit var playerName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            playerSlug = it.getString("sk_slug").toString()
            playerName = it.getString("name").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerDetailsBinding.inflate(inflater, container, false)
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
            title = playerName,
            menu = false,
            logo = false,
            search = false,
            setting = false,
            back = true,
            share = false
        )
    }

    private fun setViewPagerAdapter() {
        pdViewPagerAdapter = PDViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle, playerSlug,playerName
        )
        binding.viewPager.adapter = pdViewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Stats"
                2 -> tab.text = "News"
            }
        }.attach()

        binding.tabLayout.getTabAt(0)?.view?.background =
            ContextCompat.getDrawable(
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
        fun newInstance() =
            PlayerDetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}