package com.ionvaranita.belotenote.ui.viewmodel.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2PRepositoryImpl
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

class Game2PViewModel(private val appDatabase: AppDatabase) : ViewModel() {
    val repositoryGame = Games2PRepositoryImpl(Game2PDataSourceImpl(appDatabase.game2PDao()))
    private var getGamesUseCase: GetGames2PUseCase = GetGames2PUseCase(repositoryGame)
    private var insertGameUseCase: InsertGame2PUseCase = InsertGame2PUseCase(repositoryGame)
    private val deleteGameUseCase: DeleteGame2PUseCase = DeleteGame2PUseCase(repositoryGame)

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(Games2PUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Games2PUiState> = _uiState
    private fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGamesUseCase.execute(Unit).collect { gameList ->
            _uiState.value = Games2PUiState.Success(gameList)
        }
    }

    //TODO for testing coroutine
    fun insertGame(game: Game2PEntity, dispatcher: CoroutineDispatcher = Dispatchers.IO) = viewModelScope.launch(dispatcher) {
        insertGameUseCase.execute(game)
    }

    fun deleteGame(idGame: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO) = viewModelScope.launch(dispatcher) {
        deleteGameUseCase.execute(idGame)
    }

    init {
        getGames()
    }
}

sealed class Games2PUiState {
    data class Success(val data: List<Game2PUi>) : Games2PUiState()
    data class Error(val exception: Throwable) : Games2PUiState()
}