package com.smurzik.rickstatistictesttask.domain

import com.smurzik.rickstatistictesttask.domain.models.SubscribersModel

class GetSubscribersUseCase(
    private val repository: StatisticRepository
) {

    suspend operator fun invoke(): SubscribersModel {
        return repository.getSubscribers()
    }
}