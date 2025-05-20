package com.smurzik.rickstatistictesttask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.rickstatistictesttask.domain.GetStatisticUseCase
import com.smurzik.rickstatistictesttask.domain.models.StatisticModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitorsViewModel @Inject constructor(
    private val getStatisticUseCase: GetStatisticUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(listOf<StatisticModel>())
    val uiState: StateFlow<List<StatisticModel>> = _uiState

    fun getVisitors() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = getStatisticUseCase()
        }
    }
}