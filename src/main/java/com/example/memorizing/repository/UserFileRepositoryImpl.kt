package com.example.memorizing.repository

import com.example.memorizing.entity.User
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Repository
import java.io.File

@Repository
class UserFileRepositoryImpl : AFileRepository(), UserRepository {
    private val logger: Logger = LogManager.getLogger(UserFileRepositoryImpl::class.java)

    override val fileName = "users"
    override val entity = User::class.java

    override fun getUserByUsername(username: String): User = load() as User? ?: throw Exception("user not found")

}