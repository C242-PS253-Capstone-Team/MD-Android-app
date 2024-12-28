package com.dicoding.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientLogin {

    private const val BASE_URL = "https://bangkit-tes.et.r.appspot.com/api/auth/login/"

    val instance: UserApiLogin by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiLogin::class.java)
    }
}
