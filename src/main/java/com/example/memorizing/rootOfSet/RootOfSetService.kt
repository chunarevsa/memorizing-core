package com.example.memorizing.rootOfSet

import org.springframework.transaction.annotation.Transactional

interface RootOfSetService {

    @Transactional(readOnly = true)
    fun findRootOfSetById(rootOfSetId: Int): RootOfSet?

    @Transactional(readOnly = true)
    fun findRootOfSetByUserId(userId: Int): RootOfSet?
    @Transactional
    fun create(userId: Int): RootOfSet?
    @Transactional
    fun saveRootOfSet(rootOfSet: RootOfSet): RootOfSet

    @Transactional
    fun deleteRootOfSet(rootOfSet: RootOfSet)
}
