package com.example.memorizing.service

import com.example.memorizing.entity.Card
import com.example.memorizing.entity.ECardStatus
import com.example.memorizing.entity.SetOfCards
import com.example.memorizing.repository.CardRepository
import org.springframework.stereotype.Service

@Service
class CardService(
    private val cardRepository: CardRepository,
) {

    fun checkCard(card: Card, setOfCardsById: Int, userValue: String, translateToNative: Boolean, userMaxPoint: Int): Boolean {


        if (card.translate.contains(userValue)) {
            // correct answer
            if (card.status == ECardStatus.HARD) {
                card.status = ECardStatus.NORMAL
                card.pointFromNative = 0
            }
            if (card.pointFromNative >= userMaxPoint) {
                card.pointFromNative = card.pointFromNative + 1
                card.status = ECardStatus.COMPLETED
                println("You learn this ${card.type.name}!")
            } else {
                card.pointFromNative = card.pointFromNative + 1
                print("${card.translate}:${card.pointFromNative}")
            }
            fileService.saveCards()
        } else {
            // wrong answer
            if (card.status == ECardStatus.HARD) {
                card.pointFromNative = card.pointFromNative - 1
            } else card.pointFromNative = -1

            card.status = ECardStatus.HARD
            fileService.saveCards()
            println("Opss! Wrong!")
            println("${card.value}:${card.translate}:${card.pointFromNative}")
        }
        return false
    }

    fun getCardBySetOfCardsIdAndCardKey(setOfCardsId: String, cardKey: String) =
        getSetOfCardsById(setOfCardsId).mapOfCards[cardKey] ?: throw Exception("not found")

    fun getSetOfCardsByUsername(username: String): List<SetOfCards> {
        return cardRepository.findSetOfCardsByUsername(username) // TODO add exception
    }

    fun getSetOfCardsById(setOfCardsById: String): SetOfCards {
        return cardRepository.findSetOfCardsById(setOfCardsById) // TODO add exception
    }

}
