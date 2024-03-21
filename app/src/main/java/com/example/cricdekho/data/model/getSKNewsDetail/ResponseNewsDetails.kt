package com.example.cricdekho.data.model.getSKNewsDetail

import com.google.gson.annotations.SerializedName

data class 	ResponseNewsDetails(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("news")
	val news: News? = null
)

data class News(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
