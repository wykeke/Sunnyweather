package com.ui.weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.logic.Repository

class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<com.logic.model.Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
       // Log.d("tag","test")
        Repository.refreshWeather(location.lng,location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = com.logic.model.Location(lng, lat)
    }

}