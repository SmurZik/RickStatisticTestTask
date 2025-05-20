package com.smurzik.rickstatistictesttask.data

import com.smurzik.rickstatistictesttask.data.remote.dto.StatisticInformation
import com.smurzik.rickstatistictesttask.domain.models.StatisticModel

fun StatisticInformation.toStatisticModel(): StatisticModel {
    return StatisticModel(
        userId = this.userId,
        type = this.type,
        dates = this.dates
    )
}