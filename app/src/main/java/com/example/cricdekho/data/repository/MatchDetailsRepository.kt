package com.example.cricdekho.data.repository

import com.example.cricdekho.data.model.getSeriesBestEconomy.ResponseEconomyRate
import com.example.cricdekho.data.model.getSeriesHighestStrikeRate.ResponseStrikeRate
import com.example.cricdekho.data.model.getSeriesMostRuns.ResponseMostRuns
import com.example.cricdekho.data.model.getSeriesMostWickets.ResponseMostWickets
import com.example.cricdekho.util.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Response

class MatchDetailsRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getCommentary(
        eventSlug: String,
        timestampOfComment: String
    ): Response<ResponseBody> {
        return apiService.getCommentry(eventSlug, timestampOfComment)
    }

    suspend fun getSeriesMostRuns(tournamentSlug: String): ResponseMostRuns {
        return apiService.getSeriesMostRuns(tournamentSlug)
    }

    suspend fun getSeriesMostWickets(tournamentSlug: String): ResponseMostWickets {
        return apiService.getSeriesMostWickets(tournamentSlug)
    }

    suspend fun getSeriesHighestStrikeRate(tournamentSlug: String): ResponseStrikeRate {
        return apiService.getSeriesHighestStrikeRate(tournamentSlug)
    }

    suspend fun getSeriesBestEconomy(tournamentSlug: String): ResponseEconomyRate {
        return apiService.getSeriesBestEconomy(tournamentSlug)
    }
}