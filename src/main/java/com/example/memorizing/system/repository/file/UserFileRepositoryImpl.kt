package com.example.memorizing.system.repository.file

//import com.example.memorizing.user.User
//import com.example.memorizing.repository.UserRepository
//import org.apache.logging.log4j.LogManager
//import org.apache.logging.log4j.Logger
//import org.springframework.stereotype.Repository

//@Repository
//class UserFileRepositoryImpl : AFileRepository(), UserRepository {
//    private val logger: Logger = LogManager.getLogger(UserFileRepositoryImpl::class.java)
//
//    override val pref = "user"
//    override val entity = User::class.java
//
//    override fun findUserByUsername(username: String) = load() as User?
//    override fun addUser(user: User) {
//        logger.info("add user: ${user.username}")
//        load() as User? ?: saveUser(user)
//    }
//
//    override fun saveUser(user: User) {
//        save(user)
//    }
//
//}