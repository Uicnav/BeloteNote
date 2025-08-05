package com.ionvaranita.belotenote.ui.viewmodel.match

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ionvaranita.belotenote.constants.GameStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.properties.Delegates

sealed interface SideEffect {
    data class ShowWinner(val winnerName: String) : SideEffect
    data class ShowExtended(val maxPoints: String, val winner: Winner) : SideEffect
    data class ShowExtendedMandatory(val maxPoints: String) : SideEffect
}

data class Winner(val id: Int, val name: String)

sealed interface WinnerResult {
    data object ToContinue : WinnerResult
    data class ToFinish(val idWinner: Int) : WinnerResult
    data class ToExtend(val idWinner: Int, val maxPoints: Short) : WinnerResult
    data class ToExtendMandatory(val maxPoints: Short) : WinnerResult
}

sealed interface MatchUiState {
    data object Loading : MatchUiState
    data class Success<T>(val data: T) : MatchUiState
    data class Error(val exception: Throwable) : MatchUiState
}

abstract class ViewModelBase : ViewModel() {

    var winningPoints by Delegates.notNull<Short>()

    protected val _statusGame = mutableStateOf(GameStatus.CONTINUE)
    val statusGame: State<GameStatus> = _statusGame

    protected val _oneTimeEvent = MutableSharedFlow<SideEffect>(replay = 0, extraBufferCapacity = 1)
    val oneTimeEvent = _oneTimeEvent.asSharedFlow()

    protected val _uiState = MutableStateFlow<MatchUiState>(MatchUiState.Loading)
    val uiState: StateFlow<MatchUiState> = _uiState

    protected abstract fun getMatchData(dispatcher: CoroutineDispatcher = Dispatchers.IO)

    abstract fun deleteLastPoints(dispatcher: CoroutineDispatcher = Dispatchers.IO)
    abstract fun checkIsExtended(dispatcher: CoroutineDispatcher = Dispatchers.IO)
    abstract fun updateStatusScoreName(winner: Winner, dispatcher: CoroutineDispatcher = Dispatchers.IO)
    abstract fun resetGame(winningPoints: Short, dispatcher: CoroutineDispatcher = Dispatchers.IO)
    abstract fun extentGame(winningPoints: Short, dispatcher: CoroutineDispatcher = Dispatchers.IO)
    abstract fun updateOnlyStatus(statusGame: GameStatus, dispatcher: CoroutineDispatcher = Dispatchers.IO)
    abstract fun <T> insertPoints(model: T, dispatcher: CoroutineDispatcher = Dispatchers.IO)

    protected fun getWinner(mapPoints: Map<Int, Short>, winningPoints: Short): WinnerResult {
        val contenders = mapPoints.entries.filter { it.value >= winningPoints }
        if (contenders.isEmpty()) return WinnerResult.ToContinue
        val max = contenders.maxOf { it.value }
        val top = contenders.filter { it.value == max }
        return when {
            top.size == 1 && contenders.size == 1 -> WinnerResult.ToFinish(top[0].key)
            top.size == 1 -> WinnerResult.ToExtend(top[0].key, max)
            else -> WinnerResult.ToExtendMandatory(max)
        }
    }

}
