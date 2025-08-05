package com.ionvaranita.belotenote.ui.viewmodel.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.model.Game4PUi
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.insert.InsertGame4PUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Game4PViewModel(
    private val getGamesUseCase: GetGames4PUseCase,
    private val insertGameUseCase: InsertGame4PUseCase,
    private val deleteGameUseCase: DeleteGame4PUseCase
) : ViewModel() {


    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<Games4PUiState>(Games4PUiState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Games4PUiState> = _uiState

    private fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGamesUseCase.execute(Unit).collect { gameList ->
            _uiState.value = Games4PUiState.Success(gameList.map { value ->
                if (value.idGame == gameToDelete?.idGame) value.copy(isVisible = false) else value
            })
        }
    }

    private var gameToDelete: Game4PUi? = null

    suspend fun insertGame(game: Game4PEntity): Int {
        return insertGameUseCase.execute(game)
    }

    fun prepareDeleteGame(game: Game4PUi) {
        val currentList = _uiState.value as? Games4PUiState.Success ?: return
        val updatedList = currentList.data.map {
            if (it.idGame == game.idGame) {
                gameToDelete = it.copy(isVisible = false)
                gameToDelete!!
            } else {
                it
            }
        }
        updatedList.let {
            _uiState.value = Games4PUiState.Success(it)
        }
    }

    fun undoDeleteGame(game: Game4PUi) {
        gameToDelete?.let { gameToDelete ->
            if (gameToDelete.idGame == game.idGame) {
                val currentList = _uiState.value as? Games4PUiState.Success ?: return
                val updatedList = currentList.data.map {
                    if (it.idGame == game.idGame) {
                        it.copy(isVisible = true)
                    } else {
                        it
                    }
                }
                updatedList.let {
                    _uiState.value = Games4PUiState.Success(it)
                }
                this.gameToDelete = null
            }
        }
    }

    fun deleteGameToDelete() {
        gameToDelete?.let {
            deleteGame(it.idGame)
        }
        gameToDelete = null
    }

    fun deleteGame(idGame: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        viewModelScope.launch(dispatcher) {
            deleteGameUseCase.execute(idGame)
        }


    init {
        getGames()
    }
}

sealed interface Games4PUiState {
    data object Loading : Games4PUiState
    data class Success(val data: List<Game4PUi>) : Games4PUiState
    data class Error(val exception: Throwable) : Games4PUiState
}