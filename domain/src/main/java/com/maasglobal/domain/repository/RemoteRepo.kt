package com.maasglobal.domain.repository

import com.google.gson.JsonObject
import com.maasglobal.data.entities.DirectionResponses
import com.maasglobal.data.entities.GeoSearchResponses
import io.reactivex.Observable

interface RemoteRepo {

    fun loadNearbyArticle(gscoord: String): Observable<GeoSearchResponses>

    fun loadImageWiki(pageId: String): Observable<JsonObject>

    fun loadRoutes(origin: String, destination: String, apiKey: String): Observable<DirectionResponses>


}