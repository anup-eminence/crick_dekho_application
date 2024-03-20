package com.example.cricdekho.ui.newsdetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getSKNewsDetail.ResponseNewsDetails
import com.example.cricdekho.databinding.FragmentNewsDetailBinding
import com.example.cricdekho.theme.CurrentTheme
import com.example.cricdekho.ui.activity.HomeActivity
import com.example.cricdekho.ui.home.BaseFragment
import com.example.cricdekho.ui.home.HomeFeatureViewModel

class NewsDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentNewsDetailBinding
    private val homeFeatureViewModel: HomeFeatureViewModel by viewModels()
    private val responseNewsDetail = ResponseNewsDetails()
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
        val parts = link.split("/cricket/", limit = 2)

        homeFeatureViewModel.dataNewsDetail.observe(viewLifecycleOwner, Observer {
            println(">>>>>>>>>>>>>>>>>>>>"+it)
            binding.apply {
                tvTitle.text = it?.data?.news?.title
                Glide.with(requireContext()).load(it?.data?.news?.img).into(ivImage)
                tvText.text =
                    it.data?.news?.content?.let {
                        HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                    }
            }

            progressBarListener.hideProgressBar()
        })

        homeFeatureViewModel.getSKNewsDetail(parts.getOrNull(1).toString())

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