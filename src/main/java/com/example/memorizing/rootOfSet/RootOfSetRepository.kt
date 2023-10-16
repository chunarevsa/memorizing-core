package com.example.memorizing.rootOfSet

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface RootOfSetRepository : Repository<RootOfSet, Int> {

    @Transactional(readOnly = true)
    fun findById(id: Int): RootOfSet?

    @Transactional(readOnly = true)
    @Query("select * from root_of_set where user_id = :userId")
    fun findByUserId(@Param("userId") userId: Int): RootOfSet?

    fun saveRootOfSet(rootOfSet: RootOfSet): RootOfSet

    fun existsByUserId(userId: Int): Boolean
    fun delete(rootOfSet: RootOfSet)


}
