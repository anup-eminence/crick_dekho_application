package com.example.cricdekho.data.repository

import com.example.cricdekho.data.model.getPointsTable.ResponsePointsTable
import com.example.cricdekho.util.RetrofitClient

class PointsTableRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getPointsTable(tournamentSlug: String): ResponsePointsTable {
        return apiService.getPointsTable(tournamentSlug)
    }
}