package com.example.pillz.Network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestAPI {

    private lateinit var retrofit: Retrofit

    init {
        val URL = "https://pillz-31cd.restdb.io/rest/"
        val API_KEY = "5cbf9f2d809b8306a3d6c896"
        val httpClient = OkHttpClient.Builder().let {
            it.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder().addHeader(
                            "x-apikey", API_KEY
                    ).build()
                    return chain.proceed(request)
                }
            })
        }

        this.retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .client(httpClient.build())
                .build()
    }

    fun getRetrofit(): Retrofit {
        return this.retrofit
    }
}