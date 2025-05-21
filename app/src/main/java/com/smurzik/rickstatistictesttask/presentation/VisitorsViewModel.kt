package com.smurzik.rickstatistictesttask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.rickstatistictesttask.domain.GetMonthlyVisitorsUseCase
import com.smurzik.rickstatistictesttask.domain.GetVisitorsChartUseCase
import com.smurzik.rickstatistictesttask.domain.models.SortType
import com.smurzik.rickstatistictesttask.ui.components.visits.DataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitorsViewModel @Inject constructor(
    private val getMonthlyVisitorsUseCase: GetMonthlyVisitorsUseCase,
    private val getVisitorsChartUseCase: GetVisitorsChartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VisitorsUiState())
    val uiState: StateFlow<VisitorsUiState> = _uiState

    init {
        getVisitors()
        getVisitorsChart()
    }

    private fun getVisitors() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(monthlyVisitors = getMonthlyVisitorsUseCase())
            }
        }
    }

    fun getVisitorsChart(sortType: SortType = SortType.DAYS) {
        _uiState.update {
            it.copy(loadingChart = true, selectedSortType = sortType)
        }
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { state ->
                state.copy(
                    chartVisitors = getVisitorsChartUseCase(sortType).map {
                        DataPoint(it.key, it.value.toFloat())
                    },
                    loadingChart = false
                )
            }
        }
    }
}