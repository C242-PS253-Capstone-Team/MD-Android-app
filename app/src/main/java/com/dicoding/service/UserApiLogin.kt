package com.dicoding.service

import com.dicoding.model.LoginRequest
import com.dicoding.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApiLogin {
    @Headers("Content-Type: application/json")
    @POST("/api/auth/login")
    fun login(
        @Body loginRequest: LoginRequest): Call<LoginResponse>
}


