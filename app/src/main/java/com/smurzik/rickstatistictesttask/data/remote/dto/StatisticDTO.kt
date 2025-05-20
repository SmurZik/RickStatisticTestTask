package com.smurzik.rickstatistictesttask.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatisticDTO(
    @SerialName("statistics")
    val statistics: List<StatisticInformation>
)

@Serializable
data class StatisticInformation(
    @SerialName("user_id")
    val userId: Int,
    @SerialName("type")
    val type: String,
    @SerialName("dates")
    val dates: List<Int>
)