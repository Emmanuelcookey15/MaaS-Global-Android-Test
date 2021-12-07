package com.maasglobal.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeoSearchResponses(

    @SerializedName("batchcomplete")
    @Expose
    var batchcomplete: String? = null,
    @SerializedName("query")
    @Expose
    var query: TheQuery? = null


)


data class TheQuery(
    @SerializedName("geosearch")
    @Expose
    var geoSearches: List<GeoSearch>? = null
)
