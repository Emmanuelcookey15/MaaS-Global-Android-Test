package com.maasglobal.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class RoutesItem (

    @SerializedName("summary")
    @Expose
    var summary: String? = null,

    @SerializedName("copyrights")
    @Expose
    var copyrights: String? = null,

    @SerializedName("legs")
    @Expose
    var legs: List<LegsItem>? = null,

    @SerializedName("warnings")
    @Expose
    var warnings: List<Any>? = null,

    @SerializedName("bounds")
    @Expose
    var bounds: Bounds? = null,

    @SerializedName("overview_polyline")
    @Expose
    var overviewPolyline: OverviewPolyline? = null,

    @SerializedName("waypoint_order")
    @Expose
    var waypointOrder: List<Any>? = null


)
