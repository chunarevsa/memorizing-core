package com.example.memorizing.repository

import com.example.memorizing.entity.RootOfSets

interface RootOfSetsRepository {
    fun saveRootOfSets(rootOfSets: RootOfSets)
    fun findRootOfSets(rootId: String): RootOfSets?
}
