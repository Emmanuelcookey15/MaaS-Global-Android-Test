package com.maasglobal.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class GeocodedWaypointsItem (
    @SerializedName("types")
    @Expose
    var types: List<String?>? = null,

    @SerializedName("geocoder_status")
    @Expose
    var geocoderStatus: String? = null,

    @SerializedName("place_id")
    @Expose
    var placeId: String? = null
)
