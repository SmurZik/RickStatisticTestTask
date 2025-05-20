package com.smurzik.rickstatistictesttask.domain.models

data class StatisticModel(
    val userId: Int,
    val type: String,
    val dates: List<Int>
)