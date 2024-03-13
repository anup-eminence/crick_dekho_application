package com.example.cricdekho.ui.matchdetails.commentary

import android.text.Editable
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.Commentary
import com.example.cricdekho.databinding.ItemCommentaryBinding
import org.xml.sax.XMLReader
import kotlin.math.roundToInt

class CommentaryListAdapter : RecyclerView.Adapter<CommentaryListAdapter.CommentaryVH>() {

    var oldList = emptyList<Commentary>()

    class CommentaryVH(val binding: ItemCommentaryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentaryVH {
        return CommentaryVH(
            ItemCommentaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    override fun onBindViewHolder(holder: CommentaryVH, position: Int) {
        bindItem(holder, position)
    }

    private fun bindItem(holder: CommentaryVH, position: Int) {
        val model = oldList[position]
        holder.binding.apply {
            if (model.opta_ball_type == "wicket" || model.opta_ball_type == "normal" || model.opta_ball_type == "leg_bye" || model.opta_ball_type == "wide" || model.opta_ball_type == "bye") {
                tvBowl.visibility = View.VISIBLE
                tvRun.visibility = View.VISIBLE
                tvText.visibility = View.VISIBLE
                viewText.visibility = View.VISIBLE

                val spannedText = convertHtmlToPlainText(model.comment_text)
                tvText.text = extractTextPart(spannedText)
                tvBowl.text = extractNumericPart(spannedText)

                if (model.runs is Double || model.runs is Float) {
                    model.runs = (model.runs as Double).roundToInt()
                }

                if (model.opta_ball_type == "wicket" || model.runs == "W") {
                    tvRun.text = "W"
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_red)
                } else if (model.opta_ball_type == "leg_bye" && model.runs == 1) {
                    tvRun.text = "${model.runs}lb"
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_blue)
                } else if ((model.runs == 1 || model.runs == 2 || model.runs == 3) && model.opta_ball_type == "normal") {
                    tvRun.text = model.runs.toString()
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_blue)
                } else if ((model.runs == 1 || model.runs == 2 || model.runs == 3 || model.runs == 4) && model.opta_ball_type == "bye") {
                    tvRun.text = "${model.runs}b"
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_blue)
                } else if (model.opta_ball_type == "wide") {
                    tvRun.text = "${model.runs}wd"
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_blue)
                } else if (model.runs != null && model.runs.toString().contains("0")) {
                    tvRun.text = model.runs.toString()
                } else {
                    if (model.runs != null) {
                        if (model.runs.toString().contains("4")) {
                            setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_green)

                        } else if (model.runs.toString().contains("6")) {
                            setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_dark_blue)

                        } else {
                            setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_blue)
                        }
                        tvRun.text = model.runs.toString()
                    }
                }

            } else if (model.opta_ball_type == "end of over") {
                clScores.visibility = View.VISIBLE
                tvBatter1.text = model.batsmen[0].name
                tvBatter2.text = model.batsmen[1].name
                tvBowler.text = model.bowlers[0].name
                txtScore.text = model.score
                txtOver.text = model.over
                tvRuns.text = "${model.runs} Runs"
                tvBatterRun1.text = "${model.batsmen[0].runs} (${model.batsmen[0].balls})"
                tvBatterRun2.text = "${model.batsmen[1].runs} (${model.batsmen[1].balls})"
                tvBowlersBall.text =
                    "${model.bowlers[0].wickets} / ${model.bowlers[0].runs_conceded}"

                val parts = model.over_summary.split(" ")
                setTextViewWithCondition(tvR1, parts[0])
                setTextViewWithCondition(tvR2, parts[1])
                setTextViewWithCondition(tvR3, parts[2])
                setTextViewWithCondition(tvR4, parts[3])
                setTextViewWithCondition(tvR5, parts[4])
                setTextViewWithCondition(tvR6, parts[5])
                if (parts.size == 6) {
                    tvR7.visibility = View.GONE
                } else {
                    tvR7.visibility = View.VISIBLE
                    setTextViewWithCondition(tvR7, parts[6])
                }

            } else {
                tvComment.visibility = View.VISIBLE
                viewComment.visibility = View.VISIBLE
                tvComment.text = convertHtmlToPlainText(model.comment_text)
            }

            if (model.source == "custom") {
                imageView.visibility = View.VISIBLE
                tvTextImage.visibility = View.VISIBLE
                tvTextImage.text = model.custom_title
                Glide.with(root.context).load(model.custom_image).into(imageView)
            }
        }
    }

    fun setCommentaryData(newList: List<Commentary>) {
        val diffUtil = CommentaryDiffUtils(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    private fun convertHtmlToPlainText(htmlString: String): Spanned {
        if (htmlString.isNullOrEmpty()) {
            return SpannableString("")
        }
        return HtmlCompat.fromHtml(
            htmlString,
            HtmlCompat.FROM_HTML_MODE_LEGACY,
            null,
            StrongTagHandler()
        )
    }

    private class StrongTagHandler : Html.TagHandler {
        override fun handleTag(
            opening: Boolean,
            tag: String?,
            output: Editable?,
            xmlReader: XMLReader?
        ) {
            if (tag.equals("strong", ignoreCase = true)) {
                processTag(opening, output)
            }
        }

        private fun processTag(opening: Boolean, output: Editable?) {
            val startTag = if (opening) "<b>" else ""
            val endTag = if (opening) "" else "</b>"
            output?.append(startTag)
            output?.append(endTag)
        }
    }

    private fun extractNumericPart(spannedText: Spanned): String {
        val regex = """(\d+\.\d+)""".toRegex()
        val matchResult = regex.find(spannedText.toString())
        return matchResult?.groupValues?.get(1) ?: ""
    }

    private fun extractTextPart(spannedText: Spanned): String {
        val regex = """\d+\.\d+\s(.+)""".toRegex()
        val matchResult = regex.find(spannedText.toString())
        return matchResult?.groupValues?.get(1) ?: ""
    }

    private fun setTextViewWithCondition(textView: AppCompatTextView, value: String) {
        if (value.contains("lb") || value.contains("wd") || value.contains("b")) {
            textView.text = value
            setTextViewStyle(textView, R.color.white, R.drawable.bg_circle_blue)
        } else {
            when (value) {
                "1", "2", "3" -> {
                    textView.text = value
                    setTextViewStyle(textView, R.color.white, R.drawable.bg_circle_blue)
                }

                "4" -> {
                    textView.text = value
                    setTextViewStyle(textView, R.color.white, R.drawable.bg_circle_green)
                }

                "6" -> {
                    textView.text = value
                    setTextViewStyle(textView, R.color.white, R.drawable.bg_circle_dark_blue)
                }

                "W", "w" -> {
                    textView.text = value
                    setTextViewStyle(textView, R.color.white, R.drawable.bg_circle_red)
                }

                else -> {
                    textView.text = value
                }
            }
        }
    }

    private fun setTextViewStyle(
        textView: AppCompatTextView,
        textColorResId: Int,
        backgroundDrawableResId: Int
    ) {
        textView.setTextColor(ContextCompat.getColor(textView.context, textColorResId))
        textView.setBackgroundResource(backgroundDrawableResId)
    }
}