package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateOnlyStatusGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusAndScoreGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusWinningPointsGameParams
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2GroupsRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points2GroupsRepositoryImpl
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateOnlyStatusGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreGame2GroupsName1UseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreGame2GroupsName2UseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetLastPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints2GroupsUseCase
import com.ionvaranita.belotenote.ui.match.BOLT
import com.ionvaranita.belotenote.utils.IdsPlayer.ID_PERSON_2_1
import com.ionvaranita.belotenote.utils.IdsPlayer.ID_PERSON_2_2
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class Match2GroupsViewModel(
    private val idGame: Int,
    private val getGameUseCase: GetGame2GroupsUseCase,
    private val getPointsUseCase: GetPoints2GroupsUseCase,
    private val getLastPointsUseCase: GetLastPoints2GroupsUseCase,
    private val insertPointsUseCase: InsertPoints2GroupsUseCase,
    private val deleteLastRowPointsUseCase: DeleteLastRowPoints2GroupsUseCase,
    private val updateStatusScoreName1UseCase: UpdateStatusScoreGame2GroupsName1UseCase,
    private val updateStatusScoreName2UseCase: UpdateStatusScoreGame2GroupsName2UseCase,
    private val updateStatusWinningPointsUseCase: UpdateStatusWinningPointsGame2GroupsUseCase,
    private val updateOnlyStatusGameUseCase: UpdateOnlyStatusGame2GroupsUseCase,
    private val deleteAllPointsUseCase: DeleteAllPoints2GroupsUseCase

) : ViewModel() {
    private val _uiState = MutableStateFlow<MatchGroupsUiState>(MatchGroupsUiState.Loading)
    val uiState: StateFlow<MatchGroupsUiState> = _uiState

    private val _statusGame = mutableStateOf(GameStatus.CONTINUE)
    val statusGame: State<GameStatus> = _statusGame

    private var lastPoints: Points2GroupsUi? = null


    init {
        getMatchData()
    }

    private var countBoltWe = 0
    private var countBoltYouP = 0
    var winningPoints by Delegates.notNull<Short>()
    private var scoreName1 by Delegates.notNull<Short>()
    private var scoreName2 by Delegates.notNull<Short>()
    private var name1 by Delegates.notNull<String>()
    private var name2 by Delegates.notNull<String>()

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
                lastPoints = points.lastOrNull()
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
            model.idGame = idGame
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
            var lastPoints = getLastPointsUseCase.execute(idGame)
            if (lastPoints == null) {
                lastPoints = Points2GroupsEntity(idGame = idGame)
            }
            val updatedModel = model.add(lastPoints)
            val pointsWe = updatedModel.pointsWe
            val pointsYouP = updatedModel.pointsYouP
            if (checkIsExtended(updatedModel.toUiModel())) {
                updateOnlyStatusGameUseCase.execute(
                    params = UpdateOnlyStatusGameParams(
                        idGame, GameStatus.EXTENDED.id
                    )
                )
            } else if (pointsWe >= winningPoints) {
                if (pointsWe > pointsYouP) {
                    updateStatusScoreName1UseCase.execute(
                        UpdateStatusAndScoreGameParams(
                            idGame = idGame, score = scoreName1.plus(1).toShort()
                        )
                    )
                    _oneTimeEvent.emit(SideEffect.ShowWinner(winnerName = name1))
                }
            } else if (pointsYouP >= winningPoints) {
                if (pointsYouP > pointsWe) {
                    updateStatusScoreName2UseCase.execute(
                        UpdateStatusAndScoreGameParams(
                            idGame = idGame, score = scoreName2.plus(1).toShort()
                        )
                    )
                    _oneTimeEvent.emit(SideEffect.ShowWinner(winnerName = name2))
                }
            }
            insertPointsUseCase.execute(updatedModel)
        }
    }

    fun deleteLastPoints(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            var lastPoints = getLastPointsUseCase.execute(idGame)
            if (lastPoints != null) {
                deleteLastRowPointsUseCase.execute(lastPoints)
            }
        }

    }

    fun checkIsExtended(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            lastPoints?.let { checkIsExtended(it) }
        }
    }

    private suspend fun checkIsExtended(pointsUi: Points2GroupsUi): Boolean {
        val pointsWe = pointsUi.pointsWe
        val pointsYouP = pointsUi.pointsYouP
        if (pointsWe.toShort() >= winningPoints && pointsYouP.toShort() >= winningPoints) {
            if (pointsWe > pointsYouP) {
                _oneTimeEvent.emit(
                    SideEffect.ShowExtended(
                        maxPoints = pointsWe, winner = Winner(
                            ID_PERSON_2_1, name1
                        )
                    )
                )
            } else if (pointsWe < pointsYouP) {
                _oneTimeEvent.emit(
                    SideEffect.ShowExtended(
                        maxPoints = pointsYouP, winner = Winner(
                            ID_PERSON_2_2, name2
                        )
                    )
                )
            } else {
                _oneTimeEvent.emit(SideEffect.ShowExtendedMandatory(maxPoints = pointsYouP.toString()))
            }
            return true
        }
        return false
    }

    fun updateStatusScoreName(winner: Winner, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            if (winner.id == ID_PERSON_2_1) {
                updateStatusScoreName1UseCase.execute(
                    params = UpdateStatusAndScoreGameParams(
                        idGame = idGame,
                        statusGame = GameStatus.FINISHED.id,
                        score = scoreName1.plus(1).toShort()
                    )
                )

            } else if (winner.id == ID_PERSON_2_2) {
                updateStatusScoreName2UseCase.execute(
                    params = UpdateStatusAndScoreGameParams(
                        idGame = idGame,
                        statusGame = GameStatus.FINISHED.id,
                        score = scoreName2.plus(1).toShort()
                    )
                )
            }
        }
    }

    fun resetGame(winningPoints: Short, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            updateStatusWinningPointsUseCase.execute(
                UpdateStatusWinningPointsGameParams(
                    idGame = idGame,
                    statusGame = GameStatus.CONTINUE.id,
                    winningPoints = winningPoints
                )
            )
            deleteAllPointsUseCase.execute(idGame)
        }
    }

    fun extentGame(winningPoints: Short, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            updateStatusWinningPointsUseCase.execute(
                UpdateStatusWinningPointsGameParams(
                    idGame = idGame,
                    statusGame = GameStatus.CONTINUE.id,
                    winningPoints = winningPoints
                )
            )
        }
    }

    fun updateOnlyStatus(statusGame: GameStatus, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            updateOnlyStatusGameUseCase.execute(
                UpdateOnlyStatusGameParams(
                    idGame = idGame, statusGame = statusGame.id
                )
            )
        }
    }

    private val _oneTimeEvent = MutableSharedFlow<SideEffect>(
        replay = 0, extraBufferCapacity = 1
    )
    val oneTimeEvent = _oneTimeEvent.asSharedFlow()
}

data class MatchData2Groups(val game: Game2GroupsUi, val points: List<Points2GroupsUi>)

sealed class MatchGroupsUiState {
    data object Loading : MatchGroupsUiState()
    data class Success(val data: MatchData2Groups) : MatchGroupsUiState()
    data class Error(val exception: Throwable) : MatchGroupsUiState()
}


