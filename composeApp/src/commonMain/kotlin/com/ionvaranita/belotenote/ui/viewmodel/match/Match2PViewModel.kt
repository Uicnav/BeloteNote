package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points2PRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.model.Points2PUi
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetLastPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints2PUseCase
import com.ionvaranita.belotenote.ui.match.BOLT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class Match2PPViewModel(private val appDatabase: AppDatabase, private val idGame: Int) :
    ViewModel() {
    private val repositoryGame =
        Games2PRepositoryImpl(Game2PDataSourceImpl(appDatabase.game2PDao()))
    private val getGameUseCase = GetGame2PUseCase(repositoryGame)
    private val repositoryPoints =
        Points2PRepositoryImpl(Points2PDataSourceImpl(appDatabase.points2PDao()))

    private val getPointsUseCase = GetPoints2PUseCase(repositoryPoints)
    private val getLastPoints2PUseCase = GetLastPoints2PUseCase(repositoryPoints)
    private val insertPointsUseCase = InsertPoints2PUseCase(repositoryPoints)
    private val deleteLastRowPoints2GroupsUseCase = DeleteLastRowPoints2PUseCase(repositoryPoints)

    private val _uiState = MutableStateFlow<Match2PUiState>(Match2PUiState.Loading)
    val uiState: StateFlow<Match2PUiState> = _uiState

    init {
        getMatchData()
    }

    var countBoltMe = 0
    var countBoltYouS = 0

    private fun getMatchData(dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        viewModelScope.launch(dispatcher) {

            combine(
                getGameUseCase.execute(idGame),
                getPointsUseCase.execute(idGame),
            ) { game, points ->
                countBoltMe = 0
                countBoltYouS = 0
                points.forEach { point ->
                    if (point.isBoltMe) {
                        val boltTtoUi = (countBoltMe % 2) + 1
                        point.pointsMe = BOLT + (boltTtoUi).toString()
                        ++countBoltMe
                    }
                    if (point.isBoltYouS) {
                        val boltTtoUi = (countBoltYouS % 2) + 1
                        point.pointsYouS = BOLT + (boltTtoUi).toString()
                        ++countBoltYouS
                    }
                }
                MatchData2P(game = game, points = points)
            }.catch { exception ->
                _uiState.value = Match2PUiState.Error(exception)
            }.collect { matchData ->
                _uiState.value = Match2PUiState.Success(matchData)
            }
        }

    var isMinus10Me = false
    var isMinus10YouS = false

    private var minus10Inserted = false


    fun insertPoints(
        model: Points2PUi, dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            if (model.pointsMe.equals(BOLT)) {
                if (!isMinus10Me && countBoltMe != 0 && countBoltMe % 2 == 0) {
                    isMinus10Me = true
                    model.pointsMe = "-10"
                    minus10Inserted = true
                } else {
                    isMinus10Me = false
                    model.isBoltMe = true
                }
            }
            if (model.pointsYouS.equals(BOLT)) {
                if (!isMinus10YouS && countBoltYouS != 0 && countBoltYouS % 2 == 0) {
                    isMinus10YouS = true
                    model.pointsYouS = "-10"
                } else {
                    isMinus10YouS = false
                    model.isBoltYouS = true
                }
            }
            var lastPoints = getLastPoints2PUseCase.execute(idGame)
            if (lastPoints == null) {
                lastPoints = Points2PEntity(idGame = idGame)
            }
            insertPointsUseCase.execute(model.add(lastPoints))
        }
    }

    fun deleteLastPoints(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            var lastPoints = getLastPoints2PUseCase.execute(idGame)
            if (lastPoints != null) {
                deleteLastRowPoints2GroupsUseCase.execute(lastPoints)
            }
        }

    }
}

data class MatchData2P(val game: Game2PUi, val points: List<Points2PUi>)

sealed class Match2PUiState {
    object Loading : Match2PUiState()
    data class Success(val data: MatchData2P) : Match2PUiState()
    data class Error(val exception: Throwable) : Match2PUiState()
}
