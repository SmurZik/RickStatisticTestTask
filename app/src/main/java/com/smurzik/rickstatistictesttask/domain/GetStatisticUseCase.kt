package com.smurzik.rickstatistictesttask.domain

import com.smurzik.rickstatistictesttask.domain.models.StatisticModel

class GetStatisticUseCase(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(): List<StatisticModel> {
        return repository.getStatistic()
    }
}