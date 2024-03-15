package com.example.cricdekho.data.remote

import com.example.cricdekho.data.model.getCricketMainTabs.ResponseHomeFeature
import com.example.cricdekho.data.model.getCricketMatches.ResponseHomeMatch
import com.example.cricdekho.data.model.getCricketNews.ResponseCricketNews
import com.example.cricdekho.data.model.getLatestNews.ResponseLatestNews
import com.example.cricdekho.data.model.getMatchDetails.LiveMatchScoreResponse
import com.example.cricdekho.data.model.getPlayerInfo.ResponsePlayerInfo
import com.example.cricdekho.data.model.getPlayerStats.ResponseStats
import com.example.cricdekho.data.model.getPointsTable.ResponsePointsTable
import com.example.cricdekho.data.model.getSeriesBestEconomy.ResponseEconomyRate
import com.example.cricdekho.data.model.getSeriesHighestStrikeRate.ResponseStrikeRate
import com.example.cricdekho.data.model.getSeriesMostRuns.ResponseMostRuns
import com.example.cricdekho.data.model.getSeriesMostWickets.ResponseMostWickets
import com.example.cricdekho.data.model.getTeamInfo.ResponseTeamInfo
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("getCricketMainTabs")
    suspend fun getCricketTab(): ResponseHomeFeature

    @GET("getCricketMatches/{tournament_slug}")
    suspend fun getCricketMatches(@Path("tournament_slug") tournamentSlug: String): ResponseHomeMatch

    @GET("getPointsTable/{tournament_slug}")
    suspend fun getPointsTable(@Path("tournament_slug") tournamentSlug: String): ResponsePointsTable

    @GET("getResultMatches/{tournament_slug}")
    suspend fun getResultMatches(@Path("tournament_slug") tournamentSlug: String): Response<ResponseBody>

    @GET("getUpcomingMatches/{tournament_slug}")
    suspend fun getUpcomingMatches(@Path("tournament_slug") tournamentSlug: String): Response<ResponseBody>

    @GET("getCommentry")
    suspend fun getCommentry(
        @Query("id") eventSlug: String,
        @Query("timestamp") timestampOfComment: String
    ): Response<ResponseBody>

    @GET("getSeriesMostRuns/{tournament_slug}")
    suspend fun getSeriesMostRuns(@Path("tournament_slug") tournamentSlug: String): ResponseMostRuns

    @GET("getSeriesMostwickets/{tournament_slug}")
    suspend fun getSeriesMostWickets(@Path("tournament_slug") tournamentSlug: String): ResponseMostWickets

    @GET("getSeriesHighestStrikeRate/{tournament_slug}")
    suspend fun getSeriesHighestStrikeRate(@Path("tournament_slug") tournamentSlug: String): ResponseStrikeRate

    @GET("getSeriesBestEconomy/{tournament_slug}")
    suspend fun getSeriesBestEconomy(@Path("tournament_slug") tournamentSlug: String): ResponseEconomyRate

    @GET("getPlayerInfo/{player_slug}")
    suspend fun getPlayerInfo(@Path("player_slug") playerSlug: String): ResponsePlayerInfo

    @GET("getLiveCricketScore/{matchId}")
    suspend fun getLiveCricketScore(@Path("matchId") matchId : String) : LiveMatchScoreResponse

    @GET("getPlayerStats/{player_slug}")
    suspend fun getPlayerStats(@Path("player_slug") playerSlug: String): ResponseStats

    @GET("getTeamInfo/{tournament_slug}")
    suspend fun getTeamInfo(@Path("tournament_slug") tournamentSlug: String): ResponseTeamInfo

    @GET("getLatestNews")
    suspend fun getLatestNews(): ResponseLatestNews

    @GET("getCricketNews")
    suspend fun getCricketNews(@Query("id") link: String): ResponseCricketNews
}