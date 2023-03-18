package com.bignerdranch.android.composition.domain.entity

data class Question(
    val sum:Int,
    val visibleNumber: String,
    val options: List<Int>
)
