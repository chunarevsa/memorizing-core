package com.example.memorizing.repository

import com.example.memorizing.entity.RootOfSets
import org.springframework.stereotype.Repository

@Repository
class RootOfSetsFileRepositoryImpl : AFileRepository(), RootOfSetsRepository {
    override val pref: String = "root_of_sets"
    override val entity: Class<*> = RootOfSets::class.java


    override fun saveRootOfSets(rootOfSets: RootOfSets) {
        save(rootOfSets)
    }

    override fun findRootOfSets(rootId: String) = load() as RootOfSets?
}