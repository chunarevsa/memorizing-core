package com.example.memorizing.controller

import com.example.memorizing.service.CardService
import org.springframework.web.bind.annotation.RestController

@RestController
class CardController(
    private val cardService: CardService
) {

    fun checkCard(req: CheckCardRequest): CheckCardResponse {
        val isCorrect =
            cardService.checkCard(req.setOfCardsId, req.cardKey, req.userValue, req.translateToNative)

        return CheckCardResponse(isCorrect)

    }
}