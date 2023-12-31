package com.example.memorizing.card

interface CardService {

//    @Transactional(readOnly = true)
    fun findListByCardStockId(cardStockId: Int): MutableList<Card>
//    @Transactional(readOnly = true)
    fun findCardById(cardId: Int): Card?
//    @Transactional
    fun createCard(cardFieldsDto: CardFieldsDto): Card
//    @Transactional
    fun saveCard(card: Card): Card
//    @Transactional
    fun deleteCard(card: Card)
//    @Transactional
    fun checkCard(card: Card, checkCardDto: CheckCardDto): TestResultDto
}
