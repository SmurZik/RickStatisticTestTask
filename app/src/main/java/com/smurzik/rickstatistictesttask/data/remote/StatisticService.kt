package com.smurzik.rickstatistictesttask.data.remote

import com.smurzik.rickstatistictesttask.data.remote.dto.StatisticDTO
import com.smurzik.rickstatistictesttask.data.remote.dto.UserDTO

interface StatisticService {

    suspend fun getStatistic(): StatisticDTO

    suspend fun getUsers(): UserDTO
}