package com.example.cricdekho.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cricdekho.R
import com.example.cricdekho.databinding.FragmentVideosBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class VideosFragment : Fragment() {
    private lateinit var binding: FragmentVideosBinding
    private lateinit var videoViewPagerAdapter: VideoViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideosBinding.inflate(inflater, container, false)
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
        videoViewPagerAdapter = VideoViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = videoViewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Reels"
                1 -> tab.text = "Videos"
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
        fun newInstance(param1: String, param2: String) = VideosFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}