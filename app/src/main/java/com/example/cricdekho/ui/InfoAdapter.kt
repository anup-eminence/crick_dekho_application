package com.example.cricdekho.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.Player
import com.example.cricdekho.databinding.ItemInfoTeam1Binding
import com.example.cricdekho.theme.CurrentTheme
import com.example.cricdekho.ui.matchdetails.info.PlayerDiffUtils

class InfoAdapter : RecyclerView.Adapter<InfoAdapter.InfoListViewHolder>() {

    private var oldList = emptyList<Player>()

    private var infoAdapterClickListener: InfoAdapterClickListener? = null

    fun setInfoAdapterListener(infoAdapterClickListener: InfoAdapterClickListener){
        this.infoAdapterClickListener = infoAdapterClickListener
    }

    class InfoListViewHolder(val binding: ItemInfoTeam1Binding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoListViewHolder {
        val binding = ItemInfoTeam1Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return InfoListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    override fun onBindViewHolder(holder: InfoListViewHolder, position: Int) {
        bind(holder,position)
    }

    private fun bind(holder: InfoListViewHolder,position: Int){
        val item = oldList[position]
        holder.binding.apply {
            Glide.with(root.context).load(item.playerImages).placeholder(R.drawable.ic_player).into(ivPlayer)
            tvName.text = item.name
            tvExpert.text = item.role
            if (item.position == "captain") {
                tvCaption.visibility = View.VISIBLE
            } else {
                tvCaption.visibility = View.GONE
            }
        }

        if (item?.sk_slug.isNullOrEmpty().not()) {
            holder.binding.tvName.setTextColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.blue
                )
            )
            holder.binding.root.setOnClickListener {
                infoAdapterClickListener?.onAdapterItemClick(item)
            }
        } else {
            CurrentTheme.changeTextColor(holder.binding.tvName,holder.binding.root.context)
        }

    }

    fun setData(newList: List<Player>){
        val diffUtil = PlayerDiffUtils(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    interface InfoAdapterClickListener {
        fun onAdapterItemClick(player: Player)
    }

}