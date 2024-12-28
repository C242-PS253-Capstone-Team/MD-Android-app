package com.dicoding.capstone

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.model.RegisterRequest
import com.dicoding.model.RegisterResponse
import com.dicoding.service.RetrofitClientRegister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var txtLogin: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edtUsername = findViewById(R.id.tvUser)
        edtPassword = findViewById(R.id.tvPassword)
        btnRegister = findViewById(R.id.RegisterBtn)
        txtLogin = findViewById(R.id.LoginTxt)

        btnRegister.setOnClickListener {
            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and Password cannot be empyt!", Toast.LENGTH_SHORT).show()
            } else {
                register(username, password)
            }
        }

        val LoginTxt = findViewById<TextView>(R.id.LoginTxt)
        LoginTxt.setOnClickListener{
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }
    }

    private fun register(username: String, password: String) {
        val registerRequest = RegisterRequest(username, password)

        RetrofitClientRegister.instance.register(registerRequest)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val registerResponse = response.body()
                        Log.d(TAG, "Register successful. Response: $registerResponse")
                        val token = registerResponse?.token
                        Log.d(TAG, "Token: $token")

                        Toast.makeText(
                            this@Register,
                            "Registration Successful: ${registerResponse?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@Register, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e(TAG, "Register failed. Code: ${response.code()}, Message: ${response.message()}, ErrorBody: $errorBody")

                        Toast.makeText(
                            this@Register,
                            "Registration Failed: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.e(TAG, "Register error: ${t.message}", t)
                    Toast.makeText(this@Register, "Error: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

}