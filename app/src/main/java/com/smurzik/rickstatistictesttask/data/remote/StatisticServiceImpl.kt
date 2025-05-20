package com.smurzik.rickstatistictesttask.data.remote

import com.smurzik.rickstatistictesttask.data.remote.dto.StatisticDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class StatisticServiceImpl(
    private val client: HttpClient
) : StatisticService {

    override suspend fun getStatistic(): StatisticDTO {
        return client.get { url(HttpRoutes.STATISTICS) }.body()
    }
}