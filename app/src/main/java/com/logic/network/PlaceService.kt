package com.logic.network

import com.logic.model.PlaceResponse
import com.sunnyweather.android.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    //在searchPlaces方法的上面声明了一个@GET注解，当调用searchPlaces方法时，
    //Retrofit会自动发起一条GET请求，去访问@GET注解中配置的地址
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
    //searchPlaces的返回值被声明成Call<PlaceResponse>，这样Retrofit会将服务器返回的JSON数据自动解析成PlaceResponse对象
}