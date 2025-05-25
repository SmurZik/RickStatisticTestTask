package com.smurzik.rickstatistictesttask.presentation.age

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.rickstatistictesttask.domain.usecase.GetAgeStatisticUseCase
import com.smurzik.rickstatistictesttask.domain.models.SortType
import com.smurzik.rickstatistictesttask.domain.models.SortTypeAge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeStatisticViewModel @Inject constructor(
    private val getAgeStatisticUseCase: GetAgeStatisticUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AgeStatisticUiState())
    val uiState: StateFlow<AgeStatisticUiState> = _uiState

    private var job: Job? = null

    init {
        getAgeStatistic(isInit = true)
    }

    fun getAgeStatistic(sortType: SortType = SortTypeAge.TODAY, isInit: Boolean = false) {
        if (_uiState.value.selectedSortType != sortType || isInit) {
            if (job?.isActive == true) job?.cancel()
            _uiState.update {
                it.copy(selectedSortType = sortType)
            }
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            val result = getAgeStatisticUseCase(sortType)
            _uiState.update {
                it.copy(sexStatistic = result.first, ageStatistic = result.second)
            }
        }
    }
}