package com.smurzik.rickstatistictesttask.presentation.models

data class AgeStatisticUiModel(
    val ageStart: Int,
    val ageEnd: Int,
    val maleCount: Int,
    val femaleCount: Int,
    val isLast: Boolean = false,
    val totalCount: Int
)

data class SexStatisticUiModel(
    val maleCountPercent: Int,
    val femaleCountPercent: Int
)