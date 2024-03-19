package com.example.cricdekho.ui.match

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricdekho.data.model.getLiveMatches.CricketMatch
import com.example.cricdekho.data.model.getResultMatches.ResponseResult
import com.example.cricdekho.data.model.getResultMatches.ResponseResultMatch
import com.example.cricdekho.data.model.getUpcomingMatches.ResponseUpcoming
import com.example.cricdekho.data.model.getUpcomingMatches.ResponseUpcomingMatch
import com.example.cricdekho.data.model.getUpcomingMatches.Tab
import com.example.cricdekho.data.remote.SocketManager
import com.example.cricdekho.data.repository.MatchesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.json.JSONObject

class MatchesViewModel : ViewModel() {
    private val socketManager = SocketManager
    private val _socketData = MutableLiveData<Any>()
    val observeLiveData: LiveData<Any> get() = _socketData

    private val matchesRepository = MatchesRepository()

    private val _dataLiveTabs = MutableLiveData<MutableList<Tab>>()
    val dataLiveTabs: LiveData<MutableList<Tab>> get() = _dataLiveTabs

    private val _dataResultMatch = MutableLiveData<MutableList<ResponseResultMatch>>()
    val dataResultMatch: LiveData<MutableList<ResponseResultMatch>> get() = _dataResultMatch

    private val _dataResultTabs =
        MutableLiveData<MutableList<com.example.cricdekho.data.model.getResultMatches.Tab>>()
    val dataResultTab: LiveData<MutableList<com.example.cricdekho.data.model.getResultMatches.Tab>> get() = _dataResultTabs

    private val _dataUpcomingMatch = MutableLiveData<MutableList<ResponseUpcomingMatch>>()
    val dataUpcomingMatch: LiveData<MutableList<ResponseUpcomingMatch>> get() = _dataUpcomingMatch

    private val _dataUpcomingTabs = MutableLiveData<MutableList<Tab>>()
    val dataUpcomingTabs: LiveData<MutableList<Tab>> get() = _dataUpcomingTabs

    fun connectSocket(){
        socketManager.connect()
    }

    fun disConnectSocket() {
        socketManager.disconnect()
    }

    fun emitSocketEvent(id: String) {
        socketManager.connect()
        socketManager.emitEvent("Schedule", id)
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
                                val dataList = mutableListOf<CricketMatch>()

//                                val jsonObject = JSONObject(jsonData)
//                                val matchesObject = jsonObject.getJSONObject("matches").getJSONObject("matches")
//
//                                // Retrieve all keys from matchesObject
//                                val keysIterator = matchesObject.keys()

                                // Iterate through the keys
//                                while (keysIterator.hasNext()) {
//                                    val key = keysIterator.next().toString()
//                                    // Now you can use the 'key' as needed
//                                    Log.d("SocketData", "Key from 'matchesObject': $key")
//
//                                    // If you need the corresponding value:
//                                    val value = matchesObject.optString(key, "DefaultValue")
//                                    Log.d("SocketData", "Value for '$key': $value")
//
////                                    val responseLiveMatches = ResponseLiveMatches(listOf(value))
////                                    _socketData.postValue(responseLiveMatches)
////                                    Log.e("ResponseLiveMatches", "---$responseLiveMatches")
//
////                                    val dataObject = Data(value)
////                                    dataList.add(dataObject)
//                                }

                                val gson = Gson()
                                val jsonObject = JSONObject(jsonData)

                                val matchesObjectTab = jsonObject.getJSONObject("matches").getJSONArray("tabs")
                                val liveMatchesTabs: MutableList<Tab> = gson.fromJson(
                                    matchesObjectTab.toString(),
                                    object : TypeToken<MutableList<Tab>>() {}.type
                                )

                                val matchesObject =
                                    jsonObject.getJSONObject("matches").getJSONObject("matches")

                                val dynamicKeys = matchesObject.keys()

                                val allMatches = mutableListOf<CricketMatch>()

                                for (dynamicKey in dynamicKeys) {
                                    val matchesArray =
                                        matchesObject.getJSONArray((dynamicKey as Any).toString())

                                    val matches: List<CricketMatch> = gson.fromJson(
                                        matchesArray.toString(),
                                        object : TypeToken<List<CricketMatch>>() {}.type
                                    )
                                    allMatches.addAll(matches)
                                }

                                /* for (match in allMatches) {

                                     Log.d(
                                         "SocketData",
                                         "Score: ${match.t1_score}- ${match.t2_score}"
                                     )
                                     Log.d("SocketData", "event: ${match.event}")
                                     // ... print other fields ...

                                 }*/
                                dataList.addAll(allMatches)
                                Log.d("LiveMatches", "$dataList")
                                _socketData.postValue(dataList)
                                _dataLiveTabs.postValue(liveMatchesTabs)
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
    }

