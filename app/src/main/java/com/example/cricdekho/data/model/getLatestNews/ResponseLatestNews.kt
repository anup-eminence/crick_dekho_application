package com.example.cricdekho.data.model.getLatestNews

import com.google.gson.annotations.SerializedName

data class ResponseLatestNews(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("p")
	val p: String? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("topic")
	val topic: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
