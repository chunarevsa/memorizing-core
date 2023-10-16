package com.example.memorizing.card

import org.springframework.stereotype.Service

@Service
class CardServiceImpl (
    private val cards: CardRepository
) : CardService {

    fun findBySetOfCardId(setOfCardId: Int) = cards.findBySetOfCardId(setOfCardId)


}
