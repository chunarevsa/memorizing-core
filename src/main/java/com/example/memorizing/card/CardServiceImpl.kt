package com.example.memorizing.card

import org.springframework.stereotype.Service

@Service
class CardServiceImpl (
    private val cards: CardRepository
) : CardService {

    override fun findListBySetOfCardId(setOfCardId: Int) = cards.findAllBySetOfCardId(setOfCardId)

}
