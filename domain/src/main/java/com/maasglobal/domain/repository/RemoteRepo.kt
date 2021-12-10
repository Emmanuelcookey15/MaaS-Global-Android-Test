package com.maasglobal.domain.repository

import com.google.gson.JsonObject
import com.maasglobal.data.entities.DirectionResponses
import com.maasglobal.data.entities.GeoSearchResponses
import io.reactivex.Single

interface RemoteRepo {

    fun loadNearbyArticle(gscoord: String): Single<GeoSearchResponses>

    fun loadImageWiki(pageId: String): Single<JsonObject>

    fun loadRoutes(origin: String, destination: String, apiKey: String): Single<DirectionResponses>


}