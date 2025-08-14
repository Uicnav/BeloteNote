package com.ionvaranita.belotenote.ui.viewmodel.game

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.insert.InsertGame3PUseCase
import com.ionvaranita.belotenote.ui.GamePath
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Game3PViewModel(private val getGamesUseCase: GetGames3PUseCase, private val insertGameUseCase: InsertGame3PUseCase, private val deleteGameUseCase: DeleteGame3PUseCase,
                      override val prefs: DataStore<Preferences>,
                      override val gamePath: GamePath = GamePath.THREE
) : GameViewModelCommon() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<Games3PUiState>(Games3PUiState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Games3PUiState> = _uiState
    private fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGamesUseCase.execute(Unit).collect { gameList ->
            _uiState.value = Games3PUiState.Success(gameList.map { value -> if (value.idGame == gameToDelete?.idGame ) value.copy(isVisible = false) else value})
        }
    }

    private var gameToDelete: Game3PUi? = null

    suspend fun insertGame(game: Game3PEntity): Int {
        return insertGameUseCase.execute(game)
    }

    fun prepareDeleteGame(game: Game3PUi) {
        val currentList = _uiState.value as? Games3PUiState.Success ?: return
        val updatedList = currentList.data.map {
            if (it.idGame == game.idGame) {
                gameToDelete = it.copy(isVisible = false)
                gameToDelete!!
            } else{
                it
            }
        }
        updatedList.let {
            _uiState.value = Games3PUiState.Success(it)
        }
    }

    fun undoDeleteGame(game: Game3PUi) {
        gameToDelete?.let { gameToDelete->
            if (gameToDelete.idGame == game.idGame) {
                val currentList = _uiState.value as? Games3PUiState.Success ?: return
                val updatedList = currentList.data.map {
                    if (it.idGame == game.idGame) {
                        it.copy(isVisible = true)
                    } else{
                        it
                    }
                }
                updatedList.let {
                    _uiState.value = Games3PUiState.Success(it)
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

    fun deleteGame(idGame: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO) = viewModelScope.launch(dispatcher) {
        deleteGameUseCase.execute(idGame)
        deleteLastWinnerByIdGame(idGame)
    }
    init {
        getGames()
    }
}

sealed interface Games3PUiState {
    data object Loading : Games3PUiState
    data class Success(val data: List<Game3PUi>) : Games3PUiState
    data class Error(val exception: Throwable) : Games3PUiState
}