package com.example.memorizing.service

import com.example.memorizing.entity.RootOfSet
import com.example.memorizing.repository.RootOfSetRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class RootOfSetServiceImpl(
    private val rootOfSets: RootOfSetRepository,
) : RootOfSetService
{
    private val domainType: Class<RootOfSet> = RootOfSet::class.java

    @Transactional(readOnly = true)
    override fun findRootOfSetById(id: Int): RootOfSet = rootOfSets.findById(id)

    fun getRootOfSetByUserId(userId: String): RootOfSet {
        return rootOfSetCrudRepository.findByUserId(userId) ?: throw Exception("not found")
    }

    fun create(userId: String): RootOfSet {
        if (rootOfSetCrudRepository.existsByUserId(userId)) throw Exception("exists")
        return rootOfSetCrudRepository.save(RootOfSet(userId))
    }

    fun update(rootOfSet: RootOfSet) {
        if (!rootOfSetCrudRepository.existsById(rootOfSet.id)) throw Exception("not found")
        rootOfSetCrudRepository.save(rootOfSet)
    }

    fun delete(id: String) {
        if (!rootOfSetCrudRepository.existsById(id)) throw Exception("not found")
        return rootOfSetCrudRepository.deleteById(id)
    }

    fun addSetOfCardId(id: String, setOfCardId: String) {
        val rootOfSet = findRootOfSetById(id)
        if (rootOfSet.setOfCardsIds.contains(setOfCardId)) return
        rootOfSet.setOfCardsIds.add(setOfCardId)
        rootOfSetCrudRepository.save(rootOfSet)
    }

}
