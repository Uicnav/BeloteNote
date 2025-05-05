package com.ionvaranita.belotenote.ui.viewmodel.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.datalayer.datasource.Game4PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.Games4PRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.domain.model.Game4PUi
import com.ionvaranita.belotenote.domain.usecase.GetGames4PUseCase
import com.ionvaranita.belotenote.domain.usecase.InsertGame4PUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Game4PViewModel(private val appDatabase: AppDatabase) : ViewModel() {
    val repository = Games4PRepositoryImpl(Game4PDataSourceImpl(appDatabase.game4PDao()))
    private var getGamesUseCase: GetGames4PUseCase = GetGames4PUseCase(repository)
    private var insertGameUseCase: InsertGame4PUseCase = InsertGame4PUseCase(repository)

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(Games4PUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Games4PUiState> = _uiState
    private fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGamesUseCase.execute(Unit).collect { gameList ->
            _uiState.value = Games4PUiState.Success(gameList)
        }
    }

    fun insertGame(game: Game4PEntity, dispatcher: CoroutineDispatcher = Dispatchers.IO) = viewModelScope.launch(dispatcher) {
        insertGameUseCase.execute(game)
    }

    init {
        getGames()
    }
}

sealed class Games3PUiState {
    data class Success(val data: List<Game3PUi>) : Games3PUiState()
    data class Error(val exception: Throwable) : Games3PUiState()
}