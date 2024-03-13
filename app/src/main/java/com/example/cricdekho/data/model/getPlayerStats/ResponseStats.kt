package com.example.cricdekho.data.model.getPlayerStats

import com.google.gson.annotations.SerializedName

data class ResponseStats(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class MostRecentMatchesItem(

	@field:SerializedName("tables")
	val tables: List<TablesItem?>? = null
)

data class TablesItem(

	@field:SerializedName("Mat")
	val mat: String? = null,

	@field:SerializedName("No")
	val no: String? = null,

	@field:SerializedName("BF")
	val bF: String? = null,

	@field:SerializedName("Inn")
	val inn: String? = null,

	@field:SerializedName("H")
	val h: String? = null,

	@field:SerializedName("R")
	val r: String? = null,

	@field:SerializedName("Avg")
	val avg: String? = null,

	@field:SerializedName("100s")
	val jsonMember100s: String? = null,

	@field:SerializedName("S/R")
	val sR: String? = null,

	@field:SerializedName("50s")
	val jsonMember50s: String? = null,

	@field:SerializedName("Tournament")
	val tournament: String? = null,

	@field:SerializedName("6s")
	val jsonMember6s: String? = null,

	@field:SerializedName("4s")
	val jsonMember4s: String? = null,

	@field:SerializedName("B")
	val b: String? = null,

	@field:SerializedName("Ov")
	val ov: String? = null,

	@field:SerializedName("W")
	val w: String? = null,

	@field:SerializedName("Mdns")
	val mdns: String? = null,

	@field:SerializedName("E/R")
	val eR: String? = null,

	@field:SerializedName("Dots")
	val dots: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("RUNS")
	val rUNS: String? = null,

	@field:SerializedName("Match")
	val match: String? = null,

	@field:SerializedName("O")
	val o: String? = null
)

data class Data(

	@field:SerializedName("Tournament Stats")
	val tournamentStats: List<TournamentStatsItem?>? = null,

	@field:SerializedName("VS Team Stats")
	val vSTeamStats: List<VSTeamStatsItem?>? = null,

	@field:SerializedName("Most Recent Matches")
	val mostRecentMatches: List<MostRecentMatchesItem?>? = null
)

data class DataItem(

	@field:SerializedName("")
	val jsonMember: String? = null,

	@field:SerializedName("NO")
	val nO: String? = null,

	@field:SerializedName("R")
	val r: String? = null,

	@field:SerializedName("Avg")
	val avg: String? = null,

	@field:SerializedName("BF")
	val bF: String? = null,

	@field:SerializedName("100s")
	val jsonMember100s: String? = null,

	@field:SerializedName("S/R")
	val sR: String? = null,

	@field:SerializedName("Inn")
	val inn: String? = null,

	@field:SerializedName("50s")
	val jsonMember50s: String? = null,

	@field:SerializedName("H")
	val h: String? = null,

	@field:SerializedName("6s")
	val jsonMember6s: String? = null,

	@field:SerializedName("4s")
	val jsonMember4s: String? = null,

	@field:SerializedName("B")
	val b: String? = null,

	@field:SerializedName("5w")
	val jsonMember5w: String? = null,

	@field:SerializedName("4w")
	val jsonMember4w: String? = null,

	@field:SerializedName("W")
	val w: String? = null,

	@field:SerializedName("Mdns")
	val mdns: String? = null,

	@field:SerializedName("10w")
	val jsonMember10w: String? = null,

	@field:SerializedName("E/R")
	val eR: String? = null,

	@field:SerializedName("Dots")
	val dots: String? = null
)

data class TournamentStatsItem(

	@field:SerializedName("tables")
	val tables: List<TablesItem?>? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class VSTeamStatsItem(

	@field:SerializedName("tables")
	val tables: List<TablesItem?>? = null,

	@field:SerializedName("title")
	val title: String? = null
)
