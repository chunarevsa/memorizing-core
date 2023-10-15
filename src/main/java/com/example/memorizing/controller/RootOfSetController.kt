package com.example.memorizing.controller

import com.example.memorizing.entity.RootOfSet
import com.example.memorizing.service.CardServiceImpl
import com.example.memorizing.service.RootOfSetServiceImpl
import com.example.memorizing.service.SetOfCardService
import com.example.memorizing.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    override fun getRootOfSetById(rootOfSetId: Int): ResponseEntity<RootOfSet> {
        val rootOfSet = rootOfSetService.findRootOfSetById(rootOfSetId)
        rootOfSet.listSetOfCard = setOfCardService.findByRootOfSetId(rootOfSetId).toMutableList()
        rootOfSet.listSetOfCard.forEach { setOfCard ->
            setOfCard.listOfCards.add(
                cardServiceImpl.findBySetOfCardId(
                    setOfCard.id!!
                )
            )
        }

        return ResponseEntity(rootOfSet, HttpStatus.OK)

    }

    @GetMapping("/{userId}")
    fun getByUserId(@PathVariable userId: String): ResponseEntity<*> {
        return ResponseEntity.ok(rootOfSetServiceImpl.getRootOfSetByUserId(userId))
    }

    @PostMapping("/create/{userId}")
    fun create(@PathVariable userId: String): ResponseEntity<*> {
        if (rootOfSets.existsByUserId(userId)) throw Exception("exists")
        return rootOfSetCrudRepository.save(RootOfSet(userId))

        return ResponseEntity.ok(rootOfSetServiceImpl.create(userId))
    }

    @PostMapping("/{id}/update")
    fun update(@PathVariable id: String, @RequestBody rootOfSet: RootOfSet): ResponseEntity<*> {
        return ResponseEntity.ok(rootOfSetServiceImpl.update(rootOfSet))
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        rootOfSetServiceImpl.delete(id)
        return ResponseEntity.noContent()
            .headers(
                HeaderUtil.createEntityDeactivationAlert(
                    applicationName, false, ENTITY_NAME, id
                )
            ).build()
    }

    @PostMapping("/{rootId}/addSetOfCardId")
    fun addSetOfCardId(@PathVariable rootId: String, @RequestBody setOfCardId: String): ResponseEntity<Void> {
        rootOfSetServiceImpl.addSetOfCardId(rootId, setOfCardId)
        return ResponseEntity.noContent()
            .headers(
                HeaderUtil.createEntityDeactivationAlert(
                    applicationName, false, ENTITY_NAME, rootId
                )
            ).build()
    }


}