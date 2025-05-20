package com.smurzik.rickstatistictesttask.data.remote

import com.smurzik.rickstatistictesttask.data.remote.dto.StatisticDTO

interface StatisticService {

    suspend fun getStatistic(): StatisticDTO
}