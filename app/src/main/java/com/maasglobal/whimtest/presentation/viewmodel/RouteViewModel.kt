package com.maasglobal.whimtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maasglobal.data.entities.DirectionResponses
import com.maasglobal.domain.usecase.DirectionUseCase
import com.maasglobal.whimtest.presentation.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    private val directionUseCase: DirectionUseCase
) : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()


    private var _pointData = MutableLiveData<State<DirectionResponses?>>()

    val pointData: LiveData<State<DirectionResponses?>> = _pointData


    fun getRoutes(fromDeviceLatLng: String, toPoiLatLng: String, str: String) {
        _pointData.postValue(State.loading())
        directionUseCase.call(fromDeviceLatLng, toPoiLatLng, str)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<DirectionResponses>(){
                override fun onNext(t: DirectionResponses) {
                    _pointData.postValue(State.success(t))
                }

                override fun onError(e: Throwable) {
                    _pointData.postValue(State.error(message = "Error fetching Articles Details"))

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