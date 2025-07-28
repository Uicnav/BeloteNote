package com.ionvaranita.belotenote.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.domain.model.WinningPointsUi
import com.ionvaranita.belotenote.domain.usecase.winningpoints.get.GetWinningPointsUseCase
import com.ionvaranita.belotenote.domain.usecase.winningpoints.insert.InsertWinningPointsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WinningPointsViewModel(
    private val getWinningPointsUseCase: GetWinningPointsUseCase,
    private val insertWinningPointsUseCase: InsertWinningPointsUseCase
) : ViewModel() {

    private val _winningPoints = MutableStateFlow(emptyList<WinningPointsUi>())
    val winningPoints: StateFlow<List<WinningPointsUi>> = _winningPoints

    suspend fun getWinningPoints(dispatcher: CoroutineDispatcher = Dispatchers.IO)  {
        _winningPoints.value = getWinningPointsUseCase.execute(Unit)
    }

    fun insertWinningPoints(winningPoints: WinningPointsUi, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            insertWinningPointsUseCase.execute(winningPoints)
        }
    }
}