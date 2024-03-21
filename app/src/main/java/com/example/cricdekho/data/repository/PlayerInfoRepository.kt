package com.example.cricdekho.data.repository

import com.example.cricdekho.data.model.getPlayerInfo.ResponsePlayerInfo
import com.example.cricdekho.data.model.getPlayerStats.ResponseStats
import com.example.cricdekho.data.model.getTeamNews.ResponseTeamNews
import com.example.cricdekho.util.RetrofitClient

class PlayerInfoRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getPlayerInfo(playerSlug: String): ResponsePlayerInfo {
        return apiService.getPlayerInfo(playerSlug)
    }

    suspend fun getPlayerStats(playerSlug: String): ResponseStats {
        return apiService.getPlayerStats(playerSlug)
    }

    suspend fun getPlayerNews(playerSlug: String): ResponseTeamNews {
        return apiService.getPlayerNews(playerSlug)
    }
}