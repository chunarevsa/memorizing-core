package com.example.memorizing.service

import com.example.memorizing.repository.CardRepository
import org.springframework.stereotype.Service

@Service
class CardServiceImpl (
    private val cards: CardRepository
) : CardService {

    fun findBySetOfCardId(setOfCardId: Int) = cards.findBySetOfCardId(setOfCardId)


}
