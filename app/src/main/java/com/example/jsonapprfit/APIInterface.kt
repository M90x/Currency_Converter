package com.example.jsonapprfit

import retrofit2.http.GET

interface APIInterface {

    @GET("eur.json")

    fun getCurrency():retrofit2.Call<Currency>
}