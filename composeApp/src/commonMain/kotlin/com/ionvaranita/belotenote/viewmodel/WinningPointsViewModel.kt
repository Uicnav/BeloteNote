package com.ionvaranita.belotenote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.datasource.WinningPointsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.WinningPointsRepositoryImpl
import com.ionvaranita.belotenote.domain.model.WinningPointsUi
import com.ionvaranita.belotenote.domain.usecase.GetWinningPointsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WinningPointsViewModel(appDatabase: AppDatabase) : ViewModel() {
    val repository = WinningPointsRepositoryImpl(WinningPointsDataSourceImpl(appDatabase.winnerPointsDao()))
    private var getWinningPointsUseCase: GetWinningPointsUseCase = GetWinningPointsUseCase(repository)

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(WinningPointsState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<WinningPointsState> = _uiState

    private fun getWinningPoints() = viewModelScope.launch(Dispatchers.IO) {
        getWinningPointsUseCase.execute(Unit).collect { gameList ->
            _uiState.value = WinningPointsState.Success(gameList)
        }
    }
    init {
        getWinningPoints()

    }
}

sealed class WinningPointsState {
    data class Success(val data: List<WinningPointsUi>) : WinningPointsState()
    data class Error(val exception: Throwable) : WinningPointsState()
}