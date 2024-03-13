package com.example.cricdekho.ui.teaminfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricdekho.data.model.getTeamInfo.ResponseTeamInfo
import com.example.cricdekho.data.repository.TeamInfoRepository
import kotlinx.coroutines.launch

class TeamInfoViewModel : ViewModel() {
    private val teamInfoRepository = TeamInfoRepository()

    private val _teamInfo = MutableLiveData<ResponseTeamInfo>()
    val teamInfo: LiveData<ResponseTeamInfo> get() = _teamInfo

    fun getTeamInfo(tournamentSlug: String) {
        viewModelScope.launch {
            try {
                _teamInfo.value = teamInfoRepository.getTeamInfo(tournamentSlug)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}