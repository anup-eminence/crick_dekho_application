package com.example.cricdekho.ui.playerdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricdekho.data.model.getPlayerInfo.ResponsePlayerInfo
import com.example.cricdekho.data.model.getPlayerStats.ResponseStats
import com.example.cricdekho.data.model.getTeamNews.ResponseTeamNews
import com.example.cricdekho.data.repository.PlayerInfoRepository
import kotlinx.coroutines.launch

class PlayerInfoViewModel : ViewModel() {
    private val playerInfoRepository = PlayerInfoRepository()

    private val _playerInfo = MutableLiveData<ResponsePlayerInfo>()
    val playerInfo: LiveData<ResponsePlayerInfo> get() = _playerInfo

    private val _playerStats = MutableLiveData<ResponseStats>()
    val playerStats: LiveData<ResponseStats> get() = _playerStats

    private val _playerNews = MutableLiveData<ResponseTeamNews>()
    val playerNews: LiveData<ResponseTeamNews> get() = _playerNews

    fun getPlayerInfo(playerSlug: String) {
        viewModelScope.launch {
            try {
                _playerInfo.value = playerInfoRepository.getPlayerInfo(playerSlug)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getPlayerStats(playerSlug: String) {
        viewModelScope.launch {
            try {
                _playerStats.value = playerInfoRepository.getPlayerStats(playerSlug)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getPlayerNews(playerSlug: String) {
        viewModelScope.launch {
            try {
                _playerNews.value = playerInfoRepository.getPlayerNews(playerSlug)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}