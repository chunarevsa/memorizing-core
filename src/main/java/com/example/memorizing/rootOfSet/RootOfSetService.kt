package com.example.memorizing.rootOfSet

import com.example.memorizing.rootOfSet.RootOfSet
import org.springframework.transaction.annotation.Transactional

interface RootOfSetService {

    @Transactional(readOnly = true)
    fun findRootOfSetById(id: Int): RootOfSet
}
