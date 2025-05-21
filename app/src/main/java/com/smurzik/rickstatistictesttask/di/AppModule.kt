package com.smurzik.rickstatistictesttask.di

import com.smurzik.rickstatistictesttask.data.StatisticRepositoryImpl
import com.smurzik.rickstatistictesttask.data.remote.StatisticService
import com.smurzik.rickstatistictesttask.data.remote.StatisticServiceImpl
import com.smurzik.rickstatistictesttask.domain.GetMonthlyVisitorsUseCase
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
                    json()
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
}