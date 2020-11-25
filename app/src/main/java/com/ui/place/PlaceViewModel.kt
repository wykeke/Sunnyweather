package com.ui.place

import android.util.Log
import androidx.lifecycle.*
import com.logic.Repository
import com.logic.model.Place
import com.sunnyweather.android.logic.dao.PlaceDao


class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        //Log.d("tag","test")
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavePlace() = PlaceDao.getSavedPlace()

    fun isPlaceSave() = PlaceDao.isPlaceSaved()
}