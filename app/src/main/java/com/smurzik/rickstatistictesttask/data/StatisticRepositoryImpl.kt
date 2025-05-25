package com.smurzik.rickstatistictesttask.data

import com.smurzik.rickstatistictesttask.data.remote.StatisticService
import com.smurzik.rickstatistictesttask.domain.StatisticRepository
import com.smurzik.rickstatistictesttask.domain.models.AgeStatisticModel
import com.smurzik.rickstatistictesttask.domain.models.SortTypeAge
import com.smurzik.rickstatistictesttask.domain.models.StatisticModel
import com.smurzik.rickstatistictesttask.domain.models.SubscribersModel
import com.smurzik.rickstatistictesttask.domain.models.TopVisitorsModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StatisticRepositoryImpl(
    private val service: StatisticService
) : StatisticRepository {

    override suspend fun getStatistic(): List<StatisticModel> {
        return try {
            service.getStatistic().statistics.map { it.toStatisticModel() }
        } catch (e: Exception) {
            // handle different network exceptions
            listOf()
        }
    }

    override suspend fun getTopVisitors(): List<TopVisitorsModel> {
        val visitorViews = mutableMapOf<Int, Int>()
        try {
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
        } catch (e: Exception) {
            //handle different network exceptions
            return listOf()
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
        try {
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
        } catch (e: Exception) {
            //handle different network exceptions
            return listOf()
        }
    }

    override suspend fun getSubscribers(): SubscribersModel {
        // assume current date is 09.09.2024
        val currentDate = LocalDate.of(2024, 9, 9)
        val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
        try {


            val correctDatesStatistic = getStatistic().map {
                it.dates.map { date ->
                    if (date.toString().length == 7) LocalDate.parse(
                        "0$date",
                        formatter
                    ) else LocalDate.parse(date.toString(), formatter)
                } to it.type
            }
            var subscribesCount = 0
            var unsubscribesCount = 0
            correctDatesStatistic.forEach {
                if (it.second == "subscription") {
                    it.first.forEach { date ->
                        if (date.month == currentDate.month) subscribesCount++
                    }
                }
            }

            correctDatesStatistic.forEach {
                if (it.second == "unsubscription") {
                    it.first.forEach { date ->
                        if (date.month == currentDate.month) unsubscribesCount++
                    }
                }
            }
            return SubscribersModel(subscribesCount, unsubscribesCount)
        } catch (e: Exception) {
            //handle different network exceptions
            return SubscribersModel(0, 0)
        }
    }
}