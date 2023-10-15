package com.example.memorizing.service

import com.example.memorizing.entity.SetOfCard

interface SetOfCardService {

    fun findByRootOfSetId(rootOfSetId: Int): List<SetOfCard>

}
