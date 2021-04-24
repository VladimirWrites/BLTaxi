package com.vlad1m1r.bltaxi.remote

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface TaxiService {

    @GET("remote_data/taxi/v1/{language}/taxis.json")
    suspend fun taxis(@Path("language") language: String): List<Taxi>
}

fun getTaxiService(retrofit: Retrofit) = retrofit.create(TaxiService::class.java)!!
