package com.maasglobal.domain.usecase

import com.maasglobal.data.entities.DirectionResponses
import com.maasglobal.domain.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class DirectionUseCase @Inject constructor(
    private val repository: Repository
) {

    fun call(origin: String, destination: String, apiKey: String): Observable<DirectionResponses> {
        val response = repository.loadRoutes(origin, destination, apiKey)
        return response
    }
}