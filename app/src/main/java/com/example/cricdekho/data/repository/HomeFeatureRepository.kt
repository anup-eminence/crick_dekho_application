package com.example.cricdekho.data.repository

import com.example.cricdekho.data.model.getCricketMainTabs.ResponseHomeFeature
import com.example.cricdekho.data.model.getCricketMatches.ResponseHomeMatch
import com.example.cricdekho.data.model.getCricketNews.ResponseCricketNews
import com.example.cricdekho.data.model.getLatestNews.ResponseLatestNews
import com.example.cricdekho.util.RetrofitClient

class HomeFeatureRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getCricketTab(): ResponseHomeFeature {
        return apiService.getCricketTab()
    }

    suspend fun getCricketMatches(tournamentSlug: String): ResponseHomeMatch {
        return apiService.getCricketMatches(tournamentSlug)
    }

    suspend fun getLatestNews(): ResponseLatestNews {
        return apiService.getLatestNews()
    }

    suspend fun getCricketNews(link: String): ResponseCricketNews{
        return apiService.getCricketNews(link)
    }
}