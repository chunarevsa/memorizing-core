package com.example.memorizing.controller

import com.example.memorizing.entity.RootOfSet
import com.example.memorizing.service.SetOfCardService
import com.example.memorizing.util.HeaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/setOfCard")
class SetOfCardController(
    private val setOfCardService: SetOfCardService,
    @Value("\${spring.application.name}")
    private val applicationName: String
) {

    // TODO: Добавить в сущность флаг testMode = true.
    // TODO: Добавить флаг обратного "перевода" onlyForward = true. Проставлять UNKNOWN в статусе from
    // TODO: Переименовать значения value в key и translate в value

    companion object {
        const val ENTITY_NAME = "setOfCard"
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<*> {
        return ResponseEntity.ok(setOfCardService.getSetOfCardById(id))
    }

    @PostMapping("/create")
    fun create(@RequestBody req: CreateSetOfCardRequest): ResponseEntity<*> {
        return ResponseEntity.ok(setOfCardService.create(req.pair, req.maxPoint))
    }

    @PostMapping("/{id}/update")
    fun update(@PathVariable id: String, @RequestBody rootOfSet: RootOfSet): ResponseEntity<*> {
        return ResponseEntity.ok(setOfCardService.update(rootOfSet))
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        setOfCardService.delete(id)
        return ResponseEntity.noContent()
            .headers(
                HeaderUtil.createEntityDeactivationAlert(
                    applicationName, false, ENTITY_NAME, id
                )
            ).build()
    }

    @PostMapping("/{rootId}/addSetOfCardId")
    fun addSetOfCardId(@PathVariable id: String, @RequestBody setOfCardId: String): ResponseEntity<Void> {
        setOfCardService.addSetOfCardId(rootId, setOfCardId)
        return ResponseEntity.noContent()
            .headers(
                HeaderUtil.createEntityDeactivationAlert(
                    applicationName, false, RootOfSetController.ENTITY_NAME, rootId
                )
            ).build()
    }

}