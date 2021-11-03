package com.spacex.placeholder

import com.spacex.database.entity.Launch
import retrofit2.Call
import retrofit2.http.GET

interface JSONPlaceHolderApi {
    @GET("/v2/launches/")
    fun getLaunches(): Call<List<Launch>>
}
