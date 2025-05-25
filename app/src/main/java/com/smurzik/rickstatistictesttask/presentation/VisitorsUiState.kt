package com.smurzik.rickstatistictesttask.presentation

import com.smurzik.rickstatistictesttask.domain.models.SortType
import com.smurzik.rickstatistictesttask.domain.models.SortTypeVisitors
import com.smurzik.rickstatistictesttask.ui.components.visits.DataPoint

data class VisitorsUiState(
    val monthlyVisitors: Int = 0,
    val chartVisitors: List<DataPoint> = listOf(DataPoint("", 0f)),
    val selectedSortType: SortType = SortTypeVisitors.DAYS,
    val loadingChart: Boolean = false
)