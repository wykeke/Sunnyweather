package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {

    //全局context
    companion object {

        const val TOKEN = "RifV7yJvB3zvo9zn" // 填入你申请到的令牌值

        @SuppressLint("StaticFieldLeak")
        //使用注解忽略警告提示
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //调用getapplicationContext()方法将返回值赋给context变量
    }

}