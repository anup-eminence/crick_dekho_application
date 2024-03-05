package com.example.cricdekho.data.repository

import com.example.cricdekho.data.model.getPlayerInfo.ResponsePlayerInfo
import com.example.cricdekho.util.RetrofitClient

class PlayerInfoRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getPlayerInfo(playerSlug: String): ResponsePlayerInfo {
        return apiService.getPlayerInfo(playerSlug)
    }
}