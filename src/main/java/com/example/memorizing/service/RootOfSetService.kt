package com.example.memorizing.service

import com.example.memorizing.entity.RootOfSet
import org.springframework.transaction.annotation.Transactional

interface RootOfSetService {

    @Transactional(readOnly = true)
    fun findRootOfSetById(id: Int): RootOfSet
}
