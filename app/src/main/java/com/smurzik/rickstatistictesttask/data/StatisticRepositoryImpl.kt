package com.smurzik.rickstatistictesttask.data

import android.util.Log
import com.smurzik.rickstatistictesttask.data.remote.StatisticService
import com.smurzik.rickstatistictesttask.domain.StatisticRepository
import com.smurzik.rickstatistictesttask.domain.models.StatisticModel
import com.smurzik.rickstatistictesttask.domain.models.TopVisitorsModel

class StatisticRepositoryImpl(
    private val service: StatisticService
) : StatisticRepository {

    override suspend fun getStatistic(): List<StatisticModel> {
        return service.getStatistic().statistics.map { it.toStatisticModel() }
    }

    override suspend fun getTopVisitors(): List<TopVisitorsModel> {
        Log.d("smurzLog", "start")
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
}