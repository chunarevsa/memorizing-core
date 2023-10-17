package com.example.memorizing.setOfCard

import com.example.memorizing.rootOfSet.RootOfSetService
import com.example.memorizing.system.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/rootOfSet/{rootOfSetId}")
class SetOfCardController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val rootOfSetService: RootOfSetService,
    private val setOfCardService: SetOfCardService,
) : SetOfCardApi {

    // TODO: Добавить в сущность флаг testMode = true.
    // TODO: Добавить флаг обратного "перевода" onlyForward = true. Проставлять UNKNOWN в статусе from
    // TODO: Переименовать значения value в key и translate в value
    companion object {
        const val ENTITY_NAME = "setOfCard"
    }

    override fun getSetOfCardById(rootOfSetId: Int, setOfCardId: Int): ResponseEntity<SetOfCardDto> {
        val rootOfSet = rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val setOfCard = setOfCardService.findSetOfCardById(setOfCardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (setOfCard.rootOfSetId != rootOfSet.id) return ResponseEntity(HttpStatus.BAD_REQUEST)

        val result = SetOfCardDto().apply {
            this.id = setOfCard.id
            this.maxPoint = setOfCard.maxPoint
            this.listOfCards = setOfCard.listOfCards
        }
        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun addSetOfCardToRootOfSet(
        rootOfSetId: Int,
        setOfCardFieldsDto: SetOfCardFieldsDto
    ): ResponseEntity<SetOfCardDto> {
        rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val setOfCard = setOfCardService.addSetOfCardToRootOfSet(rootOfSetId, setOfCardFieldsDto)

        val result = SetOfCardDto().apply {
            this.id = setOfCard.id
            this.rootOfSetId = setOfCard.rootOfSetId
            this.maxPoint = setOfCard.maxPoint
            this.listOfCards = setOfCard.listOfCards
        }

        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, setOfCard.id.toString()
            )
        )
        headers.location =
            UriComponentsBuilder.newInstance().path("/rootOfSet/{$rootOfSetId}/setOfCard/{id}").buildAndExpand(setOfCard.id).toUri()

        return ResponseEntity(result, headers, HttpStatus.CREATED)
    }

    override fun updateSetOfCard(
        rootOfSetId: Int,
        setOfCardId: Int,
        setOfCardFieldsDto: SetOfCardFieldsDto
    ): ResponseEntity<SetOfCardDto> {
        rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val setOfCard = setOfCardService.findSetOfCardById(setOfCardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        setOfCardService.saveSetOfCard(setOfCard.apply {
            this.pair = setOfCardFieldsDto.pair
            this.maxPoint = setOfCardFieldsDto.maxPoint
        })

        val result = SetOfCardDto().apply {
            this.id = setOfCard.id
            this.rootOfSetId = setOfCard.rootOfSetId
            this.pair = setOfCard.pair
            this.maxPoint = setOfCard.maxPoint
            this.listOfCards = setOfCard.listOfCards
        }

        val headers = HttpHeaders(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, setOfCard.id.toString()
            )
        )
        return ResponseEntity(result, headers, HttpStatus.NO_CONTENT)
    }

    override fun deleteSetOfCard(
        rootOfSetId: Int,
        setOfCardId: Int
    ): ResponseEntity<Void> {
        rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val setOfCard = setOfCardService.findSetOfCardById(setOfCardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        setOfCardService.deleteSetOfCard(setOfCard)
        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, setOfCard.id.toString()
            )
        )
        return ResponseEntity(headers, HttpStatus.NO_CONTENT)
    }

}