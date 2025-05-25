package com.smurzik.rickstatistictesttask.domain.usecase

import com.smurzik.rickstatistictesttask.domain.StatisticRepository
import com.smurzik.rickstatistictesttask.domain.models.AgeStatisticModel
import com.smurzik.rickstatistictesttask.domain.models.SortType
import com.smurzik.rickstatistictesttask.domain.models.SortTypeAge
import com.smurzik.rickstatistictesttask.presentation.models.AgeStatisticUiModel
import com.smurzik.rickstatistictesttask.presentation.models.SexStatisticUiModel
import kotlin.math.ceil

class GetAgeStatisticUseCase(
    private val repository: StatisticRepository
) {

    suspend operator fun invoke(sortType: SortType): Pair<SexStatisticUiModel, List<AgeStatisticUiModel>> {
        var maleCount = 0
        var femaleCount = 0
        val ageStatistic = repository.getAgeStatistic(sortType as SortTypeAge)
        ageStatistic.forEach {
            if (it.isMale) maleCount += it.viewCount
            else femaleCount += it.viewCount
        }
        val totalCount = maleCount + femaleCount
        val sexStatistic = SexStatisticUiModel(
            maleCount * 100 / (maleCount + femaleCount),
            ceil(femaleCount * 100.0 / (maleCount + femaleCount)).toInt()
        )

        val resultStatistic = mutableListOf<AgeStatisticUiModel>()
        val ageGroups = listOf(
            Pair(18, 21),
            Pair(22, 25),
            Pair(26, 30),
            Pair(31, 35),
            Pair(36, 40),
            Pair(40, 50)
        )

        ageGroups.forEach {
            val groupViewCount =
                getGroupViewCount(startAge = it.first, endAge = it.second, statistic = ageStatistic)
            resultStatistic.add(
                AgeStatisticUiModel(
                    ageStart = it.first,
                    ageEnd = it.second,
                    maleCount = groupViewCount.first,
                    femaleCount = groupViewCount.second,
                    totalCount = totalCount
                )
            )
        }
        val lastGroupViewCount =
            getGroupViewCount(startAge = 50, isLast = true, statistic = ageStatistic)
        resultStatistic.add(
            AgeStatisticUiModel(
                50,
                0,
                lastGroupViewCount.first,
                lastGroupViewCount.second,
                true,
                totalCount = totalCount
            )
        )
        return sexStatistic to resultStatistic
    }

    private fun getGroupViewCount(
        startAge: Int,
        endAge: Int = 0,
        isLast: Boolean = false,
        statistic: List<AgeStatisticModel>
    ): Pair<Int, Int> {
        var maleCount = 0
        var femaleCount = 0
        val ageGroup =
            if (!isLast) statistic.filter { it.age in startAge..endAge } else statistic.filter { it.age > startAge }
        ageGroup.filter { it.isMale }.forEach {
            maleCount += it.viewCount
        }
        ageGroup.filter { !it.isMale }.forEach {
            femaleCount += it.viewCount
        }
        return maleCount to femaleCount
    }
}