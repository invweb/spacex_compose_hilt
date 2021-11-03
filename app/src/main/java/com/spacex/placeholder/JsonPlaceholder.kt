package com.spacex.placeholder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JsonPlaceholder {
    private const val BASE_URL = "https://api.spacexdata.com/v2/launches/"

    class Factory {
        companion object {
            fun create(): JSONPlaceHolderApi {

                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(JSONPlaceHolderApi::class.java)
            }
        }
    }
}
