package com.maasglobal.domain.usecase

import com.google.gson.JsonObject
import com.maasglobal.domain.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class ArticleDetailsUseCase @Inject constructor(
    private val repository: Repository
) {


    fun call(query: String): Observable<JsonObject> {
        return repository.loadImageWiki(query)
    }

}