package com.maasglobal.whimtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maasglobal.data.entities.GeoSearchResponses
import com.maasglobal.data.entities.RxSingleSchedulers
import com.maasglobal.domain.usecase.NearbyArticleUseCase
import com.maasglobal.whimtest.presentation.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val nearbyArticleUseCase: NearbyArticleUseCase,
    private val rxSingleSchedulers: RxSingleSchedulers
) : ViewModel(){

    protected val compositeDisposable = CompositeDisposable()

    private var _listOfGeoSearchData = MutableLiveData<State<GeoSearchResponses>>()

    val listOfGeoSearchData: LiveData<State<GeoSearchResponses>> = _listOfGeoSearchData

    fun loadArticlesNearby(coord: String) {
        _listOfGeoSearchData.postValue(State.loading())
        nearbyArticleUseCase.call(coord)
            .doOnEvent { data, throwable ->  onLoading() }
            .compose(rxSingleSchedulers.applySchedulers())
            .subscribe(this::onSuccess,
                this::onError).let {
                compositeDisposable.add(it)
            }
    }


    private fun onSuccess(t: GeoSearchResponses) {
        _listOfGeoSearchData.postValue(State.success(t))
    }

    private fun onError(e: Throwable) {
        _listOfGeoSearchData.postValue(State.error(message = "Error fetching Wikipedia Articles Data"))
        e.printStackTrace()
    }

    private fun onLoading() {
        _listOfGeoSearchData.postValue(State.loading())
    }



    override fun onCleared() {
        compositeDisposable.clear()
    }


}