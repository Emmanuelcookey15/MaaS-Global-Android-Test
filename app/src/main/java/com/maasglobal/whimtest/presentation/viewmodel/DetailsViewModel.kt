package com.maasglobal.whimtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.maasglobal.data.entities.GeoDetailResponse
import com.maasglobal.data.entities.ImageWiki
import com.maasglobal.data.entities.RxSingleSchedulers
import com.maasglobal.domain.usecase.ArticleDetailsUseCase
import com.maasglobal.whimtest.presentation.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val articleUseCase: ArticleDetailsUseCase,
    private val rxSingleSchedulers: RxSingleSchedulers
) : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private var _geoDetailData = MutableLiveData<State<JsonObject>>()

    var geoDetailData: LiveData<State<JsonObject>> = _geoDetailData

    fun getDetailOfPOI(pageId: String?) {
        _geoDetailData.postValue(State.loading())
        articleUseCase.call(pageId!!)
            .doOnEvent { t, e ->  onLoading() }
            .compose(rxSingleSchedulers.applySchedulers())
            .subscribe(this::onSuccess,
                this::onError).let {
                compositeDisposable.add(it)
            }
    }


    fun toGeoDetailResponse(jsonObject: JsonObject, pageId: String): GeoDetailResponse {

        val data = jsonObject
            .getAsJsonObject("query")
            .getAsJsonObject("pages")
            .getAsJsonObject(pageId)
        val list = arrayListOf<ImageWiki>()
        if (data.getAsJsonArray("images") != null){
            data.getAsJsonArray("images").forEach {
                list.add(
                    ImageWiki(
                        it.asJsonObject.get("ns").asInt,
                        it.asJsonObject.get("title").asString
                    )
                )
            }
        }


        return GeoDetailResponse(
            data.get("pageid").asInt,
            data.get("title").asString,
            data.get("contentmodel").asString,
            list
        )
    }


    private fun onSuccess(t: JsonObject) {
        _geoDetailData.postValue(State.success(t))
    }

    private fun onError(e: Throwable) {
        _geoDetailData.postValue(State.error(message = "Error fetching Articles Details"))
        e.printStackTrace()
    }

    private fun onLoading() {
        _geoDetailData.postValue(State.loading())
    }


    override fun onCleared() {
        compositeDisposable.clear()
    }

}