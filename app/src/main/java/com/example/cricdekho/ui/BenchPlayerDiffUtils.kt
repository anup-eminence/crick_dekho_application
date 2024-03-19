package com.example.cricdekho.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.cricdekho.data.model.getMatchDetails.BenchPlayer
import com.example.cricdekho.data.model.getMatchDetails.Player

class BenchPlayerDiffUtils(
    private val oldList: List<BenchPlayer>,
    private val newList: List<BenchPlayer>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
      return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return  when {
           oldList[oldItemPosition].name != newList[newItemPosition].name -> false
           oldList[oldItemPosition].position != newList[newItemPosition].position -> false
           oldList[oldItemPosition].playerImages != newList[newItemPosition].playerImages -> false
           oldList[oldItemPosition].role != newList[newItemPosition].role -> false
           oldList[oldItemPosition].delta != newList[newItemPosition].delta -> false
           oldList[oldItemPosition].sk_slug != newList[newItemPosition].sk_slug -> false
           oldList[oldItemPosition].slug != newList[newItemPosition].slug -> false
           oldList[oldItemPosition].provider_id != newList[newItemPosition].provider_id  -> false
           else -> true

       }
    }
}