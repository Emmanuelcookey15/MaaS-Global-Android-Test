package com.maasglobal.whimtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maasglobal.data.entities.DirectionResponses
import com.maasglobal.data.entities.RxSingleSchedulers
import com.maasglobal.domain.usecase.DirectionUseCase
import com.maasglobal.whimtest.presentation.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    private val directionUseCase: DirectionUseCase,
    private val rxSingleSchedulers: RxSingleSchedulers
) : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()


    private var _pointData = MutableLiveData<State<DirectionResponses?>>()

    val pointData: LiveData<State<DirectionResponses?>> = _pointData


    fun getRoutes(fromDeviceLatLng: String, toPoiLatLng: String, str: String) {
        _pointData.postValue(State.loading())
        directionUseCase.call(fromDeviceLatLng, toPoiLatLng, str)
            .doOnEvent { t, e ->  onLoading() }
            .compose(rxSingleSchedulers.applySchedulers())
            .subscribe(this::onSuccess,
                this::onError).let {
                compositeDisposable.add(it)
            }
    }


    private fun onSuccess(t: DirectionResponses) {
        _pointData.postValue(State.success(t))
    }

    private fun onError(e: Throwable) {
        _pointData.postValue(State.error(message = "Error fetching Route Details"))
        e.printStackTrace()
    }

    private fun onLoading() {
        _pointData.postValue(State.loading())
    }


    override fun onCleared() {
        compositeDisposable.clear()
    }
}