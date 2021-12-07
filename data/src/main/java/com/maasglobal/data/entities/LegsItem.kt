package com.maasglobal.data.entities

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


class LegsItem {
    @SerializedName("duration")
    @Expose
    var duration: Duration? = null

    @SerializedName("start_location")
    @Expose
    var startLocation: StartLocation? = null

    @SerializedName("distance")
    @Expose
    var distance: Distance? = null

    @SerializedName("start_address")
    @Expose
    var startAddress: String? = null

    @SerializedName("end_location")
    @Expose
    var endLocation: EndLocation? = null

    @SerializedName("end_address")
    @Expose
    var endAddress: String? = null

    @SerializedName("via_waypoint")
    @Expose
    var viaWaypoint: List<Any>? = null

    @SerializedName("steps")
    @Expose
    var steps: List<StepsItem>? = null

    @SerializedName("traffic_speed_entry")
    @Expose
    var trafficSpeedEntry: List<Any>? = null
}
