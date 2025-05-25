package com.smurzik.rickstatistictesttask.data

import com.smurzik.rickstatistictesttask.data.remote.StatisticService
import com.smurzik.rickstatistictesttask.domain.StatisticRepository
import com.smurzik.rickstatistictesttask.domain.models.AgeStatisticModel
import com.smurzik.rickstatistictesttask.domain.models.SortTypeAge
import com.smurzik.rickstatistictesttask.domain.models.StatisticModel
import com.smurzik.rickstatistictesttask.domain.models.TopVisitorsModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StatisticRepositoryImpl(
    private val service: StatisticService
) : StatisticRepository {

    override suspend fun getStatistic(): List<StatisticModel> {
        return service.getStatistic().statistics.map { it.toStatisticModel() }
    }

    override suspend fun getTopVisitors(): List<TopVisitorsModel> {
        val visitorViews = mutableMapOf<Int, Int>()
        val users = service.getUsers().users
        val statistic = getStatistic().filter { it.type == "view" }
        statistic.forEach { statisticInfo ->
            val count = visitorViews.getOrDefault(statisticInfo.userId, 0)
            visitorViews[statisticInfo.userId] = count + statisticInfo.dates.size
        }
        val topUsers = visitorViews
            .toList()
            .sortedByDescending { it.second }
            .map { it.first }
        return topUsers.map { topUserId ->
            users.find { it.id == topUserId }?.toTopVisitorsModel()!!
        }
    }

    override suspend fun getAgeStatistic(sortTypeAge: SortTypeAge): List<AgeStatisticModel> {
        val visitorViews = mutableMapOf<Int, Int>()
        // assume current date is 09.09.2024
        val currentDate = LocalDate.of(2024, 9, 9)
        val datesSet = mutableSetOf<LocalDate>()
        when (sortTypeAge) {
            SortTypeAge.TODAY -> {
                datesSet.add(currentDate)
            }

            SortTypeAge.WEEK -> {
                var tempDate = currentDate
                repeat(7) {
                    datesSet.add(tempDate)
                    tempDate = tempDate.minusDays(1)
                }
            }

            SortTypeAge.MONTH -> {
                var tempDate = currentDate
                repeat(30) {
                    datesSet.add(tempDate)
                    tempDate = tempDate.minusDays(1)
                }
            }

            SortTypeAge.ALL_TIME -> {
                // nothing change
            }
        }
        val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
        val formattedDates = datesSet.map {
            if (it.format(formatter).startsWith('0')) it.format(formatter).drop(1)
            else it.format(formatter)
        }
        val users = service.getUsers().users
        val statistic = getStatistic().filter { it.type == "view" }
        statistic.forEach { statisticInfo ->
            if (sortTypeAge != SortTypeAge.ALL_TIME) {
                var tempCount = 0
                statisticInfo.dates.forEach {
                    if (formattedDates.contains(it.toString())) tempCount++
                }
                val count = visitorViews.getOrDefault(statisticInfo.userId, 0)
                visitorViews[statisticInfo.userId] = count + tempCount
            } else {
                val count = visitorViews.getOrDefault(statisticInfo.userId, 0)
                visitorViews[statisticInfo.userId] = count + statisticInfo.dates.size
            }
        }
        val result = visitorViews.map { viewStatistic ->
            val user = users.find { it.id == viewStatistic.key }
            AgeStatisticModel(
                age = user?.age ?: -1,
                isMale = user?.sex == "M",
                viewCount = viewStatistic.value
            )
        }
        return result
    }
}