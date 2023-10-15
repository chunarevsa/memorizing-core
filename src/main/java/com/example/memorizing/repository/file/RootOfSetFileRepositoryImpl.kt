package com.example.memorizing.repository.file

import com.example.memorizing.entity.RootOfSet
import org.springframework.stereotype.Repository

@Repository
class RootOfSetFileRepositoryImpl : AFileRepository(), RootOfSetCrudRepository {
    override val pref: String = "root_of_sets"
    override val entity: Class<*> = RootOfSet::class.java


    override fun saveRootOfSet(rootOfSet: RootOfSet) {
        save(rootOfSet)
    }

    override fun findRootOfSet(rootId: String) = load() as RootOfSet?
}