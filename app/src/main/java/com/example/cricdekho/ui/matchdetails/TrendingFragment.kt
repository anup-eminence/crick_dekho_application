package com.example.cricdekho.ui.matchdetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.R
import com.example.cricdekho.databinding.FragmentTrendingBinding
import com.example.cricdekho.data.model.HomeTrendingList
import com.example.cricdekho.ui.home.adapter.HomeTrendingAdapter

class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding
    private lateinit var homeTrendingAdapter: HomeTrendingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        setUpAdapter()
    }

    private fun setUpAdapter() {
        homeTrendingAdapter = HomeTrendingAdapter()
        val recyclerViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
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
        homeTrendingAdapter.addAll(homeTrendingList, false)
        binding.recyclerView.adapter = homeTrendingAdapter
        homeTrendingAdapter.notifyDataSetChanged()
        binding.recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)

        homeTrendingAdapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.ivTwitter -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.setType("text/plain")
                    intent.putExtra(Intent.EXTRA_SUBJECT, "")
                    intent.putExtra(Intent.EXTRA_TEXT, "https://twitter.com/Sportskeeda")
                    startActivity(Intent.createChooser(intent, "Share Via"))
                }
            }
        }
    }

}