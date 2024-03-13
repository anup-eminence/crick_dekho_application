package com.example.cricdekho.ui.playerdetails.stat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.databinding.FragmentOverviewBinding
import com.example.cricdekho.databinding.StatFragmentBinding
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.playerdetails.stat.adapter.BattingStatAdapter
import com.example.cricdekho.ui.playerdetails.stat.adapter.VsStatAdapter

class PlayerStat  : BaseFragment(), FullScrollLayoutManager.ScrollListener {

    private lateinit var binding : StatFragmentBinding
    private lateinit var battingAdapter : BattingStatAdapter
    private lateinit var vsStatAdapter : VsStatAdapter
    private lateinit var fullScrollLayoutManager: FullScrollLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        battingAdapter = BattingStatAdapter()
        vsStatAdapter = VsStatAdapter()
        fullScrollLayoutManager = FullScrollLayoutManager(requireContext(),this@PlayerStat)
        binding.leftRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = vsStatAdapter
        }

        binding.rightRv.apply {
            layoutManager = fullScrollLayoutManager
            adapter = battingAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun scrollBy(dx: Int) {
        binding.topInnLayout.smoothScrollTo(dx,0)
        println(">>>>>>>>>>>>>>>.scroll $dx")
    }
}