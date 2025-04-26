package com.ionvaranita.belotenote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.datasource.Game3PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.Games3PRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.domain.model.Game4PUi
import com.ionvaranita.belotenote.domain.usecase.GetGames3PUseCase
import com.ionvaranita.belotenote.domain.usecase.InsertGame3PUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Game3PViewModel(private val appDatabase: AppDatabase) : ViewModel() {
    private var getGames3PUseCase: GetGames3PUseCase = GetGames3PUseCase(Games3PRepositoryImpl(Game3PDataSourceImpl(appDatabase.game3PDao())))
    private var insertGame3PUseCase: InsertGame3PUseCase = InsertGame3PUseCase(Games3PRepositoryImpl(Game3PDataSourceImpl(appDatabase.game3PDao())))

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(Games3PUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Games3PUiState> = _uiState
    private fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGames3PUseCase.execute(Unit).collect { gameList ->
            _uiState.value = Games3PUiState.Success(gameList)
        }
    }

    fun insertGame(game: Game3PUi) = viewModelScope.launch(Dispatchers.IO) {
        insertGame3PUseCase.execute(game.toDataClass())
    }

    init {
        getGames()
    }
}

sealed class Games4PUiState {
    data class Success(val data: List<Game4PUi>) : Games4PUiState()
    data class Error(val exception: Throwable) : Games4PUiState()
}