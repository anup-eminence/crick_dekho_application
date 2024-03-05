package com.example.cricdekho.ui.matchdetails.commentary

import android.text.Editable
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.Commentary
import com.example.cricdekho.databinding.ItemCommentaryBinding
import easyadapter.dc.com.library.EasyAdapter
import org.xml.sax.XMLReader

class CommentaryAdapter :
    EasyAdapter<Commentary, ItemCommentaryBinding>(R.layout.item_commentary) {
    override fun onBind(binding: ItemCommentaryBinding, model: Commentary) {
        binding.apply {
            if (model.opta_ball_type == "wicket" || model.opta_ball_type == "normal" || model.opta_ball_type == "leg_bye" || model.opta_ball_type == "wide") {
                tvBowl.visibility = View.VISIBLE
                tvRun.visibility = View.VISIBLE
                tvText.visibility = View.VISIBLE
                viewText.visibility = View.VISIBLE

                val spannedText = convertHtmlToPlainText(model.comment_text)
                tvText.text = extractTextPart(spannedText)
                tvBowl.text = extractNumericPart(spannedText)

                if (model.opta_ball_type == "wicket" || model.runs == "W") {
                    tvRun.text = "W"
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_red)
                } else if (model.opta_ball_type == "leg_bye" && model.runs == 1) {
                    tvRun.text = "${model.runs}lb"
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_blue)
                } else if ((model.runs == 1 || model.runs == 2 || model.runs == 3) && model.opta_ball_type == "normal") {
                    tvRun.text = model.runs.toString()
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_blue)
                } else if (model.runs == 4) {
                    tvRun.text = model.runs.toString()
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_green)
                } else if (model.runs == 6) {
                    tvRun.text = model.runs.toString()
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_dark_blue)
                } else if (model.opta_ball_type == "wide") {
                    tvRun.text = "${model.runs}wd"
                    setTextViewStyle(tvRun, R.color.white, R.drawable.bg_circle_blue)
                } else {
                    tvRun.text = "0"
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

    override fun onCreatingHolder(binding: ItemCommentaryBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
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
        when (value) {
            "1", "2", "3", "1lb", "2lb", "1nb", "1wd" -> {
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

    private fun setTextViewStyle(
        textView: AppCompatTextView,
        textColorResId: Int,
        backgroundDrawableResId: Int
    ) {
        textView.setTextColor(ContextCompat.getColor(textView.context, textColorResId))
        textView.setBackgroundResource(backgroundDrawableResId)
    }
}