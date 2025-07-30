package com.ionvaranita.belotenote.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.domain.model.WinningPointsUi
import com.ionvaranita.belotenote.domain.usecase.winningpoints.get.GetWinningPointsUseCase
import com.ionvaranita.belotenote.domain.usecase.winningpoints.insert.InsertWinningPointsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WinningPointsViewModel(
    private val getWinningPointsUseCase: GetWinningPointsUseCase,
    private val insertWinningPointsUseCase: InsertWinningPointsUseCase
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val winningPoints: StateFlow<List<WinningPointsUi>> = flowOf(Unit)
        .flatMapLatest { getWinningPointsUseCase.execute(Unit) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertWinningPoints(winningPoints: WinningPointsUi, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            insertWinningPointsUseCase.execute(winningPoints)
        }
    }
}
