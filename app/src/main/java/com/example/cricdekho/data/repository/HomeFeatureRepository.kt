package com.example.cricdekho.data.repository

import com.example.cricdekho.data.model.getCricketMainTabs.ResponseHomeFeature
import com.example.cricdekho.data.model.getCricketMatches.ResponseHomeMatch
import com.example.cricdekho.util.RetrofitClient

class HomeFeatureRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getCricketTab(): ResponseHomeFeature {
        return apiService.getCricketTab()
    }

    suspend fun getCricketMatches(tournamentSlug: String): ResponseHomeMatch {
        return apiService.getCricketMatches(tournamentSlug)
    }
}