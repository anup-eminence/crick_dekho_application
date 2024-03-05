package com.example.cricdekho.data.model.getPlayerInfo

import com.google.gson.annotations.SerializedName

data class ResponsePlayerInfo(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Worldcup(

	@field:SerializedName("Mat")
	val mat: String? = null,

	@field:SerializedName("Avg")
	val avg: String? = null,

	@field:SerializedName("S/R")
	val sR: String? = null,

	@field:SerializedName("H/S")
	val hS: String? = null,

	@field:SerializedName("Runs")
	val runs: String? = null
)

data class DataItem(

	@field:SerializedName("R")
	val r: String? = null,

	@field:SerializedName("BF")
	val bF: String? = null,

	@field:SerializedName("S/R")
	val sR: String? = null,

	@field:SerializedName("W")
	val w: String? = null,

	@field:SerializedName("E/R")
	val eR: String? = null,

	@field:SerializedName("6s")
	val jsonMember6s: String? = null,

	@field:SerializedName("Match")
	val match: String? = null,

	@field:SerializedName("4s")
	val jsonMember4s: String? = null,

	@field:SerializedName("O")
	val o: String? = null,

	@field:SerializedName("Mat")
	val mat: String? = null,

	@field:SerializedName("NO")
	val nO: String? = null,

	@field:SerializedName("St")
	val st: String? = null,

	@field:SerializedName("Inn")
	val inn: String? = null,

	@field:SerializedName("H")
	val h: String? = null,

	@field:SerializedName("Game Type")
	val gameType: String? = null,

	@field:SerializedName("Ct")
	val ct: String? = null,

	@field:SerializedName("Avg")
	val avg: String? = null,

	@field:SerializedName("100s")
	val jsonMember100s: String? = null,

	@field:SerializedName("50s")
	val jsonMember50s: String? = null,

	@field:SerializedName("5w")
	val jsonMember5w: String? = null,

	@field:SerializedName("10w")
	val jsonMember10w: String? = null,

	@field:SerializedName("Best")
	val best: String? = null
)

data class Data(

	@field:SerializedName("personalInfo")
	val personalInfo: PersonalInfo? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("tables")
	val tables: List<TablesItem?>? = null,

	@field:SerializedName("worldcup")
	val worldcup: Worldcup? = null,

	@field:SerializedName("meta")
	val meta: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class PersonalInfo(

	@field:SerializedName("Bowling Style")
	val bowlingStyle: String? = null,

	@field:SerializedName("Jersey No.")
	val jerseyNo: String? = null,

	@field:SerializedName("Batting Style")
	val battingStyle: String? = null,

	@field:SerializedName("Current Team(s)")
	val currentTeamS: String? = null,

	@field:SerializedName("Full Name")
	val fullName: String? = null,

	@field:SerializedName("Debut")
	val debut: String? = null,

	@field:SerializedName("Nationality")
	val nationality: String? = null,

	@field:SerializedName("Role")
	val role: String? = null,

	@field:SerializedName("Birth Place")
	val birthPlace: String? = null,

	@field:SerializedName("Family")
	val family: String? = null,

	@field:SerializedName("Date of Birth")
	val dateOfBirth: String? = null,

	@field:SerializedName("Height")
	val height: String? = null,

	@field:SerializedName("Age")
	val age: String? = null
)

data class TablesItem(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("heading")
	val heading: String? = null
)
