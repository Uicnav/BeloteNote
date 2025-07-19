package com.ionvaranita.belotenote.ui.viewmodel.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.insert.InsertGame3PUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Game3PViewModel(private val getGamesUseCase: GetGames3PUseCase, private val insertGameUseCase: InsertGame3PUseCase,private val deleteGameUseCase: DeleteGame3PUseCase ) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(Games3PUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Games3PUiState> = _uiState
    private fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGamesUseCase.execute(Unit).collect { gameList ->
            _uiState.value = Games3PUiState.Success(gameList)
        }
    }

    suspend fun insertGame(game: Game3PEntity): Int {
        return insertGameUseCase.execute(game)
    }
    fun deleteGame(idGame: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO) = viewModelScope.launch(dispatcher) {
        deleteGameUseCase.execute(idGame)
    }
    init {
        getGames()
    }
}

sealed interface Games3PUiState {
    object Loading : Games3PUiState
    data class Success(val data: List<Game3PUi>) : Games3PUiState
    data class Error(val exception: Throwable) : Games3PUiState
}