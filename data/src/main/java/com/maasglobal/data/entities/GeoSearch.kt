package com.maasglobal.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GeoSearch(
    @SerializedName("pageid")
    @Expose
    var pageid: Int? = null,
    @SerializedName("ns")
    @Expose
    var ns: Int? = null,
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("dist")
    @Expose
    var dist: Double? = null,
    @SerializedName("lon")
    @Expose
    var lon: Double? = null,
    @SerializedName("lat")
    @Expose
    var lat: Double? = null,
    @SerializedName("primary")
    @Expose
    var primary: String? = null
)
