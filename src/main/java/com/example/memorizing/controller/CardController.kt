package com.example.memorizing.controller

import com.example.memorizing.service.CardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/card")
class CardController(
    private val cardService: CardService
) {

    fun checkCard(req: CheckCardRequest): CheckCardResponse {
        val isCorrect =
            cardService.checkCard(req.setOfCardsId, req.cardKey, req.userValue, req.translateToNative)

        return CheckCardResponse(isCorrect)

    }

    @GetMapping("/test")
    fun test():ResponseEntity<*> = ResponseEntity.ok("Successfully")

}