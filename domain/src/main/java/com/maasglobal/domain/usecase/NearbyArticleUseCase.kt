package com.maasglobal.domain.usecase

import com.maasglobal.data.entities.GeoSearchResponses
import com.maasglobal.domain.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class NearbyArticleUseCase @Inject constructor(
    private val repository: Repository
) {

     fun call(gscoord: String): Observable<GeoSearchResponses> {

        return repository.loadNearbyArticle(gscoord)
    }
}