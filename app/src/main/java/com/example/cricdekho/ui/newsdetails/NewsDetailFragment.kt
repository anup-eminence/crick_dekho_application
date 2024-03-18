package com.example.cricdekho.ui.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getCricketNews.ResponseCricketNews
import com.example.cricdekho.databinding.FragmentNewsDetailBinding
import com.example.cricdekho.theme.CurrentTheme
import com.example.cricdekho.ui.activity.HomeActivity
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.home.HomeFeatureViewModel

class NewsDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentNewsDetailBinding
    private val homeFeatureViewModel: HomeFeatureViewModel by viewModels()
    private val responseCricketNews = ArrayList<ResponseCricketNews>()
    private lateinit var link: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            link = it.getString("link").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        CurrentTheme.changeTextColor(binding.tvTitle,requireContext())
        CurrentTheme.changeTextColor(binding.tvTime,requireContext())
        CurrentTheme.changeTextColor(binding.tvText,requireContext())

    }

    private fun initView() {
        progressBarListener.showProgressBar()
        setToolbar()
        getCricketNews()
    }

    private fun getCricketNews() {
        homeFeatureViewModel.getCricketNews(link)
        homeFeatureViewModel.dataCricketNews.observe(viewLifecycleOwner, Observer {
            responseCricketNews.clear()
            responseCricketNews.addAll(listOf(it))
            progressBarListener.hideProgressBar()
            setData()
        })
    }

    private fun setData() {
        binding.apply {
            Glide.with(requireContext()).load(responseCricketNews[0].data?.image).into(ivImage)
            tvTitle.text = responseCricketNews[0].data?.title

            var content: String = ""
            responseCricketNews[0].data?.content?.forEach {
                content += (it) + "\n\n\n"
            }
            tvText.text = content
        }
    }

    private fun setToolbar() {
        val yourActivity = activity as? HomeActivity
        yourActivity?.showToolBarMethod(
            title = getString(R.string.app_name),
            menu = false,
            logo = false,
            search = false,
            setting = false,
            back = true,
            share = false
        )
    }

    private fun removeToolBar() {
        val yourActivity = activity as? HomeActivity
        yourActivity?.showToolBarMethod(
            title = "",
            menu = true,
            logo = true,
            search = true,
            setting = true,
            back = false,
            share = false
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        removeToolBar()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}