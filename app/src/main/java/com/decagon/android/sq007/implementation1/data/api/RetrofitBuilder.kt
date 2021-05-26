package com.decagon.android.sq007.implementation1.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build() //Doesn't require the adapter
    }

    val apiService: PostApiInterface = getRetrofit().create(PostApiInterface::class.java)
}