package com.maasglobal.whimtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maasglobal.data.entities.GeoSearchResponses
import com.maasglobal.domain.usecase.NearbyArticleUseCase
import com.maasglobal.whimtest.presentation.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val nearbyArticleUseCase: NearbyArticleUseCase
) : ViewModel(){

    protected val compositeDisposable = CompositeDisposable()

    private var _listOfGeoSearchData = MutableLiveData<State<GeoSearchResponses>>()

    val listOfGeoSearchData: LiveData<State<GeoSearchResponses>> = _listOfGeoSearchData

    fun loadArticlesNearby(coord: String) {
        nearbyArticleUseCase.call(coord)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<GeoSearchResponses>(){
                override fun onNext(t: GeoSearchResponses) {
                    _listOfGeoSearchData.postValue(State.success(t))


                }

                override fun onError(e: Throwable) {
                    _listOfGeoSearchData.postValue(State.error(message = "Error fetching Wikipedia Articles Data"))
                    e.printStackTrace()
                }

                override fun onComplete() {

                }


            }).let {
                compositeDisposable.add(it)
            }
    }



    override fun onCleared() {
        compositeDisposable.clear()
    }


}