package com.example.pillz.Network

import com.example.pillz.Models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Endpoints {
    companion object {
        fun createRetrofitInstance(): Endpoints {
            val restApi = RestAPI()
            return (restApi.getRetrofit().create(Endpoints::class.java))
        }
    }

    @GET("patients")
    fun getUsers(): Call<List<User>>

    @POST("patients")
    fun addUser(@Body user: User) : Call<User>
}