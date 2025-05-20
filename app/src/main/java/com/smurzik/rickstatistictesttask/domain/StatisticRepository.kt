package com.smurzik.rickstatistictesttask.domain

import com.smurzik.rickstatistictesttask.domain.models.StatisticModel

interface StatisticRepository {

    suspend fun getStatistic(): List<StatisticModel>
}