package com.maasglobal.domain.repository

import com.google.gson.JsonObject
import com.maasglobal.data.entities.DirectionResponses
import com.maasglobal.data.entities.GeoSearchResponses
import com.maasglobal.data.networking.DirectionService
import com.maasglobal.data.networking.WikipediaService
import io.reactivex.Single
import javax.inject.Inject

open class Repository @Inject constructor(
    private val wikipediaService: WikipediaService,
    private val directionService: DirectionService
): RemoteRepo {


    override fun loadImageWiki(pageId: String): Single<JsonObject> {
        return wikipediaService.getDetailOfArticles(pageId)
    }


    override fun loadNearbyArticle(gscoord: String): Single<GeoSearchResponses> {
        return wikipediaService.getNearbyArticles(gscoord)
    }

    override fun loadRoutes(origin: String, destination: String, apiKey: String): Single<DirectionResponses>{
        return directionService.getDirection(origin, destination, apiKey)
    }


}