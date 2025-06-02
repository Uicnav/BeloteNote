package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import com.ionvaranita.belotenote.datalayer.datasource.game.Game3PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points3PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games3PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points3PRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.domain.model.Points3PUi
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetLastPoints3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints3PUseCase
import com.ionvaranita.belotenote.ui.match.BOLT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class Match3PPViewModel(private val appDatabase: AppDatabase, private val idGame: Int) :
    ViewModel() {
    private val repositoryGame =
        Games3PRepositoryImpl(Game3PDataSourceImpl(appDatabase.game3PDao()))
    private val getGameUseCase = GetGame3PUseCase(repositoryGame)
    private val repositoryPoints =
        Points3PRepositoryImpl(Points3PDataSourceImpl(appDatabase.points3PDao()))

    private val getPointsUseCase = GetPoints3PUseCase(repositoryPoints)
    private val getLastUseCase = GetLastPoints3PUseCase(repositoryPoints)
    private val insertPointsUseCase = InsertPoints3PUseCase(repositoryPoints)
    private val deleteLastRowPoints2GroupsUseCase = DeleteLastRowPoints3PUseCase(repositoryPoints)

    private val _uiState = MutableStateFlow<Match3PUiState>(Match3PUiState.Loading)
    val uiState: StateFlow<Match3PUiState> = _uiState

    init {
        getMatchData()
    }

    var countBoltP1 = 0
    var countBoltP2 = 0
    var countBoltP3 = 0

    private fun getMatchData(dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        viewModelScope.launch(dispatcher) {

            combine(
                getGameUseCase.execute(idGame),
                getPointsUseCase.execute(idGame),
            ) { game, points ->
                countBoltP1 = 0
                countBoltP2 = 0
                countBoltP3 = 0
                points.forEach { point ->
                    if (point.isBoltP1) {
                        val boltTtoUi = (countBoltP1 % 2) + 1
                        point.pointsP1 = BOLT + (boltTtoUi).toString()
                        ++countBoltP1
                    }
                    if (point.isBoltP2) {
                        val boltTtoUi = (countBoltP2 % 2) + 1
                        point.pointsP2 = BOLT + (boltTtoUi).toString()
                        ++countBoltP2
                    }
                    if (point.isBoltP3) {
                        val boltTtoUi = (countBoltP3 % 2) + 1
                        point.pointsP3 = BOLT + (boltTtoUi).toString()
                        ++countBoltP3
                    }
                }
                MatchData3P(game = game, points = points)
            }.catch { exception ->
                _uiState.value = Match3PUiState.Error(exception)
            }.collect { matchData ->
                _uiState.value = Match3PUiState.Success(matchData)
            }
        }

    private var isMinus10P1 = false
    private var isMinus10P2 = false
    private var isMinus10P3 = false

    private var minus10Inserted = false


    fun insertPoints(
        model: Points3PUi, dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            if (model.pointsP1 == BOLT) {
                if (!isMinus10P1 && countBoltP1 != 0 && countBoltP1 % 2 == 0) {
                    isMinus10P1 = true
                    model.pointsP1 = "-10"
                    minus10Inserted = true
                } else {
                    isMinus10P1 = false
                    model.isBoltP1 = true
                }
            }
            if (model.pointsP2 == BOLT) {
                if (!isMinus10P2 && countBoltP2 != 0 && countBoltP2 % 2 == 0) {
                    isMinus10P2 = true
                    model.pointsP2 = "-10"
                } else {
                    isMinus10P2 = false
                    model.isBoltP2 = true
                }
            }
            if (model.pointsP3 == BOLT) {
                if (!isMinus10P3 && countBoltP3 != 0 && countBoltP3 % 2 == 0) {
                    isMinus10P3 = true
                    model.pointsP3 = "-10"
                } else {
                    isMinus10P3 = false
                    model.isBoltP3 = true
                }
            }
            var lastPoints = getLastUseCase.execute(idGame)
            if (lastPoints == null) {
                lastPoints = Points3PEntity(idGame = idGame)
            }
            insertPointsUseCase.execute(model.add(lastPoints))
        }
    }

    fun deleteLastPoints(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            var lastPoints = getLastUseCase.execute(idGame)
            if (lastPoints != null) {
                deleteLastRowPoints2GroupsUseCase.execute(lastPoints)
            }
        }

    }
}

data class MatchData3P(val game: Game3PUi, val points: List<Points3PUi>)

sealed class Match3PUiState {
    object Loading : Match3PUiState()
    data class Success(val data: MatchData3P) : Match3PUiState()
    data class Error(val exception: Throwable) : Match3PUiState()
}
