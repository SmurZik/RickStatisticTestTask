package com.smurzik.rickstatistictesttask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.rickstatistictesttask.domain.GetMonthlyVisitorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitorsViewModel @Inject constructor(
    private val getMonthlyVisitorsUseCase: GetMonthlyVisitorsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VisitorsUiState())
    val uiState: StateFlow<VisitorsUiState> = _uiState

    init {
        getVisitors()
    }

    private fun getVisitors() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(monthlyVisitors = getMonthlyVisitorsUseCase())
            }
        }
    }
}