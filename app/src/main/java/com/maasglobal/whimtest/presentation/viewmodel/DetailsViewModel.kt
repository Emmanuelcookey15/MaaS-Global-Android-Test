package com.maasglobal.whimtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.maasglobal.data.entities.GeoDetailResponse
import com.maasglobal.data.entities.ImageWiki
import com.maasglobal.domain.usecase.ArticleDetailsUseCase
import com.maasglobal.whimtest.presentation.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val articleUseCase: ArticleDetailsUseCase
) : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private var _geoDetailData = MutableLiveData<State<GeoDetailResponse>>()

    var geoDetailData: LiveData<State<GeoDetailResponse>> = _geoDetailData

    fun getDetailOfPOI(pageId: String?) {
        _geoDetailData.postValue(State.loading())
        articleUseCase.call(pageId!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<JsonObject>(){
                override fun onNext(t: JsonObject) {
                    val data = toGeoDetailResponse(t, pageId)
                    _geoDetailData.postValue(State.success(data))
                }

                override fun onError(e: Throwable) {
                    _geoDetailData.postValue(State.error(message = "Error fetching Articles Details"))
                    e.printStackTrace()
                }

                override fun onComplete() {

                }

            }).let {
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


    override fun onCleared() {
        compositeDisposable.clear()
    }

}