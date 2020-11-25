package com.logic.network

import com.sunnyweather.android.logic.network.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()


    //创建一个PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create(PlaceService::class.java)

    //定义searchPlaces函数并调用searchPlaces()方法以发起搜索城市数据请求
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()


    private suspend fun <T> Call<T>.await(): T {
        //suspend挂起函数关键字
        //await()是一个挂起函数，给它声明一个泛型T，并将await()函数定义成call<T>的扩展函数

        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                //直接调用enqueue()方法让Retrofit发起网络请求
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}