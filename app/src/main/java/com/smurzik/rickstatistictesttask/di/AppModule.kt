package com.smurzik.rickstatistictesttask.di

import com.smurzik.rickstatistictesttask.data.StatisticRepositoryImpl
import com.smurzik.rickstatistictesttask.data.remote.StatisticService
import com.smurzik.rickstatistictesttask.data.remote.StatisticServiceImpl
import com.smurzik.rickstatistictesttask.domain.GetAgeStatisticUseCase
import com.smurzik.rickstatistictesttask.domain.GetMonthlyVisitorsUseCase
import com.smurzik.rickstatistictesttask.domain.GetSubscribersUseCase
import com.smurzik.rickstatistictesttask.domain.GetTopVisitorsUseCase
import com.smurzik.rickstatistictesttask.domain.GetVisitorsChartUseCase
import com.smurzik.rickstatistictesttask.domain.StatisticRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorApi(): StatisticService {
        return StatisticServiceImpl(
            client = HttpClient(Android) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
            }
        )
    }

    @Provides
    @Singleton
    fun provideStatisticRepository(service: StatisticService): StatisticRepository {
        return StatisticRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideGetMonthlyVisitorsUseCase(repository: StatisticRepository): GetMonthlyVisitorsUseCase {
        return GetMonthlyVisitorsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetVisitorsChartUseCase(repository: StatisticRepository): GetVisitorsChartUseCase {
        return GetVisitorsChartUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTopVisitorsUseCase(repository: StatisticRepository): GetTopVisitorsUseCase {
        return GetTopVisitorsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAgeStatisticUseCase(repository: StatisticRepository): GetAgeStatisticUseCase {
        return GetAgeStatisticUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSubscribersUseCase(repository: StatisticRepository): GetSubscribersUseCase {
        return GetSubscribersUseCase(repository)
    }
}