package com.example.cricdekho.ui.home.adapter

import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getCricketMatches.Data
import com.example.cricdekho.databinding.ItemHomeBinding
import easyadapter.dc.com.library.EasyAdapter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class HomeMatchAdapter : EasyAdapter<Data, ItemHomeBinding>(R.layout.item_home) {
    override fun onBind(binding: ItemHomeBinding, model: Data) {
        binding.apply {
            Glide.with(root.context).load(model.t1_flag).placeholder(R.drawable.ic_team_default).into(ivFlag1)
            Glide.with(root.context).load(model.t2_flag).placeholder(R.drawable.ic_team_default).into(ivFlag2)
            tvTitle1.text = model.t1_key
            tvTitle2.text = model.t2_key
            tvTitle3.text = model.result
            tvTitle3.isSelected = true
            tvTitle3.ellipsize = TextUtils.TruncateAt.MARQUEE;
            tvTitle3.setSelected(true);
            tvTitle3.setSingleLine(true);
            tvTitle3.setHorizontallyScrolling(true);
            tvTitle3.marqueeRepeatLimit = 100000;
            tvTitle3.movementMethod = ScrollingMovementMethod.getInstance()


            if (model.status.lowercase() == "live") {
                tvLive.visibility = View.VISIBLE
            } else {
                tvLive.visibility = View.GONE
            }

            if (model.t1_score.isEmpty() || model.t1_score == "") {
                tvRuns1.text = convertDateFormat(model.date)
            } else {
                tvRuns1.text = model.t1_score
            }

            if ((model.t2_score.isEmpty() || model.t2_score == "") && model.status.lowercase() != "live") {
                val parts = model.time.split("<br/>")
                tvRuns2.text = convertTime(parts[0])
            } else {
                tvRuns2.text = model.t2_score
            }

            if (!model.has_table) {
                tvPoints.visibility = View.GONE
            } else {
                tvPoints.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreatingHolder(binding: ItemHomeBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.tvSchedule.setOnClickListener(easyHolder.clickListener)
        binding.tvPoints.setOnClickListener(easyHolder.clickListener)
        binding.clItem.setOnClickListener(easyHolder.clickListener)
    }

    private fun convertDateFormat(originalDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMM, EEEE", Locale.getDefault())
            val date = inputFormat.parse(originalDate)

            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return originalDate
    }

    private fun convertTime(inputTime: String): String {
        try {
            val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val time = inputFormat.parse(inputTime)

            return outputFormat.format(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return inputTime
    }
}