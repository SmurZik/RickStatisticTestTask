package com.smurzik.rickstatistictesttask.data

import com.smurzik.rickstatistictesttask.data.remote.StatisticService
import com.smurzik.rickstatistictesttask.domain.StatisticRepository
import com.smurzik.rickstatistictesttask.domain.models.StatisticModel

class StatisticRepositoryImpl(
    private val service: StatisticService
) : StatisticRepository {
    override suspend fun getStatistic(): List<StatisticModel> {
        return service.getStatistic().statistics.map { it.toStatisticModel() }
    }
}