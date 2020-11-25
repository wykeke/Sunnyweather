package com.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(val name: String, val location: Location,
            @SerializedName("formatted_address") val address: String)
//@SerializedName注解使JSON字段和kotlin字段之间建立映射关系

data class Location(val lng: String, val lat: String)