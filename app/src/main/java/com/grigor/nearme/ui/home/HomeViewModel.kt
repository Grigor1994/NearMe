package com.grigor.nearme.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grigor.nearme.data.network.sun.api.SunResponse
import com.grigor.nearme.data.repository.SunDataRepository
import org.koin.core.KoinComponent

class HomeViewModel(private val sunDataRepository: SunDataRepository) :
    ViewModel(),
    KoinComponent {

    var sunResponseLiveData = MutableLiveData<SunResponse>()

//    var response = { sunData: SunResponse -> sunResponseLiveData.value = sunData }

    fun getSunData(lat: String, lng: String, date: String) {
        sunDataRepository.getSunData(lat, lng, date) { response ->
            sunResponseLiveData.value = response
        }
    }

    private val _sunriseTime = MutableLiveData<String>()
    val sunriseTime: LiveData<String>
        get() = _sunriseTime

    private val _sunsetTime = MutableLiveData<String>()
    val sunsetTime: LiveData<String>
        get() = _sunsetTime

}

