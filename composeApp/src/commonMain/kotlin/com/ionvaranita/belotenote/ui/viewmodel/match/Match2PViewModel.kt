package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2PRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2PUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Match2PPViewModel(private val appDatabase: AppDatabase) : ViewModel() {
    private val repositoryGame = Games2PRepositoryImpl(Game2PDataSourceImpl(appDatabase.game2PDao()))
    private val getGameUseCase = GetGame2PUseCase(repositoryGame)
    private val _uiState = MutableStateFlow<Match2PUiState>(Match2PUiState.Loading)
    val uiState: StateFlow<Match2PUiState> = _uiState

    fun getMatchData(idGame: Int) = viewModelScope.launch(Dispatchers.IO) {
        getGameUseCase.execute(idGame).catch {exception->
            _uiState.value = Match2PUiState.Error(exception)
        }.collect { data ->
            _uiState.value = Match2PUiState.Success(MatchData(game = data))
        }
    }
}

data class MatchData(val game: Game2PUi)

sealed class Match2PUiState {
    object Loading : Match2PUiState()
    data class Success(val data: MatchData) : Match2PUiState()
    data class Error(val exception: Throwable) : Match2PUiState()
}
