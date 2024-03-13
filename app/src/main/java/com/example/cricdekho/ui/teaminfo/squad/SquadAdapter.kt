package com.example.cricdekho.ui.teaminfo.squad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.Player
import com.example.cricdekho.data.model.getTeamInfo.PlayersItem
import com.example.cricdekho.databinding.ItemInfoTeam1Binding
import com.example.cricdekho.databinding.ItemSquadBinding
import com.example.cricdekho.ui.InfoAdapter

class SquadAdapter(private var playersItem: List<PlayersItem?>?): RecyclerView.Adapter<SquadAdapter.ViewHolder>() {

    private var squadAdapterClickListener: SquadAdapterClickListener? = null

    fun setSquadAdapterListener(squadAdapterClickListener: SquadAdapterClickListener){
        this.squadAdapterClickListener = squadAdapterClickListener
    }

    class ViewHolder(val binding: ItemSquadBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSquadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return playersItem?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(holder, position)
    }

    private fun bind(holder: ViewHolder, position: Int) {
        val item = playersItem?.get(position)
        holder.binding.apply {
            if (item != null) {
                Glide.with(root.context).load(item.img).placeholder(R.drawable.ic_player).into(ivPlayer)
                tvName.text = item.name
                tvExpert.text = item.style?.replace("\n.\n", " - ")
            }
        }

        holder.binding.root.setOnClickListener {
            squadAdapterClickListener?.onAdapterItemClick(item!!)
        }
    }
    interface SquadAdapterClickListener {
        fun onAdapterItemClick(item: PlayersItem)
    }
}