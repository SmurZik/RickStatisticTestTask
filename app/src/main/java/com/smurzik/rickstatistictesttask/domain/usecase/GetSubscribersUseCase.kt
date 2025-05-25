package com.smurzik.rickstatistictesttask.domain.usecase

import com.smurzik.rickstatistictesttask.domain.StatisticRepository
import com.smurzik.rickstatistictesttask.domain.models.SubscribersModel

class GetSubscribersUseCase(
    private val repository: StatisticRepository
) {

    suspend operator fun invoke(): SubscribersModel {
        return repository.getSubscribers()
    }
}