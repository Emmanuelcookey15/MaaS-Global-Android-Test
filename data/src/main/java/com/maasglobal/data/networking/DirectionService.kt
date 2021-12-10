package com.maasglobal.data.networking

import com.maasglobal.data.entities.DirectionResponses
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionService {

    companion object {
        const val BASE_URL = "https://maps.googleapis.com/"
    }



    @GET("maps/api/directions/json")
    fun getDirection(@Query("origin") origin: String,
                     @Query("destination") destination: String,
                     @Query("key") apiKey: String): Single<DirectionResponses>
}