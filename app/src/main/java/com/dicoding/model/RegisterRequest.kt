package com.dicoding.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    @SerializedName("username")
    @Expose
    val username: String,

    @SerializedName("password")
    @Expose
    val password: String
)