package com.maasglobal.data.networking

import com.google.gson.JsonObject
import com.maasglobal.data.entities.GeoSearchResponses
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WikipediaService {

    companion object {
        const val BASE_URL = "https://en.wikipedia.org/w/"
    }

    @GET("api.php?action=query&list=geosearch&gsradius=10000&gslimit=50&format=json")
    fun getNearbyArticles(
        @Query("gscoord") gscoord: String
    ): Observable<GeoSearchResponses>

    @GET("api.php?action=query&prop=info|description|images&format=json")
    fun getDetailOfArticles(
        @Query("pageids") pageids: String
    ): Observable<JsonObject>



}
