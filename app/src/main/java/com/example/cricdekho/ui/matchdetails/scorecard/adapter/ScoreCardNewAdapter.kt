package com.example.cricdekho.ui.matchdetails.scorecard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.InningTab
import com.example.cricdekho.databinding.ItemFantasyBinding
import com.example.cricdekho.ui.matchdetails.scorecard.diffutil.ScoreCardDiffUtil

class ScoreCardNewAdapter : RecyclerView.Adapter<ScoreCardNewAdapter.ScoreCardVH>() {
    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    var oldList = emptyList<InningTab>()

    private var scoreCardListener: ScoreCardListener? = null

    class ScoreCardVH(val binding: ItemFantasyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreCardVH {
        return ScoreCardVH(
            ItemFantasyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    override fun onBindViewHolder(holder: ScoreCardVH, position: Int) {
        bindItem(holder, position)
    }

    private fun bindItem(holder: ScoreCardVH, position: Int) {
        val model = oldList[position]
        holder.binding.apply {
            tvText.text = model.name
        }
        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition;
            selectedPosition = holder.adapterPosition
            scoreCardListener?.onInningTabClick(model)
            notifyItemChanged(lastSelectedPosition);
            notifyItemChanged(selectedPosition);
        }

        if (selectedPosition == holder.adapterPosition) {
            holder.binding.tvText.setTextColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.white
                )
            )
            holder.binding.tvText.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    R.drawable.bg_rounded_shape_solid
                )
            )
        } else {
            holder.binding.tvText.setTextColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.black
                )
            )
            holder.binding.tvText.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    R.drawable.bg_rounded_shape
                )
            )
        }
    }

    fun setData(newList: List<InningTab>) {
        val diffUtil = ScoreCardDiffUtil(oldList, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        result.dispatchUpdatesTo(this)
    }

    fun setScoreCardListener(scoreCardListener: ScoreCardListener) {
        this.scoreCardListener = scoreCardListener
    }

    interface ScoreCardListener {
        fun onInningTabClick(inningTab: InningTab)
    }
}