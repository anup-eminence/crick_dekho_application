package com.example.cricdekho.data.repository

import com.example.cricdekho.data.model.getSeriesNews.ResponseTeamNews
import com.example.cricdekho.data.model.getTeamInfo.ResponseTeamInfo
import com.example.cricdekho.util.RetrofitClient

class TeamInfoRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getTeamInfo(tournamentSlug: String): ResponseTeamInfo {
        return apiService.getTeamInfo(tournamentSlug)
    }

    suspend fun getSeriesNews(seriesKeedaSlug: String): ResponseTeamNews {
        return apiService.getSeriesNews(seriesKeedaSlug)
    }
}