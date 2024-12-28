package com.dicoding.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("message")
    @Expose
    var message: String? = null

        @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("data")
    @Expose
    var data: User? = null

    class User {
        @SerializedName("username")
        @Expose
        var username: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("token")
        @Expose
        var token: String? = null
    }
}
