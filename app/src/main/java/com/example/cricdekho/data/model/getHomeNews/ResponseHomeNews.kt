package com.example.cricdekho.data.model.getHomeNews

import com.google.gson.annotations.SerializedName

data class ResponseHomeNews(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("source")
	val source: String? = null,

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

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("isLiveNews")
	val isLiveNews: Boolean? = null
)