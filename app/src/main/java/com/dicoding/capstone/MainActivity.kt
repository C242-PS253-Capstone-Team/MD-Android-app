package com.dicoding.capstone

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.model.LoginRequest
import com.dicoding.model.LoginResponse
import com.dicoding.service.RetrofitClientLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtUsername = findViewById(R.id.tvUser)
        edtPassword = findViewById(R.id.tvPassword)
        btnLogin = findViewById(R.id.LoginBtn)
        txtRegister = findViewById(R.id.RegisterTxt)

        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and Password cannot be empyt!", Toast.LENGTH_SHORT).show()
            } else {
                login(username, password)
            }
        }

        txtRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)

        RetrofitClientLogin.instance.login(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        Log.d(TAG, "Login successful. Response: $loginResponse")

                        val token = loginResponse?.token
                        Log.d(TAG, "Token: $token")

                        Toast.makeText(
                            this@MainActivity,
                            "Login Successful: ${loginResponse?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@MainActivity, Dashboard::class.java)
                        startActivity(intent)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e(TAG, "Login failed. Code: ${response.code()}, Message: ${response.message()}, ErrorBody: $errorBody")

                        Toast.makeText(
                            this@MainActivity,
                            "Login Failed: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e(TAG, "Login error: ${t.message}", t)

                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

}
