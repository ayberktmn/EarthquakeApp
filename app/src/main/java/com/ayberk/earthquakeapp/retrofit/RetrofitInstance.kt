package com.ayberk.earthquakeapp.retrofit

import com.ayberk.earthquakeapp.model.Earthquake
import retrofit2.http.GET

interface RetrofitInstance {

    @GET("deprem/kandilli/live")
    fun getEarthquake() : retrofit2.Call<Earthquake>
}