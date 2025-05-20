package com.smurzik.rickstatistictesttask.domain

class GetMonthlyVisitorsUseCase(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(): Int {
        val statistic = repository.getStatistic()
        var visitorsCount = 0
        statistic.forEach {
            if (it.type == "view") visitorsCount += it.dates.size
        }
        return visitorsCount
    }
}