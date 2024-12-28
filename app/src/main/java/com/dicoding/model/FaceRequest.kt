package com.dicoding.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FaceRequest(
    @SerializedName("shape")
    @Expose
    val shape: String
)


