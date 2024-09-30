package com.zybooks.individpro.data
//Author: Jan Brix Batalla


data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: String,
    val password: String,
    val reTypePassword: String
)