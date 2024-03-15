package com.example.cricdekho.data.model.getCricketNews

import com.google.gson.annotations.SerializedName

data class ResponseCricketNews(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: List<String?>? = null
)
