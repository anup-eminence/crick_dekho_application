package com.example.cricdekho.ui.match.upcoming

import android.view.View
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getUpcomingMatches.ResponseUpcomingMatch
import com.example.cricdekho.databinding.ItemScheduleBinding
import com.example.cricdekho.theme.CurrentTheme
import easyadapter.dc.com.library.EasyAdapter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class UpcomingAdapter :
    EasyAdapter<ResponseUpcomingMatch, ItemScheduleBinding>(R.layout.item_schedule) {
    override fun onBind(binding: ItemScheduleBinding, model: ResponseUpcomingMatch) {
        binding.apply {
            Glide.with(root.context).load(model.t1_flag).placeholder(R.drawable.ic_team_default).into(ivFlag1)
            Glide.with(root.context).load(model.t2_flag).placeholder(R.drawable.ic_team_default).into(ivFlag2)
            CurrentTheme.changeTextColor(tvMatch,root.context)
            CurrentTheme.changeTextColor(tvDate,root.context)
            CurrentTheme.changeTextColor(tvTitle1,root.context)
            CurrentTheme.changeTextColor(tvTitle2,root.context)
            CurrentTheme.changeTextColor(tvRuns1,root.context)
            CurrentTheme.changeTextColor(tvRuns2,root.context)
            tvDate.text = convertDateFormat(model.date)
            tvMatch.text = model.event
            tvTitle1.text = model.t1
            tvTitle2.text = model.t2
            tvRuns1.text = model.t1_score
            tvRuns2.text = model.t2_score
            val parts = model.time.split("<br/>")
            tvDecision.text = "${convertTime(parts[0])}, ${parts[1]}"
            tvLive.visibility = View.GONE
            tvDate.visibility = View.VISIBLE
        }
    }

    override fun onCreatingHolder(binding: ItemScheduleBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.clItem.setOnClickListener(easyHolder.clickListener)
    }

    private fun convertDateFormat(originalDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMM, EEEE", Locale.getDefault())
            val date = inputFormat.parse(originalDate)
            return outputFormat.format(date!!)
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
            return outputFormat.format(time!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return inputTime
    }
}