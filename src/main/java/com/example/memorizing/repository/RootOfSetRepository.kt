package com.example.memorizing.repository

import com.example.memorizing.entity.RootOfSet
import org.springframework.data.repository.Repository
import org.springframework.transaction.annotation.Transactional

interface RootOfSetRepository : Repository<RootOfSet, Int> {

    @Transactional(readOnly = true)
    fun findById(id: Int): RootOfSet

//    fun findAllById(ids: MutableList<Long>): MutableIterable<RootOfSet>

    fun save(rootOfSet: RootOfSet): RootOfSet

    fun saveAndReturnId(rootOfSet: RootOfSet): Int

    fun update(rootOfSet: RootOfSet)

    fun delete(id: Int)

}
