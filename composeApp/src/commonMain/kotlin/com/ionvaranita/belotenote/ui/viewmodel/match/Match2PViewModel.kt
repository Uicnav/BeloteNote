package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateOnlyStatusGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusAndScoreGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusWinningPointsGameParams
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.model.Points2PUi
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateOnlyStatusGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName1Game2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName2Game2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints2PUseCase
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

class Match2PPViewModel(
    private val idGame: Int,
    private val getGameUseCase: GetGame2PUseCase,
    private val getPointsUseCase: GetPoints2PUseCase,
    private val insertPointsUseCase: InsertPoints2PUseCase,
    private val deleteLastRowUseCase: DeleteLastRowPoints2PUseCase,
    private val updateStatusScoreName1UseCase: UpdateStatusScoreName1Game2PUseCase,
    private val updateStatusScoreName2UseCase: UpdateStatusScoreName2Game2PUseCase,
    private val updateStatusWinningPointsUseCase: UpdateStatusWinningPointsGame2PUseCase,
    private val updateOnlyStatusUseCase: UpdateOnlyStatusGame2PUseCase,
    private val deleteAllPointsUseCase: DeleteAllPoints2PUseCase
) :

    ViewModel(), ViewModelBase {


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

    private lateinit var lastPoints: Points2PUi


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
                lastPoints = points.lastOrNull() ?: Points2PUi(
                    idGame = idGame,
                    pointsMe = 0.toString(),
                    pointsYouS = 0.toString(),
                    pointsGame = 0.toString()
                )
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
            model.idGame = idGame
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
            val updatedModel = model.add(lastPoints.toDataClass())
            val pointsMe = updatedModel.pointsMe
            val pointsYouS = updatedModel.pointsYouS

            if (checkIsExtended(updatedModel.toUiModel())) {
                updateOnlyStatusUseCase.execute(
                    params = UpdateOnlyStatusGameParams(
                        idGame, GameStatus.EXTENDED.id
                    )
                )
            } else if (pointsMe >= winningPoints) {
                if (pointsMe > pointsYouS) {
                    updateStatusScoreName1UseCase.execute(
                        UpdateStatusAndScoreGameParams(
                            idGame = idGame, score = scoreName1.plus(1).toShort()
                        )
                    )
                    _oneTimeEvent.emit(SideEffect.ShowWinner(winnerName = name1))
                }
            } else if (pointsYouS >= winningPoints) {
                if (pointsYouS > pointsMe) {
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

    override fun checkIsExtended(
        dispatcher: CoroutineDispatcher
    ) {
        viewModelScope.launch(dispatcher) {
            checkIsExtended(lastPoints)
        }
    }

    private suspend fun checkIsExtended(points2P: Points2PUi): Boolean {
        val pointsMe = points2P.pointsMe
        val pointsYouS = points2P.pointsYouS
        if (pointsMe.toShort() >= winningPoints && pointsYouS.toShort() >= winningPoints) {
            if (pointsMe > pointsYouS) {
                _oneTimeEvent.emit(
                    SideEffect.ShowExtended(
                        maxPoints = pointsMe, winner = Winner(ID_PERSON_2_1, name1)
                    )
                )
            } else if (pointsMe < pointsYouS) {
                _oneTimeEvent.emit(
                    SideEffect.ShowExtended(
                        maxPoints = pointsYouS, winner = Winner(ID_PERSON_2_2, name2)
                    )
                )
            } else {
                _oneTimeEvent.emit(SideEffect.ShowExtendedMandatory(maxPoints = pointsYouS))
            }
            return true
        }
        return false
    }

    override fun updateStatusScoreName(winner: Winner, dispatcher: CoroutineDispatcher) {
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

    override fun deleteLastPoints(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
                deleteLastRowUseCase.execute(lastPoints.toDataClass())
        }

    }

    override fun resetGame(winningPoints: Short, dispatcher: CoroutineDispatcher) {
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

    override fun extentGame(winningPoints: Short, dispatcher: CoroutineDispatcher) {
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

    override fun updateOnlyStatus(statusGame: GameStatus, dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
            updateOnlyStatusUseCase.execute(
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

data class MatchData2P(val game: Game2PUi, val points: List<Points2PUi>)

sealed class Match2PUiState {
    object Loading : Match2PUiState()
    data class Success(val data: MatchData2P) : Match2PUiState()
    data class Error(val exception: Throwable) : Match2PUiState()
}

