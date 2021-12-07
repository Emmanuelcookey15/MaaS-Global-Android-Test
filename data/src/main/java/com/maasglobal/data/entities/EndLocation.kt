package com.maasglobal.data.entities

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


class EndLocation {
    @SerializedName("lng")
    @Expose
    var lng: Double = 0.0

    @SerializedName("lat")
    @Expose
    var lat: Double = 0.0
}
