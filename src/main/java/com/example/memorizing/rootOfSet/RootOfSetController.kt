package com.example.memorizing.rootOfSet

import com.example.memorizing.card.CardService
import com.example.memorizing.setOfCard.SetOfCardService
import com.example.memorizing.system.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api")
class RootOfSetController(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val rootOfSetService: RootOfSetService,
) : RootOfSetApi {
    companion object {
        const val ENTITY_NAME = "rootOfSet"
    }

    override fun getRootOfSetById(rootOfSetId: Int): ResponseEntity<RootOfSetDto> {
        val rootOfSet = rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val result = RootOfSetDto().apply {
            this.id = rootOfSet.id
            this.userId = rootOfSet.userId
            this.listSetOfCard = rootOfSet.listSetOfCard
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun getRootOfSetByUserId(rootOfSetDto: RootOfSetDto): ResponseEntity<RootOfSetDto> {
        rootOfSetDto.userId ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val rootOfSet =
            rootOfSetService.findRootOfSetByUserId(rootOfSetDto.userId!!) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val result = RootOfSetDto().apply {
            this.id = rootOfSet.id
            this.userId = rootOfSet.userId
            this.listSetOfCard = rootOfSet.listSetOfCard
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun createRootOfSet(rootOfSetFieldsDto: RootOfSetFieldsDto): ResponseEntity<RootOfSetDto> {
        rootOfSetFieldsDto.userId ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val rootOfSet =
            rootOfSetService.create(rootOfSetFieldsDto.userId!!) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val result = RootOfSetDto().apply {
            this.id = rootOfSet.id
            this.userId = rootOfSet.userId
            this.listSetOfCard = rootOfSet.listSetOfCard
        }

        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, rootOfSet.id.toString()
            )
        )

        headers.location = UriComponentsBuilder.newInstance()
            .path("/api/rootOfSet/{id}").buildAndExpand(rootOfSet.id).toUri()

        return ResponseEntity(result, headers, HttpStatus.CREATED)
    }

    override fun updateRootOfSet(
        rootOfSetId: Int,
        rootOfSetFieldsDto: RootOfSetFieldsDto
    ): ResponseEntity<RootOfSetDto> {
        rootOfSetFieldsDto.listSetOfCard ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val rootOfSet = rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        rootOfSet.apply {
            this.listSetOfCard = rootOfSetFieldsDto.listSetOfCard!!
        }

        val newRootOfSet = rootOfSetService.saveRootOfSet(rootOfSet)

        val result = RootOfSetDto().apply {
            this.id = newRootOfSet.id
            this.userId = newRootOfSet.userId
            this.listSetOfCard = newRootOfSet.listSetOfCard
        }

        val headers = HttpHeaders(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, false, ENTITY_NAME, rootOfSet.id.toString()
            )
        )

        return ResponseEntity(result, headers, HttpStatus.NO_CONTENT)
    }

    override fun deleteRootOfSet(rootOfSetId: Int): ResponseEntity<Void> {
        val rootOfSet =
            rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        rootOfSetService.deleteRootOfSet(rootOfSet)
        val headers = HttpHeaders(
            HeaderUtil.createEntityDeleteAlert(
                applicationName, false, ENTITY_NAME, rootOfSet.id.toString()
            )
        )

        return ResponseEntity(headers, HttpStatus.NO_CONTENT)
    }

}