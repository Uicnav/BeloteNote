package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateOnlyStatusGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusWinningPointsGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusAndScoreGameParams
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points2PRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.model.Points2PUi
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateOnlyStatusGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateOnlyStatusGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName1Game2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName2Game2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetLastPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints2PUseCase
import com.ionvaranita.belotenote.ui.match.BOLT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

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

    private val updateStatusScoreName1Game2PUseCase =
        UpdateStatusScoreName1Game2PUseCase(repositoryGame)
    private val updateStatusScoreName2Game2PUseCase =
        UpdateStatusScoreName2Game2PUseCase(repositoryGame)

    private val updateStatusWinningPointsGame2PUseCase = UpdateStatusWinningPointsGame2PUseCase(repositoryGame)
    private val updateOnlyStatusGame2PUseCase = UpdateOnlyStatusGame2PUseCase(repositoryGame)
    private val deleteAllPoints2PUseCase = DeleteAllPoints2PUseCase(repositoryPoints)

    private val _uiState = MutableStateFlow<Match2PUiState>(Match2PUiState.Loading)
    val uiState: StateFlow<Match2PUiState> = _uiState
    private var winningPoints by Delegates.notNull<Short>()
    private var scoreName1 by Delegates.notNull<Short>()
    private var scoreName2 by Delegates.notNull<Short>()
    private var name1 by Delegates.notNull<String>()
    private var name2 by Delegates.notNull<String>()
    private val _statusGame = mutableStateOf(GameStatus.CONTINUE)
    val statusGame: State<GameStatus> = _statusGame


    init {
        getMatchData()
    }

    private var countBoltMe = 0
    private var countBoltYouS = 0


    private fun getMatchData(dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        viewModelScope.launch(dispatcher) {

            combine(
                getGameUseCase.execute(idGame),
                getPointsUseCase.execute(idGame),
            ) { game, points ->
                winningPoints = game.winningPoints
                scoreName1 = game.scoreName1
                scoreName2 = game.scoreName2
                name1 = game.name1
                name2 = game.name2
                _statusGame.value = GameStatus.fromId(game.statusGame)!!
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

    private var isMinus10Me = false
    private var isMinus10YouS = false

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
            val updatedModel = model.add(lastPoints)
            val pointsMe = updatedModel.pointsMe
            val pointsYouS = updatedModel.pointsYouS
            if (pointsMe >= winningPoints) {
                if (pointsMe > pointsYouS) {
                    updateStatusScoreName1Game2PUseCase.execute(UpdateStatusAndScoreGameParams(idGame= idGame, score = scoreName1.plus(1).toShort()))
                    _oneTimeEvent.emit(SideEffect.ShowWinner(message = name1))
                }
            }
            if (pointsYouS >= winningPoints) {
                if (pointsYouS > pointsMe) {
                    updateStatusScoreName2Game2PUseCase.execute(UpdateStatusAndScoreGameParams(idGame= idGame, score = scoreName2.plus(1).toShort()))
                    _oneTimeEvent.emit(SideEffect.ShowWinner(message = name2))
                }
            }
            insertPointsUseCase.execute(updatedModel)
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

    fun resetGame(winningPoints: Short, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            updateStatusWinningPointsGame2PUseCase.execute(UpdateStatusWinningPointsGameParams(idGame =  idGame, statusGame = GameStatus.CONTINUE.id, winningPoints = winningPoints))
            deleteAllPoints2PUseCase.execute(idGame)
        }
    }

    fun updateOnlyStatus(statusGame: GameStatus, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            updateOnlyStatusGame2PUseCase.execute(UpdateOnlyStatusGameParams(idGame = idGame, statusGame = statusGame.id))
        }
    }

    private val _oneTimeEvent = MutableSharedFlow<SideEffect>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val oneTimeEvent = _oneTimeEvent.asSharedFlow()
}

data class MatchData2P(val game: Game2PUi, val points: List<Points2PUi>)

sealed class Match2PUiState {
    object Loading : Match2PUiState()
    data class Success(val data: MatchData2P) : Match2PUiState()
    data class Error(val exception: Throwable) : Match2PUiState()
}

sealed interface SideEffect {
    data class ShowWinner(val message: String) : SideEffect
}
