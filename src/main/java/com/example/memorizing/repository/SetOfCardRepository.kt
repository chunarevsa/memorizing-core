package com.example.memorizing.repository

import com.example.memorizing.entity.SetOfCard
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

interface SetOfCardRepository : Repository<SetOfCard, Int> {

    @Query("select * from set_of_card where root_of_set_id = :rootOfSetId")
    fun findByRootOfSetId(@Param("rootOfSetId") rootOfSetId: Int): List<SetOfCard>
}
