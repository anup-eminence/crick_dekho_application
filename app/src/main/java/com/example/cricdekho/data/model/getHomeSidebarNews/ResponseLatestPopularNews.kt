package com.example.cricdekho.data.model.getHomeSidebarNews

import com.google.gson.annotations.SerializedName

data class ResponseLatestPopularNews(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("popular")
	val popular: List<NewsItem?>? = null,

	@field:SerializedName("latest")
	val latest: List<NewsItem?>? = null
)

data class NewsItem(

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)