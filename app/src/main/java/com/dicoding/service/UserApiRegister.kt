package com.dicoding.service

import com.dicoding.model.RegisterRequest
import com.dicoding.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApiRegister {
    @Headers("Content-Type: application/json")
    @POST("/api/auth/register")
    fun register (
        @Body registerRequest: RegisterRequest): Call<RegisterResponse>
}