package com.example.memorizing.rootOfSet

import com.example.memorizing.setOfCard.SetOfCardService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class RootOfSetServiceImpl(
    private val rootOfSets: RootOfSetRepository,
) : RootOfSetService {
    override fun findRootOfSetById(rootOfSetId: Int): RootOfSet? = rootOfSets.findById(rootOfSetId)

    override fun findRootOfSetByUserId(userId: Int): RootOfSet? = rootOfSets.findByUserId(userId)


    override fun create(userId: Int): RootOfSet? {
        if (rootOfSets.existsByUserId(userId)) return null
        return rootOfSets.saveRootOfSet(RootOfSet(userId))
    }

    override fun saveRootOfSet(rootOfSet: RootOfSet) = rootOfSets.saveRootOfSet(rootOfSet)
    override fun deleteRootOfSet(rootOfSet: RootOfSet) = rootOfSets.delete(rootOfSet)

}
