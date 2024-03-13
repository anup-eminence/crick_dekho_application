package com.example.cricdekho.data.model.getTeamInfo

import com.google.gson.annotations.SerializedName

data class ResponseTeamInfo(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TabsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class TeamInfo(

	@field:SerializedName("Owner(s)")
	val ownerS: String? = null,

	@field:SerializedName("Founded")
	val founded: String? = null,

	@field:SerializedName("Ground")
	val ground: String? = null,

	@field:SerializedName("Nickname")
	val nickname: String? = null
)

data class SquadItem(

	@field:SerializedName("players")
	val players: List<PlayersItem?>? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class PlayersItem(

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("style")
	val style: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class Data(

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("squad")
	val squad: List<SquadItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("tabs")
	val tabs: List<TabsItem?>? = null,

	@field:SerializedName("teamInfo")
	val teamInfo: TeamInfo? = null
)
