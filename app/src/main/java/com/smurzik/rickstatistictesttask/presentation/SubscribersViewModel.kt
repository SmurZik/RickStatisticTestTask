package com.smurzik.rickstatistictesttask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.rickstatistictesttask.domain.GetSubscribersUseCase
import com.smurzik.rickstatistictesttask.domain.models.SubscribersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribersViewModel @Inject constructor(
    private val getSubscribersUseCase: GetSubscribersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscribersModel(0, 0))
    val uiState: StateFlow<SubscribersModel> = _uiState

    init {
        getSubscribers()
    }

    private fun getSubscribers() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                getSubscribersUseCase()
            }
        }
    }
}