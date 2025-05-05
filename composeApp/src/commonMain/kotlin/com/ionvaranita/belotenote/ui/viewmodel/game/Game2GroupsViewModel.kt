package com.ionvaranita.belotenote.ui.viewmodel.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.datasource.Game2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.Games2GroupsRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.usecase.GetGames2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.InsertGame2GroupsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Game2GroupsViewModel(private val appDatabase: AppDatabase) : ViewModel() {
    val repository = Games2GroupsRepositoryImpl(Game2GroupsDataSourceImpl(appDatabase.game2GroupsDao()))
    private var getGamesUseCase: GetGames2GroupsUseCase = GetGames2GroupsUseCase(repository)
    private var insertGameUseCase: InsertGame2GroupsUseCase = InsertGame2GroupsUseCase(repository)

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(Games2GroupsUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<Games2GroupsUiState> = _uiState
    private fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGamesUseCase.execute(Unit).collect { gameList ->
            _uiState.value = Games2GroupsUiState.Success(gameList)
        }
    }
    //TODO for testing coroutine
    fun insertGame(game: Game2GroupsEntity, dispatcher: CoroutineDispatcher = Dispatchers.IO) = viewModelScope.launch(dispatcher) {
        insertGameUseCase.execute(game)
    }

    init {
        getGames()
    }
}

sealed class Games2GroupsUiState {
    data class Success(val data: List<Game2GroupsUi>) : Games2GroupsUiState()
    data class Error(val exception: Throwable) : Games2GroupsUiState()
}