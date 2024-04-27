package com.example.uts.api.services

import com.example.uts.api.model.UsersResponse
import retrofit2.Call
import retrofit2.http.GET

interface UsersService {

    @GET("users")
    fun getAll(): Call<UsersResponse>
}