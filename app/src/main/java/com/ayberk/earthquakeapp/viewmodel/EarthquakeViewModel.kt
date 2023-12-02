package com.ayberk.earthquakeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayberk.earthquakeapp.model.Earthquake
import com.ayberk.earthquakeapp.retrofit.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EarthquakeViewModel @Inject constructor(private val repository: RetrofitRepository) : ViewModel() {

    var EarthquakeList : MutableLiveData<Earthquake>

    init {
        EarthquakeList = MutableLiveData()
    }

    fun getEarthquake() : MutableLiveData<Earthquake>{
        return EarthquakeList
    }

    fun LoadEarthquake(){
        repository.getEarthquake(EarthquakeList)
    }
}