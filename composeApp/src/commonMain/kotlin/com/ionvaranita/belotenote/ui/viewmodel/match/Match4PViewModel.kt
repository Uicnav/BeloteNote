package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateOnlyStatusGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusAndScoreGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusWinningPointsGameParams
import com.ionvaranita.belotenote.domain.model.Game4PUi
import com.ionvaranita.belotenote.domain.model.Points4PUi
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateOnlyStatusGame4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName1Game4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName2Game4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName3Game4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName4Game4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame4PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints4PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints4PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints4PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints4PUseCase
import com.ionvaranita.belotenote.ui.GamePath
import com.ionvaranita.belotenote.ui.match.BOLT
import com.ionvaranita.belotenote.utils.IdsPlayer.ID_PERSON_1
import com.ionvaranita.belotenote.utils.IdsPlayer.ID_PERSON_2
import com.ionvaranita.belotenote.utils.IdsPlayer.ID_PERSON_3
import com.ionvaranita.belotenote.utils.IdsPlayer.ID_PERSON_4
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class Match4PPViewModel(
    private val getGameUseCase: GetGame4PUseCase,
    private val getPointsUseCase: GetPoints4PUseCase,
    private val insertPointsUseCase: InsertPoints4PUseCase,
    private val deleteLastPointsUseCase: DeleteLastRowPoints4PUseCase,
    private val updateStatusScoreName1UseCase: UpdateStatusScoreName1Game4PUseCase,
    private val updateStatusScoreName2UseCase: UpdateStatusScoreName2Game4PUseCase,
    private val updateStatusScoreName3UseCase: UpdateStatusScoreName3Game4PUseCase,
    private val updateStatusScoreName4UseCase: UpdateStatusScoreName4Game4PUseCase,
    private val updateStatusWinningPointsUseCase: UpdateStatusWinningPointsGame4PUseCase,
    private val updateOnlyStatusUseCase: UpdateOnlyStatusGame4PUseCase,
    private val deleteAllPointsUseCase: DeleteAllPoints4PUseCase,
    override val prefs: DataStore<Preferences>,
    override val idGame: Int,
) : ViewModelBase() {
    override val matchPlayed: String
        get() = (scoreName1 + scoreName2 + scoreName3 + scoreName4).toString()
    override val namesMap: Map<Int, String> by lazy {
        mutableMapOf<Int, String>().apply {
            this[ID_PERSON_1] = name1
            this[ID_PERSON_2] = name2
            this[ID_PERSON_3] = name3
            this[ID_PERSON_4] = name4
        }
    }
    override val gamePath: GamePath = GamePath.FOUR
    init {
        getMatchData()
    }

    private var countBoltP1 = 0
    private var countBoltP2 = 0
    private var countBoltP3 = 0
    private var countBoltP4 = 0

    private var isMinus10P1 = false
    private var isMinus10P2 = false
    private var isMinus10P3 = false
    private var isMinus10P4 = false

    private var scoreName1 by Delegates.notNull<Short>()
    private var scoreName2 by Delegates.notNull<Short>()
    private var scoreName3 by Delegates.notNull<Short>()
    private var scoreName4 by Delegates.notNull<Short>()
    private var name1 by Delegates.notNull<String>()
    private var name2 by Delegates.notNull<String>()
    private var name3 by Delegates.notNull<String>()
    private var name4 by Delegates.notNull<String>()

    private lateinit var lastPoints: Points4PUi


    override fun getMatchData(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {

            combine(
                getGameUseCase.execute(idGame),
                getPointsUseCase.execute(idGame),
            ) { game, points ->
                lastPoints = points.lastOrNull()?.copy() ?: Points4PUi(
                    idGame = idGame,
                    pointsP1 = 0.toString(),
                    pointsP2 = 0.toString(),
                    pointsP3 = 0.toString(),
                    pointsP4 = 0.toString(),
                    pointsGame = 0.toString()
                )
                countBoltP1 = 0
                countBoltP2 = 0
                countBoltP3 = 0
                countBoltP4 = 0
                winningPoints = game.winningPoints
                scoreName1 = game.scoreName1
                scoreName2 = game.scoreName2
                scoreName3 = game.scoreName3
                scoreName4 = game.scoreName4
                name1 = game.name1
                name2 = game.name2
                name3 = game.name3
                name4 = game.name4
                _statusGame.value = GameStatus.fromId(game.statusGame)!!
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

                    if (point.isBoltP4) {
                        val boltTtoUi = (countBoltP4 % 2) + 1
                        point.pointsP4 = BOLT + (boltTtoUi).toString()
                        ++countBoltP4
                    }
                }
                MatchData4P(game = game, points = points)
            }.catch { exception ->
                _uiState.value = MatchUiState.Error(exception)
            }.collect { matchData ->
                _uiState.value = MatchUiState.Success(matchData)
            }
        }
    }

    override fun <T> insertPoints(
        model: T, dispatcher: CoroutineDispatcher
    ) {
        val modelPoints = model as Points4PUi
        viewModelScope.launch(dispatcher) {
            modelPoints.idGame = idGame
            if (modelPoints.pointsP1 == BOLT) {
                model.pointsP1 = lastPoints.pointsP1
                if (!isMinus10P1 && countBoltP1 != 0 && countBoltP1 % 2 == 0) {
                    isMinus10P1 = true
                    modelPoints.pointsP1 = "-10"
                } else {
                    isMinus10P1 = false
                    modelPoints.isBoltP1 = true
                }
            }
            if (modelPoints.pointsP2 == BOLT) {
                model.pointsP2 = lastPoints.pointsP2
                if (!isMinus10P2 && countBoltP2 != 0 && countBoltP2 % 2 == 0) {
                    isMinus10P2 = true
                    modelPoints.pointsP2 = "-10"
                } else {
                    isMinus10P2 = false
                    modelPoints.isBoltP2 = true
                }
            }
            if (modelPoints.pointsP3 == BOLT) {
                model.pointsP3 = lastPoints.pointsP3
                if (!isMinus10P3 && countBoltP3 != 0 && countBoltP3 % 2 == 0) {
                    isMinus10P3 = true
                    modelPoints.pointsP3 = "-10"
                } else {
                    isMinus10P3 = false
                    modelPoints.isBoltP3 = true
                }
            }

            if (modelPoints.pointsP4 == BOLT) {
                model.pointsP4 = lastPoints.pointsP4
                if (!isMinus10P4 && countBoltP4 != 0 && countBoltP4 % 2 == 0) {
                    isMinus10P4 = true
                    modelPoints.pointsP4 = "-10"
                } else {
                    isMinus10P4 = false
                    modelPoints.isBoltP4 = true
                }
            }
            val updatedModel = modelPoints.add(lastPoints)

            val isToExtend = checkIsExtended(updatedModel)
            if (isToExtend) {
                updateOnlyStatusUseCase.execute(
                    params = UpdateOnlyStatusGameParams(
                        idGame = idGame, statusGame = GameStatus.EXTENDED.id
                    )
                )
            }
            insertPointsUseCase.execute(updatedModel)
        }
    }

    override fun checkStatusAndScore(
        dispatcher: CoroutineDispatcher
    ) {
        viewModelScope.launch(dispatcher) {
            checkIsExtended(lastPoints)
        }
    }

    override fun updateStatusScoreName(
        idWinner: Int,
        gameStatus: GameStatus,
        isScoreToIncrease: Boolean,
        dispatcher: CoroutineDispatcher
    )  {
        viewModelScope.launch(dispatcher) {
            saveLastWinner(idWinner)

            when (idWinner) {
                ID_PERSON_1 -> {
                    updateStatusScoreName1UseCase.execute(
                        params = UpdateStatusAndScoreGameParams(
                            idGame = idGame,
                            statusGame = gameStatus.id,
                            score = if (isScoreToIncrease)scoreName1.plus(1).toShort() else scoreName1.minus(1).toShort()
                        )
                    )

                }
                ID_PERSON_2 -> {
                    updateStatusScoreName2UseCase.execute(
                        params = UpdateStatusAndScoreGameParams(
                            idGame = idGame,
                            statusGame = gameStatus.id,
                            score = if (isScoreToIncrease)scoreName2.plus(1).toShort() else scoreName2.minus(1).toShort()

                        )
                    )
                }
                ID_PERSON_3 -> {
                    updateStatusScoreName3UseCase.execute(
                        params = UpdateStatusAndScoreGameParams(
                            idGame = idGame,
                            statusGame = gameStatus.id,
                            score = if (isScoreToIncrease)scoreName3.plus(1).toShort() else scoreName3.minus(1).toShort()
                        )
                    )
                }
                ID_PERSON_4 -> {
                    updateStatusScoreName4UseCase.execute(
                        params = UpdateStatusAndScoreGameParams(
                            idGame = idGame,
                            statusGame = gameStatus.id,
                            score = if (isScoreToIncrease)scoreName4.plus(1).toShort() else scoreName4.minus(1).toShort()
                        )
                    )
                }
            }
            if (gameStatus == GameStatus.FINISHED) {
                _oneTimeEvent.emit(SideEffect.ShowWinner(winnerName = namesMap[idWinner]!!))
            }
        }
    }

    override fun deleteLastPoints(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
            deleteLastPointsUseCase.execute(lastPoints.toDataClass())
            if (statusGame.value != GameStatus.CONTINUE) {
                if (statusGame.value == GameStatus.FINISHED) {
                    val lastWinner = getLastWinnerAndRemove()
                    updateStatusScoreName(idWinner = lastWinner, gameStatus = GameStatus.CONTINUE, isScoreToIncrease = false)
                } else {
                    updateOnlyStatus(GameStatus.CONTINUE)
                }
            }
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

    private suspend fun checkIsExtended(pointsUi: Points4PUi): Boolean {
        val pointsP1 = pointsUi.pointsP1.toShort()
        val pointsP2 = pointsUi.pointsP2.toShort()
        val pointsP3 = pointsUi.pointsP3.toShort()
        val pointsP4 = pointsUi.pointsP4.toShort()
        val mapPoints: Map<Int, Short> = mapOf(
            ID_PERSON_1 to pointsP1,
            ID_PERSON_2 to pointsP2,
            ID_PERSON_3 to pointsP3,
            ID_PERSON_4 to pointsP4
        )
        var isExtended = false
        when (val result = getWinner(mapPoints, winningPoints)) {
            WinnerResult.ToContinue -> {

            }

            is WinnerResult.ToExtend -> {
                isExtended = true
                _oneTimeEvent.emit(
                    SideEffect.ShowExtended(
                        maxPoints = result.maxPoints.toString(), winner = Winner(
                            id=result.idWinner, name=namesMap[result.idWinner]!!
                        )
                    )
                )
            }

            is WinnerResult.ToExtendMandatory -> {
                isExtended = true
                _oneTimeEvent.emit(SideEffect.ShowExtendedMandatory(maxPoints = result.maxPoints.toString()))

            }

            is WinnerResult.ToFinish -> {
                updateStatusScoreName(result.idWinner, gameStatus = GameStatus.FINISHED)
            }
        }
        return isExtended
    }
}

data class MatchData4P(val game: Game4PUi, val points: List<Points4PUi>)

