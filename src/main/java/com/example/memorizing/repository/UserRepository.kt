package com.example.memorizing.repository

import com.example.memorizing.entity.User


interface UserRepository {

    fun getUserByUsername(username: String): User

}