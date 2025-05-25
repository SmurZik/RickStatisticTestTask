package com.smurzik.rickstatistictesttask.presentation.topvisitors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.rickstatistictesttask.domain.usecase.GetTopVisitorsUseCase
import com.smurzik.rickstatistictesttask.domain.models.TopVisitorsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopVisitorsViewModel @Inject constructor(
    private val getTopVisitorsUseCase: GetTopVisitorsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(listOf<TopVisitorsModel>())
    val uiState: StateFlow<List<TopVisitorsModel>> = _uiState

    init {
        getTopVisitors()
    }

    private fun getTopVisitors() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                getTopVisitorsUseCase()
            }
        }
    }
}