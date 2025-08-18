package com.ionvaranita.belotenote.ui.viewmodel.game

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.insert.InsertGame2GroupsUseCase
import com.ionvaranita.belotenote.ui.GamePath
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Game2GroupsViewModel(private val getGamesUseCase: GetGames2GroupsUseCase, private val insertGameUseCase: InsertGame2GroupsUseCase, private val deleteGameUseCase: DeleteGame2GroupsUseCase,
                           override val prefs: DataStore<Preferences>,
                           override val gamePath: GamePath = GamePath.GROUP
) : GameViewModelCommon() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<Games2GroupsUiState>(Games2GroupsUiState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Games2GroupsUiState> = _uiState
    private fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGamesUseCase.execute(Unit).collect { gameList ->
            _uiState.value = Games2GroupsUiState.Success(gameList.map { value -> if (value.idGame == gameToDelete?.idGame ) value.copy(isVisible = false) else value})
        }
    }

    private var gameToDelete: Game2GroupsUi? = null

    suspend fun insertGame(game: Game2GroupsEntity): Int {
        return insertGameUseCase.execute(game)
    }

    fun prepareDeleteGame(game: Game2GroupsUi) {
        val currentList = _uiState.value as? Games2GroupsUiState.Success ?: return
        val updatedList = currentList.data.map {
            if (it.idGame == game.idGame) {
                gameToDelete = it.copy(isVisible = false)
                gameToDelete!!
            } else{
                it
            }
        }
        updatedList.let {
            _uiState.value = Games2GroupsUiState.Success(it)
        }
    }

    fun undoDeleteGame(game: Game2GroupsUi) {
        gameToDelete?.let { gameToDelete->
            if (gameToDelete.idGame == game.idGame) {
                val currentList = _uiState.value as? Games2GroupsUiState.Success ?: return
                val updatedList = currentList.data.map {
                    if (it.idGame == game.idGame) {
                        it.copy(isVisible = true)
                    } else{
                        it
                    }
                }
                updatedList.let {
                    _uiState.value = Games2GroupsUiState.Success(it)
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

sealed interface Games2GroupsUiState {
    data object Loading : Games2GroupsUiState
    data class Success(val data: List<Game2GroupsUi>) : Games2GroupsUiState
    data class Error(val exception: Throwable) : Games2GroupsUiState
}