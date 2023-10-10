package com.example.memorizing.repository

import com.example.memorizing.entity.User

interface UserRepository {
    fun findUserByUsername(username: String): User?
    fun addUser(user: User)
    fun saveUser(user: User)
}