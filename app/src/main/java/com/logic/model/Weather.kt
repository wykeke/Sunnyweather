package com.logic.model

import com.sunnyweather.android.logic.model.RealtimeResponse

data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)