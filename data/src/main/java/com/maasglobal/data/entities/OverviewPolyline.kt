package com.maasglobal.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OverviewPolyline (

    @SerializedName("points")
    @Expose
    var points: String? = null

)
