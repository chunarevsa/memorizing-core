package com.example.memorizing.setOfCard

import com.example.memorizing.entity.ELanguage
import org.springframework.stereotype.Service
import java.util.*

@Service
class SetOfCardServiceImpl (
    private val setOfCards: SetOfCardRepository
) : SetOfCardService {

    override fun findByRootOfSetId(rootOfSetId: Int): List<SetOfCard> = setOfCards.findByRootOfSetId(rootOfSetId)


    fun getSetOfCardById(setOfCardId: String): SetOfCard {
        return setOfCardRepository.findById(setOfCardId).orElseThrow { throw Exception("not found") }
    }

    fun create(pair: Pair<ELanguage, ELanguage>?, maxPoint: Int): SetOfCard {
        return save(SetOfCard(pair, maxPoint))
    }

    fun update(rootOfSet: SetOfCard) {
        setOfCardRepository.exist(rootOfSet.id) ?: throw Exception("not found")
        setOfCardRepository.update(rootOfSet)
    }

    fun delete(rootId: String) {
        setOfCardRepository.exist(rootOfSet.id) ?: throw Exception("not found")
        return setOfCardRepository.delete(rootId)
    }

    fun addSetOfCardId(rootId: String, setOfCardId: String) {
        val rootOfSet = setOfCardRepository.findRootOfSet(rootId) ?: throw Exception("not found")
        rootOfSet.setOfCardsIds.add(setOfCardId)
        save(rootOfSet)
    }

    private fun save(setOfCard: SetOfCard): SetOfCard {
        if (setOfCardRepository.exist(rootOfSet.id)) throw Exception("not found")
        return setOfCardRepository.saveRootOfSet(rootOfSet)
    }


}