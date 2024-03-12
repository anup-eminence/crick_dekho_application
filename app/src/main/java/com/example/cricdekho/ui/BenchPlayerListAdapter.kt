package com.example.cricdekho.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.BenchPlayer
import com.example.cricdekho.data.model.getMatchDetails.Player
import com.example.cricdekho.databinding.ItemInfoTeam1Binding
import com.example.cricdekho.ui.matchdetails.info.PlayerDiffUtils

class BenchPlayerListAdapter : RecyclerView.Adapter<BenchPlayerListAdapter.InfoListViewHolder>() {

    private var oldList = emptyList<BenchPlayer>()

    private var benchPlayerAdapterListener : BenchPlayerAdapterListener ?= null

    fun setBenchAdapterListener(benchPlayerAdapterListener: BenchPlayerAdapterListener){
        this.benchPlayerAdapterListener = benchPlayerAdapterListener
    }

    class InfoListViewHolder(val binding: ItemInfoTeam1Binding) : RecyclerView.ViewHolder(binding.root){

    }

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

        holder.binding.root.setOnClickListener {
            benchPlayerAdapterListener?.onBenchPlayerClick(item)
        }
    }

    fun setData(newList: List<BenchPlayer>){
        val diffUtil = BenchPlayerDiffUtils(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    interface BenchPlayerAdapterListener {
        fun onBenchPlayerClick(player: BenchPlayer)
    }

}