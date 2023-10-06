package com.example.memorizing.controller

import com.example.memorizing.entity.SetOfCards
import com.example.memorizing.service.CardService
import com.example.memorizing.service.CheckCardResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class CardController(
    private val cardService: CardService
) {

    fun getSetOfCardsByUsername(username: String): SetOfCards {
        return cardService.getSetOfCardsByUsername(username)
    }

    fun checkCard(req: CheckCardRequest): CheckCardResponse {

        val card = cardService.getCardBySetOfCardsIdAndCardKey(req.setOfCardsId, req.cardKey)
        val isCorrect = cardService.checkCard(card, req.userValue, req.translateToNative, req.userMaxPoint)
        return CheckCardResponse(isCorrect, card)

    }
}