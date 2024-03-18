package com.example.cricdekho.data.model.getSeriesNews

import com.google.gson.annotations.SerializedName

data class ResponseTeamNews(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("news")
	val news: List<NewsItem?>? = null,

	@field:SerializedName("sidebar")
	val sidebar: Sidebar? = null
)

data class LatestItem(

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class Sidebar(

	@field:SerializedName("popular")
	val popular: List<PopularItem?>? = null,

	@field:SerializedName("latest")
	val latest: List<LatestItem?>? = null
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

data class PopularItem(

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
