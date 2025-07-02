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

    private var countBoltWe = 0
    private var countBoltYouP = 0
    private var scoreName1 by Delegates.notNull<Short>()
    private var scoreName2 by Delegates.notNull<Short>()
    private var name1 by Delegates.notNull<String>()
    private var name2 by Delegates.notNull<String>()

    override fun getMatchData(dispatcher: CoroutineDispatcher) {
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
                lastPoints = points.lastOrNull() ?: Points2GroupsUi(
                    idGame = idGame,
                    pointsWe = 0.toString(),
                    pointsYouP = 0.toString(),
                    pointsGame = 0.toString()
                )
                countBoltWe = 0
                countBoltYouP = 0
                points.forEach { point ->
                    if (point.isBoltWe) {
                        val boltTtoUi = (countBoltWe % 2) + 1
                        point.pointsWe = BOLT + (boltTtoUi).toString()
                        ++countBoltWe
                    }
                    if (point.isBoltYouP) {
                        val boltTtoUi = (countBoltYouP % 2) + 1
                        point.pointsYouP = BOLT + (boltTtoUi).toString()
                        ++countBoltYouP
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


    var isMinus10We = false
    var isMinus10YouP = false


    override fun <T> insertPoints(
        model: T, dispatcher: CoroutineDispatcher
    ) {
        val model = model as Points2GroupsUi
        viewModelScope.launch(dispatcher) {
            model.idGame = idGame
            if (model.pointsWe == BOLT) {
                if (!isMinus10We && countBoltWe != 0 && countBoltWe % 2 == 0) {
                    isMinus10We = true
                    model.pointsWe = "-10"
                } else {
                    isMinus10We = false
                    model.isBoltWe = true
                }
            }
            if (model.pointsYouP == BOLT) {
                if (!isMinus10YouP && countBoltYouP != 0 && countBoltYouP % 2 == 0) {
                    isMinus10YouP = true
                    model.pointsYouP = "-10"
                } else {
                    isMinus10YouP = false
                    model.isBoltYouP = true
                }
            }
            val updatedModel = model.add(lastPoints.toDataClass())

            val isToExtend = checkIsExtended(updatedModel.toUiModel())
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
        val pointsWe = pointsUi.pointsWe.toShort()
        val pointsYouP = pointsUi.pointsYouP.toShort()
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


