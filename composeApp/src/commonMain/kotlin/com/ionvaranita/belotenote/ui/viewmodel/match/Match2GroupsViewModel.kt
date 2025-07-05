package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.lifecycle.viewModelScope
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateOnlyStatusGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusAndScoreGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusWinningPointsGameParams
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateOnlyStatusGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreGame2GroupsName1UseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreGame2GroupsName2UseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints2GroupsUseCase
import com.ionvaranita.belotenote.ui.match.BOLT
import com.ionvaranita.belotenote.utils.IdsPlayer.ID_PERSON_1
import com.ionvaranita.belotenote.utils.IdsPlayer.ID_PERSON_2
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class Match2GroupsViewModel(
    private val idGame: Int,
    private val getGameUseCase: GetGame2GroupsUseCase,
    private val getPointsUseCase: GetPoints2GroupsUseCase,
    private val insertPointsUseCase: InsertPoints2GroupsUseCase,
    private val deleteLastRowPointsUseCase: DeleteLastRowPoints2GroupsUseCase,
    private val updateStatusScoreName1UseCase: UpdateStatusScoreGame2GroupsName1UseCase,
    private val updateStatusScoreName2UseCase: UpdateStatusScoreGame2GroupsName2UseCase,
    private val updateStatusWinningPointsUseCase: UpdateStatusWinningPointsGame2GroupsUseCase,
    private val updateOnlyStatusUseCase: UpdateOnlyStatusGame2GroupsUseCase,
    private val deleteAllPointsUseCase: DeleteAllPoints2GroupsUseCase

) : ViewModelBase() {


    private lateinit var lastPoints: Points2GroupsUi


    init {
        getMatchData()
    }


    private var scoreName1 by Delegates.notNull<Short>()
    private var scoreName2 by Delegates.notNull<Short>()
    private var name1 by Delegates.notNull<String>()
    private var name2 by Delegates.notNull<String>()
    private var isMinus10P1 = false
    private var isMinus10P2 = false
    private var countBoltP1 = 0
    private var countBoltP2 = 0

    override fun getMatchData(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {

            combine(
                getGameUseCase.execute(idGame),
                getPointsUseCase.execute(idGame),
            ) { game, points ->
                lastPoints = points.lastOrNull()?.copy() ?: Points2GroupsUi(
                    idGame = idGame,
                    pointsP1 = 0.toString(),
                    pointsP2 = 0.toString(),
                    pointsGame = 0.toString()
                )
                winningPoints = game.winningPoints
                scoreName1 = game.scoreName1
                scoreName2 = game.scoreName2
                name1 = game.name1
                name2 = game.name2
                _statusGame.value = GameStatus.fromId(game.statusGame)!!

                countBoltP1 = 0
                countBoltP2 = 0
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
                }
                MatchData2Groups(game = game, points = points)
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
        val modelPoints = model as Points2GroupsUi
        viewModelScope.launch(dispatcher) {
            modelPoints.idGame = idGame
            if (modelPoints.pointsP1 == BOLT) {
                modelPoints.pointsP1 = lastPoints.pointsP1
                if (!isMinus10P1 && countBoltP1 != 0 && countBoltP1 % 2 == 0) {
                    isMinus10P1 = true
                    modelPoints.pointsP1 = "-10"
                } else {
                    isMinus10P1 = false
                    modelPoints.isBoltP1 = true
                }
            }
            if (modelPoints.pointsP2 == BOLT) {
                modelPoints.pointsP2 = lastPoints.pointsP2
                if (!isMinus10P2 && countBoltP2 != 0 && countBoltP2 % 2 == 0) {
                    isMinus10P2 = true
                    modelPoints.pointsP2 = "-10"
                } else {
                    isMinus10P2 = false
                    modelPoints.isBoltP2 = true
                }
            }
            val updatedModel = modelPoints.add(lastPoints)

            val isToExtend = checkIsExtended(updatedModel)
            if (isToExtend) {
                updateOnlyStatusUseCase.execute(params = UpdateOnlyStatusGameParams(idGame = idGame, statusGame = GameStatus.EXTENDED.id))
            }
            insertPointsUseCase.execute(updatedModel)
        }
    }

    override fun deleteLastPoints(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
            deleteLastRowPointsUseCase.execute(lastPoints.toDataClass())

        }

    }

    override fun checkIsExtended(
        dispatcher: CoroutineDispatcher
    ) {
        viewModelScope.launch(dispatcher) {
            checkIsExtended(lastPoints)
        }
    }

    private suspend fun checkIsExtended(pointsUi: Points2GroupsUi): Boolean {
        val pointsWe = pointsUi.pointsP1.toShort()
        val pointsYouP = pointsUi.pointsP2.toShort()
        val mapPoints: Map<Int, Short> = mapOf(
            ID_PERSON_1 to pointsWe, ID_PERSON_2 to pointsYouP
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
                            result.idWinner, if (result.idWinner == ID_PERSON_1) name1 else name2
                        )
                    )
                )
            }

            is WinnerResult.ToExtendMandatory -> {
                isExtended = true
                _oneTimeEvent.emit(SideEffect.ShowExtendedMandatory(maxPoints = result.maxPoints.toString()))

            }

            is WinnerResult.ToFinish -> {
                if (result.idWinner == ID_PERSON_1) {
                    updateStatusScoreName1UseCase.execute(
                        UpdateStatusAndScoreGameParams(
                            idGame = idGame, score = scoreName1.plus(1).toShort()
                        )
                    )
                    _oneTimeEvent.emit(SideEffect.ShowWinner(winnerName = name1))
                } else if (result.idWinner == ID_PERSON_2) {
                    updateStatusScoreName2UseCase.execute(
                        UpdateStatusAndScoreGameParams(
                            idGame = idGame, score = scoreName2.plus(1).toShort()
                        )
                    )
                    _oneTimeEvent.emit(SideEffect.ShowWinner(winnerName = name2))
                }
            }
        }
        return isExtended
    }

    override fun updateStatusScoreName(winner: Winner, dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
            if (winner.id == ID_PERSON_1) {
                updateStatusScoreName1UseCase.execute(
                    params = UpdateStatusAndScoreGameParams(
                        idGame = idGame,
                        statusGame = GameStatus.FINISHED.id,
                        score = scoreName1.plus(1).toShort()
                    )
                )

            } else if (winner.id == ID_PERSON_2) {
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
}

data class MatchData2Groups(val game: Game2GroupsUi, val points: List<Points2GroupsUi>)


