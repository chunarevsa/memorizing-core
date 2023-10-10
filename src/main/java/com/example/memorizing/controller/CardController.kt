package com.example.memorizing.controller

import com.example.memorizing.service.CardService
import org.springframework.web.bind.annotation.RestController

@RestController
class CardController(
    private val cardService: CardService
) {

    fun checkCard(req: CheckCardRequest): CheckCardResponse {

        val card = cardService.getCardBySetOfCardsIdAndCardKey(req.setOfCardsId, req.cardKey)
        val isCorrect =
            cardService.checkCard(card, req.setOfCardsId, req.userValue, req.translateToNative, req.userMaxPoint)
        return CheckCardResponse(isCorrect, card)

    }
}