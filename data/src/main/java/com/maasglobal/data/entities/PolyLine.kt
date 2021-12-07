package com.maasglobal.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PolyLine {
    @SerializedName("points")
    @Expose
    var points: kotlin.String? = null

}