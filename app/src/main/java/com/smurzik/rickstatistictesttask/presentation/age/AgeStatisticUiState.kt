package com.smurzik.rickstatistictesttask.presentation.age

import com.smurzik.rickstatistictesttask.domain.models.SortType
import com.smurzik.rickstatistictesttask.domain.models.SortTypeAge
import com.smurzik.rickstatistictesttask.presentation.models.AgeStatisticUiModel
import com.smurzik.rickstatistictesttask.presentation.models.SexStatisticUiModel

data class AgeStatisticUiState(
    val sexStatistic: SexStatisticUiModel = SexStatisticUiModel(0, 0),
    val ageStatistic: List<AgeStatisticUiModel> = listOf(),
    val selectedSortType: SortType = SortTypeAge.TODAY
)