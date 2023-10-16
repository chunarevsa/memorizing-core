package com.example.memorizing.setOfCard

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface SetOfCardRepository : Repository<SetOfCard, Int> {

    @Transactional(readOnly = true)
    fun findById(id: Int): SetOfCard?

    @Transactional(readOnly = true)
    @Query("select * from set_of_card where root_of_set_id = :rootOfSetId")
    fun findAllByRootOfSetId (@Param("rootOfSetId") rootOfSetId: Int): MutableList<SetOfCard>

    fun saveSetOfCard(setOfCard: SetOfCard): SetOfCard

    fun existsByRootOfSetId(rootOfSetId: Int): Boolean
    fun delete(setOfCard: SetOfCard)
}
