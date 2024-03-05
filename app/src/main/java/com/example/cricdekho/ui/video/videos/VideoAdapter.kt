package com.example.cricdekho.ui.video.videos

import com.example.cricdekho.R
import com.example.cricdekho.databinding.ItemVideoBinding
import com.example.cricdekho.data.model.VideoList
import easyadapter.dc.com.library.EasyAdapter

class VideoAdapter : EasyAdapter<VideoList, ItemVideoBinding>(R.layout.item_video) {
    override fun onBind(binding: ItemVideoBinding, model: VideoList) {
        binding.apply {
            ivImage.setImageResource(model.image)
            tvText.text = model.text
            tvTime.text = model.time
        }
    }

    override fun onCreatingHolder(binding: ItemVideoBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.playBtn.setOnClickListener(easyHolder.clickListener)
    }
}