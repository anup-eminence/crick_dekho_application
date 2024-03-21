package com.example.cricdekho.data.model.getTeamNews

import com.google.gson.annotations.SerializedName

data class ResponseTeamNews(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class NewsItem(

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
	val type: String? = null
)

data class Data(

	@field:SerializedName("news")
	val news: List<NewsItem?>? = null,

	@field:SerializedName("source")
	val source: String? = null
)
