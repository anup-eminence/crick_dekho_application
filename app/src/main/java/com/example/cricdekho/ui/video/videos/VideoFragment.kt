package com.example.cricdekho.ui.video.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricdekho.R
import com.example.cricdekho.data.model.VideoList
import com.example.cricdekho.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {
    private lateinit var binding: FragmentVideoBinding
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        setUpAdapter()
    }

    private fun setUpAdapter() {
        videoAdapter = VideoAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val videoList = ArrayList<VideoList>()
        for (i in 1..10) {
            videoList.add(
                VideoList(
                    R.drawable.ic_image,
                    "songViewPagerAdapter = SongsViewPagerAdapter(childFragmentManager, fragments, titles)binding.viewPager.adapter = songViewPagerAdapter",
                    "15 days ago"
                )
            )
        }
        videoAdapter.addAll(videoList, false)
        binding.recyclerView.adapter = videoAdapter
        videoAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}