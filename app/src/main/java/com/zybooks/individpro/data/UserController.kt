package com.zybooks.individpro.data
//Author: Jan Brix Batalla


object UserManager {// not doing db yet - till week10
    private val registeredUsers = mutableListOf<User>()

    fun registerUser(user: User): Boolean {
        if (isUserRegistered(user.email)) {
            return false
        }
        registeredUsers.add(user)
        return true
    }

    fun isUserRegistered(email: String): Boolean {
        return registeredUsers.any { it.email == email }
    }
    //can add a username here also

    fun getAllUsers(): List<User> {//use to check users when logging in
        return registeredUsers.toList()
    }
}