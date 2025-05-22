package com.smurzik.rickstatistictesttask.data

import com.smurzik.rickstatistictesttask.data.remote.dto.StatisticInformation
import com.smurzik.rickstatistictesttask.data.remote.dto.UserInformation
import com.smurzik.rickstatistictesttask.domain.models.StatisticModel
import com.smurzik.rickstatistictesttask.domain.models.TopVisitorsModel

fun StatisticInformation.toStatisticModel(): StatisticModel {
    return StatisticModel(
        userId = this.userId,
        type = this.type,
        dates = this.dates
    )
}

fun UserInformation.toTopVisitorsModel(): TopVisitorsModel {
    return TopVisitorsModel(
        username = this.username,
        isOnline = this.isOnline,
        age = this.age,
        imageUri = this.files.first().avatarUrl
    )
}