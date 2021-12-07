package com.maasglobal.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DirectionResponses (

    @SerializedName("routes")
    @Expose
    var routes: List<RoutesItem>? = null,

    @SerializedName("geocoded_waypoints")
    @Expose
    var geocodedWaypoints: List<GeocodedWaypointsItem>? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null



)
