package com.example.memorizing.entity

enum class ELanguage {
    RUS,
    ENG,
    DEU;

    companion object {
        fun getPairsOfLanguage(): MutableList<Pair<ELanguage, ELanguage>> {
            val listOfPairs: MutableList<Pair<ELanguage, ELanguage>> = mutableListOf()

            ELanguage.values().forEach {first ->
                ELanguage.values().forEach {second ->
                    if (first != second &&
                        (first == RUS || second == RUS) // допущение, что один из языков всегда RUS
                    ) listOfPairs.add(Pair(first, second))
                }
            }
            println("Получен список пар: $listOfPairs")
            return listOfPairs
        }
    }
}