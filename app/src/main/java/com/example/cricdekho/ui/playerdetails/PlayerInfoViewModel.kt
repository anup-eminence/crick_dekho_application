package com.example.cricdekho.ui.playerdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricdekho.data.model.getPlayerInfo.ResponsePlayerInfo
import com.example.cricdekho.data.repository.PlayerInfoRepository
import kotlinx.coroutines.launch

class PlayerInfoViewModel : ViewModel() {
    private val playerInfoRepository = PlayerInfoRepository()

    private val _playerInfo = MutableLiveData<ResponsePlayerInfo>()
    val playerInfo: LiveData<ResponsePlayerInfo> get() = _playerInfo

    fun getPlayerInfo(playerSlug: String) {
        viewModelScope.launch {
            try {
                _playerInfo.value = playerInfoRepository.getPlayerInfo(playerSlug)
            } catch (e: Exception) {
                Log.e("Exception", "Exception ${e.message.toString()}")
            }
        }
    }
}