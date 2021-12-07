package com.maasglobal.data.entities

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


class Duration {
    @SerializedName("text")
    @Expose
    var text: String? = null

    @SerializedName("value")
    @Expose
    var value = 0
}