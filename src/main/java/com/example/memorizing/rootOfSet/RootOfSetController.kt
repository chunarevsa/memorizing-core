package com.example.memorizing.rootOfSet

import com.example.memorizing.card.CardServiceImpl
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
    private val rootOfSetService: RootOfSetServiceImpl,
    private val setOfCardService: SetOfCardService,
    private val cardServiceImpl: CardServiceImpl,
) : RootOfSetApi {
    companion object {
        const val ENTITY_NAME = "rootOfSet"
    }

    override fun findRootOfSetById(rootOfSetId: Int): ResponseEntity<RootOfSetDto> {
        val rootOfSet = rootOfSetService.findRootOfSetById(rootOfSetId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        rootOfSet.listSetOfCard = setOfCardService.findByRootOfSetId(rootOfSetId).toMutableList()
        rootOfSet.listSetOfCard.forEach { setOfCard ->
            setOfCard.listOfCards.add(
                cardServiceImpl.findBySetOfCardId(
                    setOfCard.id!!
                )
            )
        }
        val result = RootOfSetDto().apply {
            this.id = rootOfSet.id
            this.userId = rootOfSet.userId
            this.listSetOfCard = rootOfSet.listSetOfCard
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun findRootOfSetByUserId(req: RootOfSetDto): ResponseEntity<RootOfSetDto> {
        req.userId ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val rootOfSet =
            rootOfSetService.findRootOfSetByUserId(req.userId!!) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        rootOfSet.listSetOfCard = setOfCardService.findByRootOfSetId(rootOfSet.id!!).toMutableList()
        rootOfSet.listSetOfCard.forEach { setOfCard ->
            setOfCard.listOfCards.add(
                cardServiceImpl.findBySetOfCardId(
                    setOfCard.id!!
                )
            )
        }
        val result = RootOfSetDto().apply {
            this.id = rootOfSet.id
            this.userId = rootOfSet.userId
            this.listSetOfCard = rootOfSet.listSetOfCard
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun createRootOfSet(req: RootOfSetDto): ResponseEntity<RootOfSetDto> {
        req.userId ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val rootOfSet = rootOfSetService.create(req.userId!!) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

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

    override fun updateRootOfSet(rootOfSetId: Int, req: RootOfSetDto): ResponseEntity<RootOfSetDto> {
        req.listSetOfCard ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        val rootOfSet = rootOfSetService.findRootOfSetById(req.id!!) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        rootOfSet.apply {
            this.listSetOfCard = req.listSetOfCard!!
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

//    @PostMapping("/{rootId}/addSetOfCardId")
//    fun addSetOfCardId(@PathVariable rootId: String, @RequestBody setOfCardId: String): ResponseEntity<Void> {
//        rootOfSetServiceImpl.addSetOfCardId(rootId, setOfCardId)
//        return ResponseEntity.noContent()
//            .headers(
//                HeaderUtil.createEntityDeleteAlert(
//                    applicationName, false, ENTITY_NAME, rootId
//                )
//            ).build()
//    }

}