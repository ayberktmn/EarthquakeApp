package com.ayberk.earthquakeapp.retrofit

import androidx.lifecycle.MutableLiveData
import com.ayberk.earthquakeapp.model.Earthquake
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val retrofitInstance: RetrofitInstance) {
    fun getEarthquake(liveData: MutableLiveData<Earthquake>){
        retrofitInstance.getEarthquake().enqueue(object : retrofit2.Callback<Earthquake>{
            override fun onResponse(call: Call<Earthquake>, response: Response<Earthquake>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Earthquake>, t: Throwable) {
               liveData.postValue(null)
            }
        })
    }
}