package com.smurzik.rickstatistictesttask.domain

import com.smurzik.rickstatistictesttask.domain.models.AgeStatisticModel
import com.smurzik.rickstatistictesttask.domain.models.SortTypeAge
import com.smurzik.rickstatistictesttask.domain.models.StatisticModel
import com.smurzik.rickstatistictesttask.domain.models.TopVisitorsModel

interface StatisticRepository {

    suspend fun getStatistic(): List<StatisticModel>

    suspend fun getTopVisitors(): List<TopVisitorsModel>

    suspend fun getAgeStatistic(sortTypeAge: SortTypeAge): List<AgeStatisticModel>
}