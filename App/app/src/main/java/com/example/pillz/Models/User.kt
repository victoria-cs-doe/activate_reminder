package com.example.pillz.Models

import com.google.gson.annotations.SerializedName

data class User(
        val email: String,
        val password: String,
        @SerializedName("firstname") val firstName: String,
        @SerializedName("lastname") val lastName: String
)