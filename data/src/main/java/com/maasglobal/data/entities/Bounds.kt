package com.maasglobal.data.entities

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


class Bounds {
    @SerializedName("southwest")
    @Expose
    var southwest: Southwest? = null

    @SerializedName("northeast")
    @Expose
    var northeast: Northeast? = null
}
