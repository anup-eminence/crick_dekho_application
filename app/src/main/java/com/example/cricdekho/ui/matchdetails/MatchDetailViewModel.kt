package com.example.cricdekho.ui.matchdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricdekho.data.model.getMatchDetails.Commentary
import com.example.cricdekho.data.model.getMatchDetails.LiveMatchScoreResponse
import com.example.cricdekho.data.model.getMatchDetails.PlayerImages
import com.example.cricdekho.data.model.getMatchDetails.Squad
import com.example.cricdekho.data.model.getSeriesBestEconomy.ResponseEconomyRate
import com.example.cricdekho.data.model.getSeriesHighestStrikeRate.ResponseStrikeRate
import com.example.cricdekho.data.model.getSeriesMostRuns.ResponseMostRuns
import com.example.cricdekho.data.model.getSeriesMostWickets.ResponseMostWickets
import com.example.cricdekho.data.remote.ApiService
import com.example.cricdekho.data.remote.SocketManager
import com.example.cricdekho.data.repository.MatchDetailsRepository
import com.example.cricdekho.util.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchDetailViewModel : ViewModel() {
    private val socketManager = SocketManager
    private val _socketData = MutableLiveData<Any>()
    val observeLiveData: LiveData<Any> get() = _socketData

    var errorCaught = MutableLiveData<Boolean>()

    private val matchDetailsRepository = MatchDetailsRepository()

    private val _dataCommentary = MutableLiveData<List<Commentary>?>()
    val dataCommentary: MutableLiveData<List<Commentary>?> get() = _dataCommentary

    private val _mostRuns = MutableLiveData<ResponseMostRuns>()
    val mostRuns: LiveData<ResponseMostRuns> get() = _mostRuns

    private val _mostWickets = MutableLiveData<ResponseMostWickets>()
    val mostWickets: LiveData<ResponseMostWickets> get() = _mostWickets

    private val _strikeRate = MutableLiveData<ResponseStrikeRate>()
    val strikeRate: LiveData<ResponseStrikeRate> get() = _strikeRate

    private val _economyRate = MutableLiveData<ResponseEconomyRate>()
    val economyRate: LiveData<ResponseEconomyRate> get() = _economyRate

    private val _liveMatchScore = MutableLiveData<LiveMatchScoreResponse>()
    val liveMatchSore: LiveData<LiveMatchScoreResponse> get() = _liveMatchScore

    fun emitSocketEvent(matchId: String) {
        socketManager.emitEvent("LiveScore", matchId)
        socketManager.setEventListener("Score/$matchId") { args ->
            println(">>>>>>>>>>>>>>showddddbbb")
            viewModelScope.launch {
                if (args != null) {
                    try {
                        val arrayLength = java.lang.reflect.Array.getLength(args)
                        if (arrayLength > 0) {
                            val firstElement = java.lang.reflect.Array.get(args, 0)
                            Log.d(
                                "SocketData",
                                "Type of the first element: ${firstElement?.javaClass?.simpleName}"
                            )

                            if (firstElement is JSONObject) {
                                val jsonData = firstElement.toString()
                                Log.d("SocketData", "Received JSON: $jsonData")


                                val playerImages: MutableList<PlayerImages> = mutableListOf()
                                val jsonObject = JSONObject(jsonData)
                                if (jsonObject.has("player_images")) {
                                    val playerImagesObject =
                                        jsonObject.getJSONObject("player_images")

                                    for (playerName in playerImagesObject.keys()) {
                                        val imageUrl =
                                            playerImagesObject.getString(playerName.toString())
                                        playerImages.add(
                                            PlayerImages(
                                                playerName.toString(),
                                                imageUrl
                                            )
                                        )
                                    }
                                }
                                try {
                                    val squadResponse = Gson().fromJson(jsonData, Squad::class.java)
                                    squadResponse?.squad?.get(0)?.playerImages = playerImages
                                    _socketData.postValue(squadResponse)

                                } catch (e: Exception) {
                                    Log.e("SquadData", "Error parsing squad data: ${e.message}")
                                    errorCaught.postValue(true)
                                }

                            } else {
                                Log.e("SocketData", "Unexpected type for the first element")
                                errorCaught.postValue(true)
                            }
                        } else {
                            Log.e("SocketData", "Empty args array")
                            errorCaught.postValue(true)
                        }
                    } catch (e: Exception) {
                        Log.e("SocketData", "Error accessing array length: ${e.message}")
                        errorCaught.postValue(true)
                    }
                } else {
                    Log.e("SocketData", "Null args")
                    errorCaught.postValue(true)
                }
            }
        }
    }

    fun getCommentary(eventSlug: String, timestampOfComment: String) {
        viewModelScope.launch {
            try {
                val response = matchDetailsRepository.getCommentary(eventSlug, timestampOfComment)
                val responseBodyString = response.body()?.string()
                val gson = Gson()
                val responseCommentary = gson.fromJson(responseBodyString, Squad::class.java)
                Log.e("response", responseCommentary.toString())
                _dataCommentary.postValue(responseCommentary?.commentary)
            } catch (e: Exception) {
                Log.e("Exception_Commentary", e.message.toString())
            }
        }
    }

    fun getSeriesMostRuns(tournamentSlug: String) {
        viewModelScope.launch {
            try {
                _mostRuns.value = matchDetailsRepository.getSeriesMostRuns(tournamentSlug)
            } catch (e: Exception) {
                Log.e("Exception", "Exception ${e.message.toString()}")
            }
        }
    }

    fun getSeriesMostWickets(tournamentSlug: String) {
        viewModelScope.launch {
            try {
                _mostWickets.value = matchDetailsRepository.getSeriesMostWickets(tournamentSlug)
            } catch (e: Exception) {
                Log.e("Exception", "Exception ${e.message.toString()}")
            }
        }
    }

    fun getSeriesHighestStrikeRate(tournamentSlug: String) {
        viewModelScope.launch {
            try {
                _strikeRate.value =
                    matchDetailsRepository.getSeriesHighestStrikeRate(tournamentSlug)
            } catch (e: Exception) {
                Log.e("Exception", "Exception ${e.message.toString()}")
            }
        }
    }

    fun getSeriesBestEconomy(tournamentSlug: String) {
        viewModelScope.launch {
            try {
                _economyRate.value = matchDetailsRepository.getSeriesBestEconomy(tournamentSlug)
            } catch (e: Exception) {
                Log.e("Exception", "Exception ${e.message.toString()}")
            }
        }
    }

    fun getLiveMatchScore(matchId: String) {
        val playerImages: MutableList<PlayerImages> = mutableListOf()

        viewModelScope.launch {
            try {
             //   _liveMatchScore.value = matchDetailsRepository.getLiveMatchSore(matchId)
               val data  = matchDetailsRepository.getLiveScoreData(matchId)
                data.data.player_images?.forEach {
                    playerImages.add(PlayerImages(it.key,it.value))
                }
                if (data.data.squad.isNullOrEmpty().not()) {
                    data.data?.squad?.get(0)?.playerImages = playerImages
                }
                _liveMatchScore.value = data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}