package com.example.cricdekho.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricdekho.data.model.getCricketMainTabs.ResponseHomeFeature
import com.example.cricdekho.data.model.getCricketMatches.Data
import com.example.cricdekho.data.model.getCricketMatches.ResponseHomeMatch
import com.example.cricdekho.data.model.getCricketNews.ResponseCricketNews
import com.example.cricdekho.data.model.getHomeNews.ResponseHomeNews
import com.example.cricdekho.data.model.getHomeSidebarNews.ResponseLatestPopularNews
import com.example.cricdekho.data.remote.SocketManager
import com.example.cricdekho.data.repository.HomeFeatureRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.json.JSONObject

class HomeFeatureViewModel : ViewModel() {
    private val socketManager = SocketManager
    private val _socketData = MutableLiveData<Any>()
    val observeLiveData: LiveData<Any> get() = _socketData

    private val homeFeatureRepository = HomeFeatureRepository()

    private val _dataTab = MutableLiveData<ResponseHomeFeature>()
    val dataTab: LiveData<ResponseHomeFeature> get() = _dataTab

    private val _dataMatch = MutableLiveData<ResponseHomeMatch>()
    val dataMatch: LiveData<ResponseHomeMatch> get() = _dataMatch

    private val _dataHomeNews = MutableLiveData<ResponseHomeNews>()
    val dataHomeNews: LiveData<ResponseHomeNews> get() = _dataHomeNews

    private val _dataCricketNews = MutableLiveData<ResponseCricketNews>()
    val dataCricketNews: LiveData<ResponseCricketNews> get() = _dataCricketNews

    private val _dataLatestPopularNews = MutableLiveData<ResponseLatestPopularNews>()
    val dataLatestPopularNews: LiveData<ResponseLatestPopularNews> get() = _dataLatestPopularNews

    fun loadDataMatch(tournamentSlug: String) {
        viewModelScope.launch {
            try {
                _dataTab.value = homeFeatureRepository.getCricketTab()
                _dataMatch.value = homeFeatureRepository.getCricketMatches(tournamentSlug)
                _dataHomeNews.value = homeFeatureRepository.getHomeNews()
                _dataLatestPopularNews.value = homeFeatureRepository.getLatestPopularNews()
            } catch (e: Exception) {
                Log.e("Exception", "Exception ${e.message.toString()}")
            }
        }
    }

    fun emitSocketEvent(id: String) {
        socketManager.emitEvent("Competition", id)
        socketManager.setEventListener("Matches/$id") { args ->
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

                            try {
                                val jsonObject = JSONObject(jsonData)
                                val dataList = Gson().fromJson<List<Data>>(
                                    jsonObject.optJSONArray("matches")?.toString(),
                                    object : TypeToken<List<Data>>() {}.type
                                )

                                val responseHomeMatch = ResponseHomeMatch(dataList, "")
                                _socketData.postValue(responseHomeMatch)
                            } catch (e: Exception) {
                                Log.e("SocketData", "Error parsing JSON: ${e.message}")
                            }
                        } else {
                            Log.e("SocketData", "Unexpected type for the first element")
                        }
                    } else {
                        Log.e("SocketData", "Empty args array")
                    }
                } catch (e: Exception) {
                    Log.e("SocketData", "Error accessing array length: ${e.message}")
                }
            } else {
                Log.e("SocketData", "Null args")
            }
        }


//        socketManager.setEventListener("Matches/$id") { args ->
//            val dataArray = (args as? Array<*>)
//            val data = dataArray?.getOrNull(0) ?: "Empty data"
//            _socketData.postValue(data as Data)
//        }


//        socketManager.setEventListener("Matches/$id") { args ->
//            Log.d("SocketData", "Received args: $args")
//            val jsonData = (args as? Array<*>)?.getOrNull(0) as? String
//            Log.d("SocketData", "Received JSON: $jsonData")
//
//            jsonData?.let {
//                try {
//                    val responseHomeMatch = Gson().fromJson(it, ResponseHomeMatch::class.java)
//                    _socketData.postValue(responseHomeMatch)
//                } catch (e: Exception) {
//                    Log.e("SocketData", "Error parsing JSON: ${e.message}")
//                }
//            }
//        }
    }

    fun getCricketNews(link: String) {
        viewModelScope.launch {
            try {
                _dataCricketNews.value = homeFeatureRepository.getCricketNews(link)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}