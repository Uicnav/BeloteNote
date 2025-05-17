package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2GroupsRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points2GroupsRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.repo.match.Points2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.insert.InsertPoints2GroupsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Match2GroupsViewModel(private val appDatabase: AppDatabase, private val idGame: Int) : ViewModel() {
    private val repositoryGame = Games2GroupsRepositoryImpl(Game2GroupsDataSourceImpl(appDatabase.game2GroupsDao()))
    private val repositoryPoints = Points2GroupsRepositoryImpl(Points2GroupsDataSourceImpl(appDatabase.points2GroupsDao()))
    private val getGameUseCase = GetGame2GroupsUseCase(repositoryGame)
    private val getPointsUseCase = GetPoints2GroupsUseCase(repositoryPoints)
    private val insertPointsUseCase = InsertPoints2GroupsUseCase(repositoryPoints)
    private val _uiState = MutableStateFlow<MatchGroupsUiState>(MatchGroupsUiState.Loading)
    val uiState: StateFlow<MatchGroupsUiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            insertPointsUseCase.execute(points = Points2GroupsEntity(idGame =idGame, pointsWe = 101, pointsYouP = 200, pointsGame = 300))
        }
    }

    fun getMatchData() = viewModelScope.launch(Dispatchers.IO) {
        getGameUseCase.execute(idGame).combine(getPointsUseCase.execute(idGame)) { game, points ->
            MatchData2Groups(game = game, points = points)
        }.catch { exception ->
            _uiState.value = MatchGroupsUiState.Error(exception)
        }.collect { matchData ->
            _uiState.value = MatchGroupsUiState.Success(matchData)
        }
    }
}

data class MatchData2Groups(val game: Game2GroupsUi, val points: List<Points2GroupsUi>)

sealed class MatchGroupsUiState {
    data object Loading : MatchGroupsUiState()
    data class Success(val data: MatchData2Groups) : MatchGroupsUiState()
    data class Error(val exception: Throwable) : MatchGroupsUiState()
}
