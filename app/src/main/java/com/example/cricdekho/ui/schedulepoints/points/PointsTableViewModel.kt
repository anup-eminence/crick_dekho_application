package com.example.cricdekho.ui.schedulepoints.points

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricdekho.data.model.getPointsTable.Group
import com.example.cricdekho.data.repository.PointsTableRepository
import kotlinx.coroutines.launch

class PointsTableViewModel : ViewModel() {
    private val pointsTableRepository = PointsTableRepository()

    private val _dataPointsTable = MutableLiveData<List<Group>>()
    val dataPointsTable: LiveData<List<Group>> get() = _dataPointsTable

    fun getPointsTable(tournamentSlug: String) {
        viewModelScope.launch {
            try {
                val response = pointsTableRepository.getPointsTable(tournamentSlug)
                _dataPointsTable.value = response.data.table[0].table[0].group
            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
            }
        }
    }
}