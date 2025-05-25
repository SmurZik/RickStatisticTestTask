package com.smurzik.rickstatistictesttask.domain.usecase

import com.smurzik.rickstatistictesttask.domain.StatisticRepository
import com.smurzik.rickstatistictesttask.domain.models.TopVisitorsModel

class GetTopVisitorsUseCase(
    private val repository: StatisticRepository
) {

    suspend operator fun invoke(): List<TopVisitorsModel> {
        return repository.getTopVisitors().filterIndexed { index, _ -> index <= 2 }
    }
}