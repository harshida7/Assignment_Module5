package com.example.retrofit_crud_ex

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient
{

    companion object
    {
        lateinit var retrofit: Retrofit

        var BASE_URL =  "https://compressible-approa.000webhostapp.com/serverimage/"

        fun getretrofit():Retrofit

        {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory
                    .create())
                .build()

            return retrofit
        }

    }
}