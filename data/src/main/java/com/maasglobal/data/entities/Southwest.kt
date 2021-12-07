package com.maasglobal.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Southwest {
    @SerializedName("lng")
    @Expose
    var lng = 0.0

    @SerializedName("lat")
    @Expose
    var lat = 0.0
}
