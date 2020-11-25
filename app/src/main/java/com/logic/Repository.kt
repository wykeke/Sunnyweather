package com.logic

import androidx.lifecycle.liveData
import com.logic.model.Place
import com.logic.model.Weather
import com.logic.network.SunnyWeatherNetwork
import com.sunnyweather.android.logic.dao.PlaceDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {

    //搜索
    fun searchPlaces(query: String) = fire(Dispatchers.IO){
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    //刷新天气信息
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO){

        //coroutineScope函数创建协程作用域
        coroutineScope {
            //并发执行获取实时天气信息和未来天气信息，使用async函数保证只有在两个网络请求都成功响应之后才会进一步执行程序
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }

            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }

    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavePlace() = PlaceDao.getSavedPlace()

    fun isPlaceSave() = PlaceDao.isPlaceSaved()

    private fun <T> fire(context: CoroutineContext, block: suspend() -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            }catch (e: Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }

}