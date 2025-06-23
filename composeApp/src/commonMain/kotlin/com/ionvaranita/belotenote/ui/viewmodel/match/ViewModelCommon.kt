package com.ionvaranita.belotenote.ui.viewmodel.match

import com.ionvaranita.belotenote.constants.GameStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

sealed interface SideEffect {
    data class ShowWinner(val winnerName: String) : SideEffect
    data class ShowExtended(val maxPoints: String, val winner: Winner) : SideEffect
    data class ShowExtendedMandatory(val maxPoints: String) : SideEffect
}

data class Winner(val id: Int, val name: String)

interface ViewModelBase {
    fun deleteLastPoints(dispatcher: CoroutineDispatcher = Dispatchers.IO)
    fun checkIsExtended(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    )

    fun updateStatusScoreName(winner: Winner, dispatcher: CoroutineDispatcher = Dispatchers.IO)
    fun resetGame(winningPoints: Short, dispatcher: CoroutineDispatcher = Dispatchers.IO)
    fun extentGame(winningPoints: Short, dispatcher: CoroutineDispatcher = Dispatchers.IO)
    fun updateOnlyStatus(statusGame: GameStatus, dispatcher: CoroutineDispatcher = Dispatchers.IO)
}