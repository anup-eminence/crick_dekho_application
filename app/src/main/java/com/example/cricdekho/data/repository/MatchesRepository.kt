package com.example.cricdekho.data.repository

import com.example.cricdekho.util.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Response

class MatchesRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getResultMatches(tournamentSlug: String): Response<ResponseBody> {
        return apiService.getResultMatches(tournamentSlug)
    }

    suspend fun getUpcomingMatches(tournamentSlug: String): Response<ResponseBody> {
        return apiService.getUpcomingMatches(tournamentSlug)
    }
}