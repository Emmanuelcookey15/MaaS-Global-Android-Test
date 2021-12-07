package com.maasglobal.whimtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class ShareViewModel : ViewModel() {


    var latLngDevice = MutableLiveData<LatLng>()

    var latLngPOI = MutableLiveData<LatLng>()

    var idPOI = MutableLiveData<String>()

}