    fun getResultMatches(tournamentSlug: String) {
        viewModelScope.launch {
            try {
                val resultMatchList = mutableListOf<ResponseResultMatch>()
                val response = matchesRepository.getResultMatches(tournamentSlug)
                val gson = Gson()
                if (tournamentSlug == "") {
                    val responseBody = response.body()?.string()
                    val jsonObject = responseBody?.let { JSONObject(it) }

//                    Log.e("json_object", jsonObject.toString())

                    val matchesObjectTab = jsonObject?.getJSONObject("data")?.getJSONArray("tabs")
                    val resultMatchesTabs: MutableList<com.example.cricdekho.data.model.getResultMatches.Tab> =
                        gson.fromJson(
                            matchesObjectTab.toString(),
                            object :
                                TypeToken<MutableList<com.example.cricdekho.data.model.getResultMatches.Tab>>() {}.type
                        )

                    val dateKeys =
                        jsonObject?.getJSONObject("data")?.getJSONObject("matches")?.keys()
                    dateKeys?.let { keys ->
                        while (keys.hasNext()) {
                            val dateKey = keys.next()
                            val matchesObject =
                                jsonObject.getJSONObject("data").getJSONObject("matches")
                                    .getJSONObject(dateKey.toString())

                            val tournamentKeys = matchesObject.keys()
                            while (tournamentKeys.hasNext()) {
                                val tournamentKey = tournamentKeys.next().toString()
                                val matchesArray = matchesObject.getJSONArray(tournamentKey)

                                val matches: ArrayList<ResponseResultMatch> = gson.fromJson(
                                    matchesArray.toString(),
                                    object : TypeToken<List<ResponseResultMatch>>() {}.type
                                )
                                resultMatchList.addAll(matches)
                            }
                        }
                        _dataResultMatch.postValue(resultMatchList)
                        _dataResultTabs.postValue(resultMatchesTabs)
                    }
                } else {
                    val responseBodyString = response.body()?.string()
                    val responseResult =
                        gson.fromJson(responseBodyString, ResponseResult::class.java)
                    resultMatchList.addAll(responseResult.data)
                    _dataResultMatch.postValue(resultMatchList)
                }
            } catch (e: Exception) {
                Log.e("Exception_Result", e.message.toString())
            }
        }
    }

    fun getUpcomingMatches(tournamentSlug: String) {
        viewModelScope.launch {
            try {
                val matchList = mutableListOf<ResponseUpcomingMatch>()
                val response = matchesRepository.getUpcomingMatches(tournamentSlug)
                val responseBody = response.body()?.string()
                val gson = Gson()
                if (tournamentSlug == "") {
//                val jsonParser = Gson()
//                val jsonObject: JsonObject = jsonParser.fromJson(responseBody, JsonObject::class.java)
                    val jsonObject = responseBody?.let { JSONObject(it) }

//                    Log.e("json_object", jsonObject.toString())
//                val matchesObject = jsonObject?.getJSONObject("data")?.getJSONObject("matches")?.getJSONObject("16/02/2024")?.getJSONArray("KCC T20 Challenge Cup")
//                val matches: List<ResponseUpcomingMatch> = gson.fromJson(matchesObject.toString(),
//                    object : TypeToken<List<ResponseUpcomingMatch>>() {}.type
//                )

                    val matchesObjectTab = jsonObject?.getJSONObject("data")?.getJSONArray("tabs")
                    val matchesTabs: MutableList<Tab> = gson.fromJson(
                        matchesObjectTab.toString(),
                        object : TypeToken<MutableList<Tab>>() {}.type
                    )

                    val dateKeys =
                        jsonObject?.getJSONObject("data")?.getJSONObject("matches")?.keys()
                    dateKeys?.let { keys ->
                        while (keys.hasNext()) {
                            val dateKey = keys.next()
                            val matchesObject =
                                jsonObject.getJSONObject("data").getJSONObject("matches")
                                    .getJSONObject(dateKey.toString())

                            val tournamentKeys = matchesObject.keys()
                            while (tournamentKeys.hasNext()) {
                                val tournamentKey = tournamentKeys.next().toString()
                                val matchesArray = matchesObject.getJSONArray(tournamentKey)


                                val matches: ArrayList<ResponseUpcomingMatch> = gson.fromJson(
                                    matchesArray.toString(),
                                    object : TypeToken<List<ResponseUpcomingMatch>>() {}.type
                                )
                                matchList.addAll(matches)

//                            Log.d("DynamicKeys", "Date: $dateKey, Tournament: $tournamentKey, Matches: $matchesArray")
                            }
                        }
                        Log.e("matchList", matchList.toString())
                        Log.e("matchTabs", matchesTabs.toString())
                        _dataUpcomingMatch.postValue(matchList)
                        _dataUpcomingTabs.postValue(matchesTabs)
                    }
                } else {
                    val responseUpcoming = gson.fromJson(responseBody, ResponseUpcoming::class.java)
                    matchList.addAll(responseUpcoming.data)
                    _dataUpcomingMatch.postValue(matchList)
                }
            } catch (e: Exception) {
                Log.e("Exception_Upcoming", e.message.toString())
            }
        }
    }
}