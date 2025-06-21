package com.ionvaranita.belotenote.ui.viewmodel.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.datalayer.datasource.game.Game4PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games4PRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.insert.InsertGame4PUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Game4PViewModel(private val getGamesUseCase: GetGames4PUseCase, private val insertGameUseCase: InsertGame4PUseCase,private val deleteGameUseCase: DeleteGame4PUseCase) : ViewModel() {


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

    fun deleteGame(idGame: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO) = viewModelScope.launch(dispatcher) {
        deleteGameUseCase.execute(idGame)
    }

    init {
        getGames()
    }
}

sealed class Games3PUiState {
    data class Success(val data: List<Game3PUi>) : Games3PUiState()
    data class Error(val exception: Throwable) : Games3PUiState()
}