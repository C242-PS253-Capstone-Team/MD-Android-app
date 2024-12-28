package com.dicoding.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientRegister {

    private const val BASE_URL = "https://bangkit-tes.et.r.appspot.com/api/auth/register/"

    val instance: UserApiRegister by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiRegister::class.java)
    }
}