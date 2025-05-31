package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2GroupsRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points2GroupsRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetLastPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.delete.DeleteLastRowPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.insert.InsertPoints2GroupsUseCase
import com.ionvaranita.belotenote.ui.match.BOLT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class Match2GroupsViewModel(private val appDatabase: AppDatabase, private val idGame: Int) :
    ViewModel() {
    private val repositoryGame =
        Games2GroupsRepositoryImpl(Game2GroupsDataSourceImpl(appDatabase.game2GroupsDao()))
    private val repositoryPoints =
        Points2GroupsRepositoryImpl(Points2GroupsDataSourceImpl(appDatabase.points2GroupsDao()))
    private val getGameUseCase = GetGame2GroupsUseCase(repositoryGame)
    private val getPointsUseCase = GetPoints2GroupsUseCase(repositoryPoints)
    private val getLastPoints2GroupsUseCase = GetLastPoints2GroupsUseCase(repositoryPoints)
    private val insertPointsUseCase = InsertPoints2GroupsUseCase(repositoryPoints)
    private val deleteLastRowPoints2GroupsUseCase =
        DeleteLastRowPoints2GroupsUseCase(repositoryPoints)

    private val _uiState = MutableStateFlow<MatchGroupsUiState>(MatchGroupsUiState.Loading)
    val uiState: StateFlow<MatchGroupsUiState> = _uiState

    init {
        getMatchData()
    }

    var countBoltWe = 0
    var countBoltYouP = 0

    private fun getMatchData(dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        viewModelScope.launch(dispatcher) {

            combine(
                getGameUseCase.execute(idGame),
                getPointsUseCase.execute(idGame),
            ) { game, points ->
                countBoltWe = 0
                countBoltYouP = 0
                points.forEach { point ->
                    if (point.boltWe) {
                        val boltTtoUi = (countBoltWe % 2) + 1
                        point.pointsWe = BOLT + (boltTtoUi).toString()
                        ++countBoltWe
                    }
                    if (point.boltYouP) {
                        val boltTtoUi = (countBoltYouP % 2) + 1
                        point.pointsYouP = BOLT + (boltTtoUi).toString()
                        ++countBoltYouP
                    }
                }
                MatchData2Groups(game = game, points = points)
            }.catch { exception ->
                _uiState.value = MatchGroupsUiState.Error(exception)
            }.collect { matchData ->
                _uiState.value = MatchGroupsUiState.Success(matchData)
            }
        }

    var isMinus10We = false
    var isMinus10YouP = false

    private var minus10Inserted = false


    fun insertPoints(
        model: Points2GroupsUi, dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            if (model.pointsWe.equals(BOLT)) {
                if (!isMinus10We && countBoltWe != 0 && countBoltWe % 2 == 0) {
                    isMinus10We = true
                    model.pointsWe = "-10"
                    minus10Inserted = true
                } else {
                    isMinus10We = false
                    model.boltWe = true
                }
            }
            if (model.pointsYouP.equals(BOLT)) {
                if (!isMinus10YouP && countBoltYouP != 0 && countBoltYouP % 2 == 0) {
                    isMinus10YouP = true
                    model.pointsYouP = "-10"
                } else {
                    isMinus10YouP = false
                    model.boltYouP = true
                }
            }
            var lastPoints = getLastPoints2GroupsUseCase.execute(idGame)
            if (lastPoints == null) {
                lastPoints = Points2GroupsEntity(idGame = idGame)
            }
            insertPointsUseCase.execute(model.toDataClass(lastPoints))
        }
    }

    fun deleteLastPoints(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            var lastPoints = getLastPoints2GroupsUseCase.execute(idGame)
            if (lastPoints != null) {
                deleteLastRowPoints2GroupsUseCase.execute(lastPoints)
            }
        }

    }
}

data class MatchData2Groups(val game: Game2GroupsUi, val points: List<Points2GroupsUi>)

sealed class MatchGroupsUiState {
    data object Loading : MatchGroupsUiState()
    data class Success(val data: MatchData2Groups) : MatchGroupsUiState()
    data class Error(val exception: Throwable) : MatchGroupsUiState()
}
