package com.ionvaranita.belotenote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.datasource.Game2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.Games2PRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.usecase.GetGames2PUseCase
import com.ionvaranita.belotenote.domain.usecase.InsertGame2PUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Game2PViewModel(private val appDatabase: AppDatabase) : ViewModel() {
    private var getGames2PUseCase: GetGames2PUseCase = GetGames2PUseCase(Games2PRepositoryImpl(Game2PDataSourceImpl(appDatabase.game2PDao())))
    private var insertGame2PUseCase: InsertGame2PUseCase = InsertGame2PUseCase(Games2PRepositoryImpl(Game2PDataSourceImpl(appDatabase.game2PDao())))

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(Games2PUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Games2PUiState> = _uiState
    private fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGames2PUseCase.execute(Unit).collect { gameList ->
            _uiState.value = Games2PUiState.Success(gameList)
        }
    }

    fun insertGame(game2PUi: Game2PUi) = viewModelScope.launch(Dispatchers.IO) {
        insertGame2PUseCase.execute(game2PUi.toDataClass())
    }

    init {
        getGames()
    }
}

sealed class Games2PUiState {
    data class Success(val data: List<Game2PUi>) : Games2PUiState()
    data class Error(val exception: Throwable) : Games2PUiState()
}