package com.maasglobal.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StepsItem {

    @SerializedName("duration")
    @Expose
    var duration: Duration? = null

    @SerializedName("start_location")
    @Expose
    var startLocation: StartLocation? = null

    @SerializedName("distance")
    @Expose
    var distance: Distance? = null

    @SerializedName("travel_mode")
    @Expose
    var travelMode: String? = null

    @SerializedName("html_instructions")
    @Expose
    var htmlInstructions: String? = null

    @SerializedName("end_location")
    @Expose
    var endLocation: EndLocation? = null

    @SerializedName("maneuver")
    @Expose
    var maneuver: String? = null

    @SerializedName("polyline")
    @Expose
    var polyline: PolyLine? = null
}
