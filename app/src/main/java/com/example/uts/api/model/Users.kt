package com.example.uts.api.model

import java.util.Objects

data class Users (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val gender: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String,
    val birthdate: String,
    val image: String,
    val height: Int,
    val weight: Number
)
