package com.example.memorizing.setOfCard

import com.example.memorizing.system.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/setOfCard")
class SetOfCardController(
    private val setOfCardService: SetOfCardService,
    @Value("\${spring.application.name}")
    private val applicationName: String
) : SetOfCardApi {

    // TODO: Добавить в сущность флаг testMode = true.
    // TODO: Добавить флаг обратного "перевода" onlyForward = true. Проставлять UNKNOWN в статусе from
    // TODO: Переименовать значения value в key и translate в value
    companion object {
        const val ENTITY_NAME = "setOfCard"
    }

    override fun findSetOfCardById(setOfCardId: Int): ResponseEntity<SetOfCardDto> {
        val setOfCard = setOfCardService.findSetOfCardById(setOfCardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        setOfCard.listSetOfCard = setOfCardService.findBySetOfCardId(setOfCardId).toMutableList()
        setOfCard.listSetOfCard.forEach { setOfCard ->
            setOfCard.listOfCards.add(
                cardServiceImpl.findBySetOfCardId(
                    setOfCard.id!!
                )
            )
        }
        val result = SetOfCardDto().apply {
            this.id = setOfCard.id
            this.userId = setOfCard.userId
            this.listSetOfCard = setOfCard.listSetOfCard
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun findSetOfCardByUserId(setOfCardDto: SetOfCardDto): ResponseEntity<SetOfCardDto> {
        setOfCardDto.userId ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val setOfCard =
            setOfCardService.findSetOfCardByUserId(setOfCardDto.userId!!) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        setOfCard.listSetOfCard = setOfCardService.findBySetOfCardId(setOfCard.id!!).toMutableList()
        setOfCard.listSetOfCard.forEach { setOfCard ->
            setOfCard.listOfCards.add(
                cardServiceImpl.findBySetOfCardId(
                    setOfCard.id!!
                )
            )
        }
        val result = SetOfCardDto().apply {
            this.id = setOfCard.id
            this.userId = setOfCard.userId
            this.listSetOfCard = setOfCard.listSetOfCard
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun createSetOfCard(setOfCardFieldsDto: SetOfCardFieldsDto): ResponseEntity<SetOfCardDto> {
        setOfCardFieldsDto.userId ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val setOfCard =
            setOfCardService.create(setOfCardFieldsDto.userId!!) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val result = SetOfCardDto().apply {
            this.id = setOfCard.id
            this.userId = setOfCard.userId
            this.listSetOfCard = setOfCard.listSetOfCard
        }

        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, setOfCard.id.toString()
            )
        )

        headers.location = UriComponentsBuilder.newInstance()
            .path("/api/setOfCard/{id}").buildAndExpand(setOfCard.id).toUri()

        return ResponseEntity(result, headers, HttpStatus.CREATED)
    }

    override fun updateSetOfCard(
        setOfCardId: Int,
        setOfCardFieldsDto: SetOfCardFieldsDto
    ): ResponseEntity<SetOfCardDto> {
        setOfCardFieldsDto.listSetOfCard ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val setOfCard = setOfCardService.findSetOfCardById(setOfCardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        setOfCard.apply {
            this.listSetOfCard = setOfCardFieldsDto.listSetOfCard!!
        }

        val newSetOfCard = setOfCardService.saveSetOfCard(setOfCard)

        val result = SetOfCardDto().apply {
            this.id = newSetOfCard.id
            this.userId = newSetOfCard.userId
            this.listSetOfCard = newSetOfCard.listSetOfCard
        }

        val headers = HttpHeaders(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, setOfCard.id.toString()
            )
        )

        return ResponseEntity(result, headers, HttpStatus.NO_CONTENT)
    }

    override fun deleteSetOfCard(setOfCardId: Int): ResponseEntity<Void> {
        val setOfCard =
            setOfCardService.findSetOfCardById(setOfCardId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        setOfCardService.deleteSetOfCard(setOfCard)
        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, setOfCard.id.toString()
            )
        )

        return ResponseEntity(headers, HttpStatus.NO_CONTENT)
    }

    override fun addSetOfCardToSetOfCard(
        setOfCardId: Int,
        setOfCardFieldsDto: SetOfCardFieldsDto
    ): ResponseEntity<SetOfCardDto> {

        setOfCardServiceImpl.addSetOfCardId(rootId, setOfCardId)
        return ResponseEntity.noContent()
            .headers(
                HeaderUtil.createEntityDeleteAlert(
                    applicationName, false, ENTITY_NAME, rootId
                )
            ).build()
    }


}