package com.ionvaranita.belotenote.ui.viewmodel.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.insert.InsertGame2PUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Game2PViewModel(private val getGamesUseCase: GetGames2PUseCase, private val insertGameUseCase: InsertGame2PUseCase, private val deleteGameUseCase: DeleteGame2PUseCase) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<Games2PUiState>(Games2PUiState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Games2PUiState> = _uiState
    private fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGamesUseCase.execute(Unit).collect { gameList ->
            _uiState.value = Games2PUiState.Success(gameList)
        }
    }

    private var gameToDelete: Game2PUi? = null

    suspend fun insertGame(game: Game2PEntity): Int {
        return insertGameUseCase.execute(game)
    }

    fun prepareDeleteGame(game: Game2PUi, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        game.isVisible = false
        gameToDelete = game
    }

    fun prepareDeleteGame(game: Game2PUi) {
        val currentList = _uiState.value as? Games2PUiState.Success ?: return
        val updatedList = currentList.data.map {
            if (it.idGame == game.idGame) {
                gameToDelete = it.copy(isVisible = false)
                gameToDelete!!
            } else{
                it
            }
        }
        updatedList.let {
            _uiState.value = Games2PUiState.Success(it)
        }
    }

    fun undoDeleteGame(game: Game2PUi) {
        gameToDelete?.let { gameToDelete->
            if (gameToDelete.idGame == game.idGame) {
                val currentList = _uiState.value as? Games2PUiState.Success ?: return
                val updatedList = currentList.data.map {
                    if (it.idGame == game.idGame) {
                        it.copy(isVisible = true)
                    } else{
                        it
                    }
                }
                updatedList.let {
                    _uiState.value = Games2PUiState.Success(it)
                }
                this.gameToDelete = null
            } else {
                deleteGame((gameToDelete.idGame))
            }
        }
    }

    fun deleteGameToDelete() {
        gameToDelete?.let {
            deleteGame(it.idGame)
        }
        gameToDelete = null
    }

    fun deleteGame(idGame: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO) = viewModelScope.launch(dispatcher) {
        deleteGameUseCase.execute(idGame)
    }

    init {
        getGames()
    }
}

sealed interface Games2PUiState {
    data object Loading : Games2PUiState
    data class Success(val data: List<Game2PUi>) : Games2PUiState
    data class Error(val exception: Throwable) : Games2PUiState
}