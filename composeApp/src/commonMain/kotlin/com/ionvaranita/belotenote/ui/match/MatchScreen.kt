package com.ionvaranita.belotenote.ui.match

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.alert_dialog_finish_match
import belotenote.composeapp.generated.resources.alert_dialog_finished
import belotenote.composeapp.generated.resources.alert_dialog_playing
import belotenote.composeapp.generated.resources.alert_dialog_to_extend
import belotenote.composeapp.generated.resources.dialog_are_you_sure
import belotenote.composeapp.generated.resources.dialog_fragment_extend_match
import belotenote.composeapp.generated.resources.dialog_fragment_extend_match_info
import belotenote.composeapp.generated.resources.dialog_fragment_extend_match_q
import belotenote.composeapp.generated.resources.dialog_fragment_greater_than
import belotenote.composeapp.generated.resources.dialog_fragment_mandatory_extend_match_info1
import belotenote.composeapp.generated.resources.dialog_fragment_mandatory_extend_match_info2
import belotenote.composeapp.generated.resources.dialog_fragment_win
import belotenote.composeapp.generated.resources.game
import belotenote.composeapp.generated.resources.game_status
import belotenote.composeapp.generated.resources.games_played
import belotenote.composeapp.generated.resources.no
import belotenote.composeapp.generated.resources.ok
import belotenote.composeapp.generated.resources.rate_confirm
import belotenote.composeapp.generated.resources.rate_dismiss
import belotenote.composeapp.generated.resources.rate_message
import belotenote.composeapp.generated.resources.rate_title
import belotenote.composeapp.generated.resources.winner_is
import belotenote.composeapp.generated.resources.winning_points
import belotenote.composeapp.generated.resources.yes
import com.ionvaranita.belotenote.Circle
import com.ionvaranita.belotenote.StatusImage
import com.ionvaranita.belotenote.constants.GLOBAL_ALPHA
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.model.Points2PUi
import com.ionvaranita.belotenote.domain.model.Points3PUi
import com.ionvaranita.belotenote.domain.model.Points4PUi
import com.ionvaranita.belotenote.domain.model.toShortCustom
import com.ionvaranita.belotenote.domain.model.toShortGame
import com.ionvaranita.belotenote.domain.model.toShortHint
import com.ionvaranita.belotenote.ui.table.CenteredCircularProgressIndicator
import com.ionvaranita.belotenote.ui.table.GameCard
import com.ionvaranita.belotenote.ui.table.InsertFloatingActionButton
import com.ionvaranita.belotenote.ui.table.InsertGameDialogBase
import com.ionvaranita.belotenote.ui.table.ShakerTextFieldAtom
import com.ionvaranita.belotenote.ui.table.TextFieldShaker
import com.ionvaranita.belotenote.ui.viewmodel.WinningPointsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.MatchData2Groups
import com.ionvaranita.belotenote.ui.viewmodel.match.MatchData2P
import com.ionvaranita.belotenote.ui.viewmodel.match.MatchData3P
import com.ionvaranita.belotenote.ui.viewmodel.match.MatchData4P
import com.ionvaranita.belotenote.ui.viewmodel.match.MatchUiState
import com.ionvaranita.belotenote.ui.viewmodel.match.SideEffect
import com.ionvaranita.belotenote.ui.viewmodel.match.ViewModelBase
import com.ionvaranita.belotenote.ui.viewmodel.match.Winner
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MatchScreen2(
    viewModel: ViewModelBase, winningPointsViewModel: WinningPointsViewModel
) {
    val matchUiState = viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var showInfoGameDialog by remember { mutableStateOf(false) }
    var showUpdateStatusGameDialog by remember { mutableStateOf(false) }
    var showWinnerDialog by remember { mutableStateOf(false) }
    var showExtended by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf("") }
    var winnerData by remember { mutableStateOf<Winner?>(null) }
    var maxPoints by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.collectLatest { event ->
            when (event) {
                is SideEffect.ShowWinner -> {
                    if (event.winnerName.isNotEmpty()) winner = event.winnerName
                    showWinnerDialog = true
                }

                is SideEffect.ShowExtended -> {
                    winnerData = event.winner
                    maxPoints = event.maxPoints
                    showExtended = true
                }

                is SideEffect.ShowExtendedMandatory -> {
                    winnerData = null
                    maxPoints = event.maxPoints
                    showExtended = true
                }
            }
        }
    }
    if (showWinnerDialog) {
        WinnerDialog(
            onDismiss = { showWinnerDialog = false },
            onConfirm = { showWinnerDialog = false },
            winner
        )
    }
    if (showExtended) {
        ExtendedDialog(
            onDismiss = { showExtended = false }, onWin = {
            winnerData?.let {
                viewModel.updateStatusScoreName(
                    idWinner = it.id, gameStatus = GameStatus.FINISHED
                )
            }
            showExtended = false
        }, onExtend = { winningPoints ->
            viewModel.extentGame(winningPoints)
            showExtended = false
        }, winnerText = winnerData?.name, maxPoints = maxPoints
        )
    }
    if (showInfoGameDialog) {
        InfoGameDialog(
            onDismiss = { showInfoGameDialog = false }, infoGame = InfoGame(
                status = viewModel.statusGame.value,
                winningPoints = viewModel.winningPoints.toString(),
                matchPlayed = viewModel.matchPlayed
            ), onConfirm = {
                showInfoGameDialog = false
                showUpdateStatusGameDialog = true
            }, showFinishMatch = viewModel.statusGame.value == GameStatus.CONTINUE
        )
    }
    if (showUpdateStatusGameDialog && viewModel.statusGame.value == GameStatus.CONTINUE) {
        UpdateStatusGameDialog(onDismiss = { showUpdateStatusGameDialog = false }, onConfirm = {
            viewModel.updateOnlyStatus(GameStatus.FINISHED)
            showUpdateStatusGameDialog = false
        })
    }
    MatchWrapper {
        when (val matchState = matchUiState.value) {
            is MatchUiState.Success<*> -> {
                val matchData = matchState.data as MatchData2P
                val game = matchData.game
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    StatusImage(
                        gameStatus = GameStatus.fromId(game.statusGame),
                        isInMatch = true,
                        onStatusClick = {
                            showInfoGameDialog = true
                        })
                    PointsTextAtom(text = game.scoreName1.toString(), isBody = false)
                    PointsTextAtom(text = game.scoreName2.toString(), isBody = false)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PointsTextAtom(text = stringResource(Res.string.game), isBody = false)
                    PointsTextAtom(text = game.name1)
                    PointsTextAtom(text = game.name2)
                }
                val points = matchData.points
                var pointsGame by remember { mutableStateOf("") }
                var pointsP1 by remember { mutableStateOf("") }
                var hintP1 by remember { mutableStateOf("") }
                var pointsP2 by remember { mutableStateOf("") }
                var hintP2 by remember { mutableStateOf("") }
                var isPressedPoints by remember { mutableStateOf(true) }
                var isPressedP1 by remember { mutableStateOf(false) }
                var isPressedP2 by remember { mutableStateOf(false) }
                val shakerGame by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP1 by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP2 by remember { mutableStateOf(TextFieldShaker()) }
                fun recomputeHints() {
                    hintP1 = ""
                    hintP2 = ""
                    if (pointsGame.isEmpty()) return
                    val g = pointsGame.toShortGame()
                    val k1 = pointsP1.isNotEmpty()
                    val k2 = pointsP2.isNotEmpty()
                    if (!k1 && k2) hintP1 = (g - pointsP2.toShortHint()).toCustomString()
                    if (!k2 && k1) hintP2 = (g - pointsP1.toShortHint()).toCustomString()
                }

                val pointsListState = rememberLazyListState()
                scope.launch { pointsListState.animateScrollToItem(points.size) }
                LazyColumn(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = pointsListState
                ) {
                    item { HorizontalDivider(thickness = 2.dp) }
                    itemsIndexed(points, key = { _, item ->
                        item.id
                    }) { index: Int, item: Points2PUi ->
                        val isLast = index == points.lastIndex
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isLast) {
                                GameCard(
                                    onDelete = { scope.launch { viewModel.deleteLastPoints() } },
                                    isTable = false
                                ) {
                                    PointsTextAtom(text = item.pointsGame, isBody = true)
                                    PointsTextAtom(text = item.pointsP1, isBody = true)
                                    PointsTextAtom(text = item.pointsP2, isBody = true)
                                }
                            } else {
                                PointsTextAtom(text = item.pointsGame, isBody = true)
                                PointsTextAtom(text = item.pointsP1, isBody = true)
                                PointsTextAtom(text = item.pointsP2, isBody = true)
                            }
                        }
                        if ((index + 1) % 2 == 0) {
                            HorizontalDivider(
                                thickness = 4.dp, color = MaterialTheme.colorScheme.scrim
                            )
                        } else {
                            HorizontalDivider(thickness = 2.dp)
                        }
                    }
                    if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                                    .wrapContentHeight()
                            ) {
                                TouchableText(
                                    text = pointsGame,
                                    shaker = shakerGame,
                                    isPressed = isPressedPoints,
                                    onClick = {
                                        isPressedPoints = true
                                        isPressedP1 = false
                                        isPressedP2 = false
                                    })
                                TouchableText(
                                    text = pointsP1,
                                    shaker = shakerP1,
                                    isPressed = isPressedP1,
                                    placeholder = hintP1,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = true
                                        isPressedP2 = false
                                    })
                                TouchableText(
                                    text = pointsP2,
                                    shaker = shakerP2,
                                    isPressed = isPressedP2,
                                    placeholder = hintP2,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = false
                                        isPressedP2 = true
                                    })
                            }
                        }
                    }
                }
                if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                    Keyboard(
                        isPressedGames = isPressedPoints,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { inputKey ->
                            if (inputKey == ADD) {
                                if (pointsGame.isEmpty()) {
                                    shakerGame.shake()
                                } else if (pointsP1.isEmpty() && hintP1.isEmpty()) {
                                    shakerP1.shake()
                                } else if (pointsP2.isEmpty() && hintP2.isEmpty()) {
                                    shakerP2.shake()
                                } else {
                                    viewModel.insertPoints(
                                        Points2PUi(
                                            pointsGame = pointsGame,
                                            pointsP1 = pointsP1.ifEmpty { hintP1 },
                                            pointsP2 = pointsP2.ifEmpty { hintP2 })
                                    )
                                    pointsGame = ""
                                    pointsP1 = ""
                                    pointsP2 = ""
                                    hintP1 = ""
                                    hintP2 = ""
                                }
                            } else {
                                if (isPressedPoints) {
                                    manageUserInputKey(
                                        inputText = pointsGame, inputKey = inputKey
                                    ) { text ->
                                        pointsGame = text
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP1) {
                                    manageUserInputKey(
                                        inputText = pointsP1, inputKey = inputKey
                                    ) { text ->
                                        pointsP1 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP2 == BOLT) pointsP2 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP2) {
                                    manageUserInputKey(
                                        inputText = pointsP2, inputKey = inputKey
                                    ) { text ->
                                        pointsP2 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP1 == BOLT) pointsP1 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                            }
                        })
                } else {
                    var shouDialog by remember { mutableStateOf(false) }
                    InsertFloatingActionButton(
                        onClick = {
                            if (viewModel.statusGame.value == GameStatus.EXTENDED || viewModel.statusGame.value == GameStatus.EXTENDED_MANDATORY) {
                                viewModel.checkStatusAndScore()
                            } else {
                                shouDialog = true
                            }
                        }, modifier = Modifier.align(Alignment.End).padding(16.dp)
                    )
                    if (shouDialog) {
                        InsertGameDialogBase(
                            onDismissRequest = { shouDialog = false },
                            onClick = { winningPoints -> viewModel.resetGame(winningPoints) },
                            winningPointsViewModel = winningPointsViewModel
                        )
                    }
                }
            }

            is MatchUiState.Error -> {}
            MatchUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }
        }
    }
}


@Composable
internal fun MatchScreen3(
    viewModel: ViewModelBase, winningPointsViewModel: WinningPointsViewModel
) {
    val matchUiState = viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var showInfoGameDialog by remember { mutableStateOf(false) }
    var showUpdateStatusGameDialog by remember { mutableStateOf(false) }
    var showWinnerDialog by remember { mutableStateOf(false) }
    var showExtended by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf("") }
    var winnerData by remember { mutableStateOf<Winner?>(null) }
    var maxPoints by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.collectLatest { event ->
            when (event) {
                is SideEffect.ShowWinner -> {
                    if (event.winnerName.isNotEmpty()) winner = event.winnerName
                    showWinnerDialog = true
                }

                is SideEffect.ShowExtended -> {
                    winnerData = event.winner
                    maxPoints = event.maxPoints
                    showExtended = true
                }

                is SideEffect.ShowExtendedMandatory -> {
                    winnerData = null
                    maxPoints = event.maxPoints
                    showExtended = true
                }
            }
        }
    }
    if (showWinnerDialog) {
        WinnerDialog(
            onDismiss = { showWinnerDialog = false },
            onConfirm = { showWinnerDialog = false },
            winner
        )
    }
    if (showExtended) {
        ExtendedDialog(
            onDismiss = { showExtended = false }, onWin = {
            winnerData?.let { viewModel.updateStatusScoreName(it.id, GameStatus.FINISHED) }
            showExtended = false
        }, onExtend = { winningPoints ->
            viewModel.extentGame(winningPoints)
            showExtended = false
        }, winnerText = winnerData?.name, maxPoints = maxPoints
        )
    }
    if (showInfoGameDialog) {
        InfoGameDialog(
            onDismiss = { showInfoGameDialog = false }, infoGame = InfoGame(
                status = viewModel.statusGame.value,
                winningPoints = viewModel.winningPoints.toString(),
                matchPlayed = viewModel.matchPlayed
            ), onConfirm = {
                showInfoGameDialog = false
                showUpdateStatusGameDialog = true
            }, showFinishMatch = viewModel.statusGame.value == GameStatus.CONTINUE
        )
    }
    if (showUpdateStatusGameDialog && viewModel.statusGame.value == GameStatus.CONTINUE) {
        UpdateStatusGameDialog(onDismiss = { showUpdateStatusGameDialog = false }, onConfirm = {
            viewModel.updateOnlyStatus(GameStatus.FINISHED)
            showUpdateStatusGameDialog = false
        })
    }
    MatchWrapper {
        when (val matchState = matchUiState.value) {
            is MatchUiState.Success<*> -> {
                val matchData = matchState.data as MatchData3P
                val game = matchData.game
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusImage(
                        gameStatus = GameStatus.fromId(game.statusGame),
                        isInMatch = true,
                        onStatusClick = {
                            showInfoGameDialog = true
                        },
                    )
                    PointsTextAtom(text = game.scoreName1.toString())
                    PointsTextAtom(text = game.scoreName2.toString())
                    PointsTextAtom(text = game.scoreName3.toString())
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PointsTextAtom(text = stringResource(Res.string.game))
                    PointsTextAtom(text = game.name1)
                    PointsTextAtom(text = game.name2)
                    PointsTextAtom(text = game.name3)
                }
                val points = matchData.points
                var pointsGame by remember { mutableStateOf("") }
                var pointsP1 by remember { mutableStateOf("") }
                var pointsP2 by remember { mutableStateOf("") }
                var pointsP3 by remember { mutableStateOf("") }
                var hintP1 by remember { mutableStateOf("") }
                var hintP2 by remember { mutableStateOf("") }
                var hintP3 by remember { mutableStateOf("") }
                var isPressedPoints by remember { mutableStateOf(true) }
                var isPressedP1 by remember { mutableStateOf(false) }
                var isPressedP2 by remember { mutableStateOf(false) }
                var isPressedP3 by remember { mutableStateOf(false) }
                val shakerGame by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP1 by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP2 by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP3 by remember { mutableStateOf(TextFieldShaker()) }
                fun recomputeHints() {
                    hintP1 = ""
                    hintP2 = ""
                    hintP3 = ""
                    if (pointsGame.isEmpty()) return
                    val g = pointsGame.toShortGame()
                    val k1 = pointsP1.isNotEmpty()
                    val k2 = pointsP2.isNotEmpty()
                    val k3 = pointsP3.isNotEmpty()
                    if (!k1 && k2 && k3) hintP1 =
                        (g - pointsP2.toShortHint() - pointsP3.toShortHint()).toCustomString()
                    if (!k2 && k1 && k3) hintP2 =
                        (g - pointsP1.toShortHint() - pointsP3.toShortHint()).toCustomString()
                    if (!k3 && k1 && k2) hintP3 =
                        (g - pointsP1.toShortHint() - pointsP2.toShortHint()).toCustomString()
                }

                val pointsListState = rememberLazyListState()
                scope.launch { pointsListState.animateScrollToItem(points.size) }
                LazyColumn(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = pointsListState
                ) {
                    item { HorizontalDivider(thickness = 2.dp) }
                    itemsIndexed(points, key = { _, item ->
                        item.id
                    }) { index: Int, item: Points3PUi ->
                        val isLast = index == points.lastIndex
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isLast) {
                                GameCard(onDelete = {
                                    scope.launch { viewModel.deleteLastPoints() }
                                }) {
                                    PointsTextAtom(text = item.pointsGame, isBody = true)
                                    PointsTextAtom(text = item.pointsP1, isBody = true)
                                    PointsTextAtom(text = item.pointsP2, isBody = true)
                                    PointsTextAtom(text = item.pointsP3, isBody = true)
                                }
                            } else {
                                PointsTextAtom(text = item.pointsGame, isBody = true)
                                PointsTextAtom(text = item.pointsP1, isBody = true)
                                PointsTextAtom(text = item.pointsP2, isBody = true)
                                PointsTextAtom(text = item.pointsP3, isBody = true)
                            }
                        }
                        if ((index + 1) % 3 == 0) {
                            HorizontalDivider(
                                thickness = 4.dp, color = MaterialTheme.colorScheme.scrim
                            )
                        } else {
                            HorizontalDivider(thickness = 2.dp)
                        }
                    }
                    if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                                    .wrapContentHeight()
                            ) {
                                TouchableText(
                                    text = pointsGame,
                                    shaker = shakerGame,
                                    isPressed = isPressedPoints,
                                    onClick = {
                                        isPressedPoints = true
                                        isPressedP1 = false
                                        isPressedP2 = false
                                        isPressedP3 = false
                                    })
                                TouchableText(
                                    text = pointsP1,
                                    shaker = shakerP1,
                                    isPressed = isPressedP1,
                                    placeholder = hintP1,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = true
                                        isPressedP2 = false
                                        isPressedP3 = false
                                    })
                                TouchableText(
                                    text = pointsP2,
                                    shaker = shakerP2,
                                    isPressed = isPressedP2,
                                    placeholder = hintP2,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = false
                                        isPressedP2 = true
                                        isPressedP3 = false
                                    })
                                TouchableText(
                                    text = pointsP3,
                                    shaker = shakerP3,
                                    isPressed = isPressedP3,
                                    placeholder = hintP3,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = false
                                        isPressedP2 = false
                                        isPressedP3 = true
                                    })
                            }
                        }
                    }
                }
                if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                    Keyboard(
                        isPressedGames = isPressedPoints,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { inputKey ->
                            if (inputKey == ADD) {
                                if (pointsGame.isEmpty()) {
                                    shakerGame.shake()
                                } else if (pointsP1.isEmpty() && hintP1.isEmpty()) {
                                    shakerP1.shake()
                                } else if (pointsP2.isEmpty() && hintP2.isEmpty()) {
                                    shakerP2.shake()
                                } else if (pointsP3.isEmpty() && hintP3.isEmpty()) {
                                    shakerP3.shake()
                                } else {
                                    viewModel.insertPoints(
                                        Points3PUi(
                                            pointsGame = pointsGame,
                                            pointsP1 = pointsP1.ifEmpty { hintP1 },
                                            pointsP2 = pointsP2.ifEmpty { hintP2 },
                                            pointsP3 = pointsP3.ifEmpty { hintP3 })
                                    )
                                    pointsGame = ""
                                    pointsP1 = ""
                                    pointsP2 = ""
                                    pointsP3 = ""
                                    hintP1 = ""
                                    hintP2 = ""
                                    hintP3 = ""
                                }
                            } else {
                                if (isPressedPoints) {
                                    manageUserInputKey(
                                        inputText = pointsGame, inputKey = inputKey
                                    ) { text ->
                                        pointsGame = text
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP1) {
                                    manageUserInputKey(
                                        inputText = pointsP1, inputKey = inputKey
                                    ) { text ->
                                        pointsP1 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP2 == BOLT) pointsP2 = ""
                                            if (pointsP3 == BOLT) pointsP3 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP2) {
                                    manageUserInputKey(
                                        inputText = pointsP2, inputKey = inputKey
                                    ) { text ->
                                        pointsP2 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP1 == BOLT) pointsP1 = ""
                                            if (pointsP3 == BOLT) pointsP3 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP3) {
                                    manageUserInputKey(
                                        inputText = pointsP3, inputKey = inputKey
                                    ) { text ->
                                        pointsP3 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP1 == BOLT) pointsP1 = ""
                                            if (pointsP2 == BOLT) pointsP2 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                            }
                        })
                } else {
                    var shouDialog by remember { mutableStateOf(false) }
                    InsertFloatingActionButton(
                        onClick = {
                            if (viewModel.statusGame.value == GameStatus.EXTENDED || viewModel.statusGame.value == GameStatus.EXTENDED_MANDATORY) {
                                viewModel.checkStatusAndScore()
                            } else {
                                shouDialog = true
                            }
                        }, modifier = Modifier.align(Alignment.End).padding(16.dp)
                    )
                    if (shouDialog) {
                        InsertGameDialogBase(
                            onDismissRequest = { shouDialog = false },
                            onClick = { winningPoints -> viewModel.resetGame(winningPoints) },
                            winningPointsViewModel = winningPointsViewModel
                        )
                    }
                }
            }

            is MatchUiState.Error -> {}
            MatchUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }
        }
    }
}


@Composable
internal fun MatchScreen4(
    viewModel: ViewModelBase, winningPointsViewModel: WinningPointsViewModel
) {
    val matchUiState = viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var showInfoGameDialog by remember { mutableStateOf(false) }
    var showUpdateStatusGameDialog by remember { mutableStateOf(false) }
    var showWinnerDialog by remember { mutableStateOf(false) }
    var showExtended by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf("") }
    var winnerData by remember { mutableStateOf<Winner?>(null) }
    var maxPoints by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.collectLatest { event ->
            when (event) {
                is SideEffect.ShowWinner -> {
                    if (event.winnerName.isNotEmpty()) winner = event.winnerName
                    showWinnerDialog = true
                }

                is SideEffect.ShowExtended -> {
                    winnerData = event.winner
                    maxPoints = event.maxPoints
                    showExtended = true
                }

                is SideEffect.ShowExtendedMandatory -> {
                    winnerData = null
                    maxPoints = event.maxPoints
                    showExtended = true
                }
            }
        }
    }
    if (showWinnerDialog) {
        WinnerDialog(
            onDismiss = { showWinnerDialog = false },
            onConfirm = { showWinnerDialog = false },
            winner
        )
    }
    if (showExtended) {
        ExtendedDialog(
            onDismiss = { showExtended = false }, onWin = {
            winnerData?.let {
                viewModel.updateStatusScoreName(
                    idWinner = it.id, gameStatus = GameStatus.FINISHED
                )
            }
            showExtended = false
        }, onExtend = { winningPoints ->
            viewModel.extentGame(winningPoints)
            showExtended = false
        }, winnerText = winnerData?.name, maxPoints = maxPoints
        )
    }
    if (showInfoGameDialog) {
        InfoGameDialog(
            onDismiss = { showInfoGameDialog = false }, infoGame = InfoGame(
                status = viewModel.statusGame.value,
                winningPoints = viewModel.winningPoints.toString(),
                matchPlayed = viewModel.matchPlayed
            ), onConfirm = {
                showInfoGameDialog = false
                showUpdateStatusGameDialog = true
            }, showFinishMatch = viewModel.statusGame.value == GameStatus.CONTINUE
        )
    }
    if (showUpdateStatusGameDialog && viewModel.statusGame.value == GameStatus.CONTINUE) {
        UpdateStatusGameDialog(onDismiss = { showUpdateStatusGameDialog = false }, onConfirm = {
            viewModel.updateOnlyStatus(GameStatus.FINISHED)
            showUpdateStatusGameDialog = false
        })
    }
    MatchWrapper {
        when (val matchState = matchUiState.value) {
            is MatchUiState.Success<*> -> {
                val matchData = matchState.data as MatchData4P
                val game = matchData.game
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusImage(
                        gameStatus = GameStatus.fromId(game.statusGame),
                        isInMatch = true,
                        onStatusClick = {
                            showInfoGameDialog = true
                        })
                    PointsTextAtom(text = game.scoreName1.toString())
                    PointsTextAtom(text = game.scoreName2.toString())
                    PointsTextAtom(text = game.scoreName3.toString())
                    PointsTextAtom(text = game.scoreName4.toString())
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PointsTextAtom(text = stringResource(Res.string.game))
                    PointsTextAtom(text = game.name1)
                    PointsTextAtom(text = game.name2)
                    PointsTextAtom(text = game.name3)
                    PointsTextAtom(text = game.name4)
                }
                val points = matchData.points
                var pointsGame by remember { mutableStateOf("") }
                var pointsP1 by remember { mutableStateOf("") }
                var pointsP2 by remember { mutableStateOf("") }
                var pointsP3 by remember { mutableStateOf("") }
                var pointsP4 by remember { mutableStateOf("") }
                var hintP1 by remember { mutableStateOf("") }
                var hintP2 by remember { mutableStateOf("") }
                var hintP3 by remember { mutableStateOf("") }
                var hintP4 by remember { mutableStateOf("") }
                var isPressedPoints by remember { mutableStateOf(true) }
                var isPressedP1 by remember { mutableStateOf(false) }
                var isPressedP2 by remember { mutableStateOf(false) }
                var isPressedP3 by remember { mutableStateOf(false) }
                var isPressedP4 by remember { mutableStateOf(false) }
                val shakerGame by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP1 by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP2 by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP3 by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP4 by remember { mutableStateOf(TextFieldShaker()) }
                fun recomputeHints() {
                    hintP1 = ""
                    hintP2 = ""
                    hintP3 = ""
                    hintP4 = ""
                    if (pointsGame.isEmpty()) return
                    val g = pointsGame.toShortGame()
                    val k1 = pointsP1.isNotEmpty()
                    val k2 = pointsP2.isNotEmpty()
                    val k3 = pointsP3.isNotEmpty()
                    val k4 = pointsP4.isNotEmpty()
                    if (!k1 && k2 && k3 && k4) hintP1 =
                        (g - pointsP2.toShortHint() - pointsP3.toShortHint() - pointsP4.toShortHint()).toCustomString()
                    if (!k2 && k1 && k3 && k4) hintP2 =
                        (g - pointsP1.toShortHint() - pointsP3.toShortHint() - pointsP4.toShortHint()).toCustomString()
                    if (!k3 && k1 && k2 && k4) hintP3 =
                        (g - pointsP1.toShortHint() - pointsP2.toShortHint() - pointsP4.toShortHint()).toCustomString()
                    if (!k4 && k1 && k2 && k3) hintP4 =
                        (g - pointsP1.toShortHint() - pointsP2.toShortHint() - pointsP3.toShortHint()).toCustomString()
                }

                val pointsListState = rememberLazyListState()
                scope.launch { pointsListState.animateScrollToItem(points.size) }
                LazyColumn(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = pointsListState
                ) {
                    item { HorizontalDivider(thickness = 2.dp) }
                    itemsIndexed(points, key = { _, item ->
                        item.id
                    }) { index: Int, item: Points4PUi ->
                        val isLast = index == points.lastIndex
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isLast) {
                                GameCard(onDelete = { scope.launch { viewModel.deleteLastPoints() } }) {
                                    PointsTextAtom(text = item.pointsGame, isBody = true)
                                    PointsTextAtom(text = item.pointsP1, isBody = true)
                                    PointsTextAtom(text = item.pointsP2, isBody = true)
                                    PointsTextAtom(text = item.pointsP3, isBody = true)
                                    PointsTextAtom(text = item.pointsP4, isBody = true)
                                }
                            } else {
                                PointsTextAtom(text = item.pointsGame, isBody = true)
                                PointsTextAtom(text = item.pointsP1, isBody = true)
                                PointsTextAtom(text = item.pointsP2, isBody = true)
                                PointsTextAtom(text = item.pointsP3, isBody = true)
                                PointsTextAtom(text = item.pointsP4, isBody = true)
                            }
                        }
                        if ((index + 1) % 4 == 0) {
                            HorizontalDivider(
                                thickness = 4.dp, color = MaterialTheme.colorScheme.scrim
                            )
                        } else {
                            HorizontalDivider(thickness = 2.dp)
                        }
                    }
                    if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                                    .wrapContentHeight()
                            ) {
                                TouchableText(
                                    text = pointsGame,
                                    shaker = shakerGame,
                                    isPressed = isPressedPoints,
                                    onClick = {
                                        isPressedPoints = true
                                        isPressedP1 = false
                                        isPressedP2 = false
                                        isPressedP3 = false
                                        isPressedP4 = false
                                    })
                                TouchableText(
                                    text = pointsP1,
                                    shaker = shakerP1,
                                    isPressed = isPressedP1,
                                    placeholder = hintP1,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = true
                                        isPressedP2 = false
                                        isPressedP3 = false
                                        isPressedP4 = false
                                    })
                                TouchableText(
                                    text = pointsP2,
                                    shaker = shakerP2,
                                    isPressed = isPressedP2,
                                    placeholder = hintP2,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = false
                                        isPressedP2 = true
                                        isPressedP3 = false
                                        isPressedP4 = false
                                    })
                                TouchableText(
                                    text = pointsP3,
                                    shaker = shakerP3,
                                    isPressed = isPressedP3,
                                    placeholder = hintP3,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = false
                                        isPressedP2 = false
                                        isPressedP3 = true
                                        isPressedP4 = false
                                    })
                                TouchableText(
                                    text = pointsP4,
                                    shaker = shakerP4,
                                    isPressed = isPressedP4,
                                    placeholder = hintP4,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = false
                                        isPressedP2 = false
                                        isPressedP3 = false
                                        isPressedP4 = true
                                    })
                            }
                        }
                    }
                }
                if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                    Keyboard(
                        isPressedGames = isPressedPoints,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { inputKey ->
                            if (inputKey == ADD) {
                                if (pointsGame.isEmpty()) {
                                    shakerGame.shake()
                                } else if (pointsP1.isEmpty() && hintP1.isEmpty()) {
                                    shakerP1.shake()
                                } else if (pointsP2.isEmpty() && hintP2.isEmpty()) {
                                    shakerP2.shake()
                                } else if (pointsP3.isEmpty() && hintP3.isEmpty()) {
                                    shakerP3.shake()
                                } else if (pointsP4.isEmpty() && hintP4.isEmpty()) {
                                    shakerP4.shake()
                                } else {
                                    viewModel.insertPoints(
                                        Points4PUi(
                                            pointsGame = pointsGame,
                                            pointsP1 = pointsP1.ifEmpty { hintP1 },
                                            pointsP2 = pointsP2.ifEmpty { hintP2 },
                                            pointsP3 = pointsP3.ifEmpty { hintP3 },
                                            pointsP4 = pointsP4.ifEmpty { hintP4 })
                                    )
                                    pointsGame = ""
                                    pointsP1 = ""
                                    pointsP2 = ""
                                    pointsP3 = ""
                                    pointsP4 = ""
                                    hintP1 = ""
                                    hintP2 = ""
                                    hintP3 = ""
                                    hintP4 = ""
                                }
                            } else {
                                if (isPressedPoints) {
                                    manageUserInputKey(
                                        inputText = pointsGame, inputKey = inputKey
                                    ) { text ->
                                        pointsGame = text
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP1) {
                                    manageUserInputKey(
                                        inputText = pointsP1, inputKey = inputKey
                                    ) { text ->
                                        pointsP1 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP2 == BOLT) pointsP2 = ""
                                            if (pointsP3 == BOLT) pointsP3 = ""
                                            if (pointsP4 == BOLT) pointsP4 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP2) {
                                    manageUserInputKey(
                                        inputText = pointsP2, inputKey = inputKey
                                    ) { text ->
                                        pointsP2 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP1 == BOLT) pointsP1 = ""
                                            if (pointsP3 == BOLT) pointsP3 = ""
                                            if (pointsP4 == BOLT) pointsP4 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP3) {
                                    manageUserInputKey(
                                        inputText = pointsP3, inputKey = inputKey
                                    ) { text ->
                                        pointsP3 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP1 == BOLT) pointsP1 = ""
                                            if (pointsP2 == BOLT) pointsP2 = ""
                                            if (pointsP4 == BOLT) pointsP4 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP4) {
                                    manageUserInputKey(
                                        inputText = pointsP4, inputKey = inputKey
                                    ) { text ->
                                        pointsP4 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP1 == BOLT) pointsP1 = ""
                                            if (pointsP2 == BOLT) pointsP2 = ""
                                            if (pointsP3 == BOLT) pointsP3 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                            }
                        })
                } else {
                    var shouDialog by remember { mutableStateOf(false) }
                    InsertFloatingActionButton(
                        onClick = {
                            if (viewModel.statusGame.value == GameStatus.EXTENDED || viewModel.statusGame.value == GameStatus.EXTENDED_MANDATORY) {
                                viewModel.checkStatusAndScore()
                            } else {
                                shouDialog = true
                            }
                        }, modifier = Modifier.align(Alignment.End).padding(16.dp)
                    )
                    if (shouDialog) {
                        InsertGameDialogBase(
                            onDismissRequest = { shouDialog = false },
                            onClick = { winningPoints -> viewModel.resetGame(winningPoints) },
                            winningPointsViewModel = winningPointsViewModel
                        )
                    }
                }
            }

            is MatchUiState.Error -> {}
            MatchUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }
        }
    }
}


@Composable
internal fun MatchScreen2Groups(
    viewModel: ViewModelBase, winningPointsViewModel: WinningPointsViewModel
) {
    val matchUiState = viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var showInfoGameDialog by remember { mutableStateOf(false) }
    var showUpdateStatusGameDialog by remember { mutableStateOf(false) }
    var showWinnerDialog by remember { mutableStateOf(false) }
    var showExtended by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf("") }
    var winnerData by remember { mutableStateOf<Winner?>(null) }
    var maxPoints by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.collectLatest { event ->
            when (event) {
                is SideEffect.ShowWinner -> {
                    if (event.winnerName.isNotEmpty()) winner = event.winnerName
                    showWinnerDialog = true
                }

                is SideEffect.ShowExtended -> {
                    winnerData = event.winner
                    maxPoints = event.maxPoints
                    showExtended = true
                }

                is SideEffect.ShowExtendedMandatory -> {
                    winnerData = null
                    maxPoints = event.maxPoints
                    showExtended = true
                }
            }
        }
    }
    if (showWinnerDialog) {
        WinnerDialog(
            onDismiss = { showWinnerDialog = false },
            onConfirm = { showWinnerDialog = false },
            winner
        )
    }
    if (showExtended) {
        ExtendedDialog(
            onDismiss = { showExtended = false }, onWin = {
            winnerData?.let {
                viewModel.updateStatusScoreName(
                    idWinner = it.id, gameStatus = GameStatus.FINISHED
                )
            }
            showExtended = false
        }, onExtend = { winningPoints ->
            viewModel.extentGame(winningPoints)
            showExtended = false
        }, winnerText = winnerData?.name, maxPoints = maxPoints
        )
    }
    MatchWrapper {
        when (val matchState = matchUiState.value) {
            is MatchUiState.Success<*> -> {
                var pointsGame by remember { mutableStateOf("") }
                var pointsP1 by remember { mutableStateOf("") }
                var pointsP2 by remember { mutableStateOf("") }
                var hintP1 by remember { mutableStateOf("") }
                var hintP2 by remember { mutableStateOf("") }
                var isPressedPoints by remember { mutableStateOf(true) }
                var isPressedP1 by remember { mutableStateOf(false) }
                var isPressedP2 by remember { mutableStateOf(false) }
                val shakerGame by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP1 by remember { mutableStateOf(TextFieldShaker()) }
                val shakerP2 by remember { mutableStateOf(TextFieldShaker()) }
                if (showInfoGameDialog) {
                    InfoGameDialog(
                        onDismiss = { showInfoGameDialog = false }, infoGame = InfoGame(
                            status = viewModel.statusGame.value,
                            winningPoints = viewModel.winningPoints.toString(),
                            matchPlayed = viewModel.matchPlayed
                        ), onConfirm = {
                            showInfoGameDialog = false
                            showUpdateStatusGameDialog = true
                        }, showFinishMatch = viewModel.statusGame.value == GameStatus.CONTINUE
                    )
                }
                if (showUpdateStatusGameDialog && viewModel.statusGame.value == GameStatus.CONTINUE) {
                    UpdateStatusGameDialog(
                        onDismiss = { showUpdateStatusGameDialog = false },
                        onConfirm = {
                            viewModel.updateOnlyStatus(GameStatus.FINISHED)
                            showUpdateStatusGameDialog = false
                        })
                }
                val matchData = matchState.data as MatchData2Groups
                val game = matchData.game
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusImage(
                        gameStatus = GameStatus.fromId(game.statusGame),
                        isInMatch = true,
                        onStatusClick = {
                            showInfoGameDialog = true
                        })
                    PointsTextAtom(text = game.scoreName1.toString())
                    PointsTextAtom(text = game.scoreName2.toString())
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PointsTextAtom(text = stringResource(Res.string.game))
                    PointsTextAtom(text = game.name1)
                    PointsTextAtom(text = game.name2)
                }
                val points = matchData.points
                fun recomputeHints() {
                    hintP1 = ""
                    hintP2 = ""
                    if (pointsGame.isEmpty()) return
                    val g = pointsGame.toShortGame()
                    val k1 = pointsP1.isNotEmpty()
                    val k2 = pointsP2.isNotEmpty()
                    if (!k1 && k2) hintP1 = (g - pointsP2.toShortHint()).toCustomString()
                    if (!k2 && k1) hintP2 = (g - pointsP1.toShortHint()).toCustomString()
                }

                val pointsListState = rememberLazyListState()
                scope.launch { pointsListState.animateScrollToItem(points.size) }
                LazyColumn(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = pointsListState
                ) {
                    item { HorizontalDivider(thickness = 2.dp) }
                    itemsIndexed(points, key = { _, item ->
                        item.id
                    }) { index: Int, item: Points2GroupsUi ->
                        val isLast = index == points.lastIndex
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isLast) {
                                GameCard(onDelete = { scope.launch { viewModel.deleteLastPoints() } }) {
                                    PointsTextAtom(text = item.pointsGame, isBody = true)
                                    PointsTextAtom(text = item.pointsP1, isBody = true)
                                    PointsTextAtom(text = item.pointsP2, isBody = true)
                                }
                            } else {
                                PointsTextAtom(text = item.pointsGame, isBody = true)
                                PointsTextAtom(text = item.pointsP1, isBody = true)
                                PointsTextAtom(text = item.pointsP2, isBody = true)
                            }
                        }
                        if ((index + 1) % 2 == 0) {
                            HorizontalDivider(
                                thickness = 4.dp, color = MaterialTheme.colorScheme.scrim
                            )
                        } else {
                            HorizontalDivider(thickness = 2.dp)
                        }
                    }
                    if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                                    .wrapContentHeight()
                            ) {
                                TouchableText(
                                    text = pointsGame,
                                    shaker = shakerGame,
                                    isPressed = isPressedPoints,
                                    onClick = {
                                        isPressedPoints = true
                                        isPressedP1 = false
                                        isPressedP2 = false
                                    })
                                TouchableText(
                                    text = pointsP1,
                                    shaker = shakerP1,
                                    isPressed = isPressedP1,
                                    placeholder = hintP1,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = true
                                        isPressedP2 = false
                                    })
                                TouchableText(
                                    text = pointsP2,
                                    shaker = shakerP2,
                                    isPressed = isPressedP2,
                                    placeholder = hintP2,
                                    onClick = {
                                        isPressedPoints = false
                                        isPressedP1 = false
                                        isPressedP2 = true
                                    })
                            }
                        }
                    }
                }
                if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                    Keyboard(
                        isPressedGames = isPressedPoints,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { inputKey ->
                            if (inputKey == ADD) {
                                if (pointsGame.isEmpty()) {
                                    shakerGame.shake()
                                } else if (pointsP1.isEmpty() && hintP1.isEmpty()) {
                                    shakerP1.shake()
                                } else if (pointsP2.isEmpty() && hintP2.isEmpty()) {
                                    shakerP2.shake()
                                } else {
                                    viewModel.insertPoints(
                                        Points2GroupsUi(
                                            pointsGame = pointsGame,
                                            pointsP1 = pointsP1.ifEmpty { hintP1 },
                                            pointsP2 = pointsP2.ifEmpty { hintP2 })
                                    )
                                    pointsGame = ""
                                    pointsP1 = ""
                                    pointsP2 = ""
                                    hintP1 = ""
                                    hintP2 = ""
                                }
                            } else {
                                if (isPressedPoints) {
                                    manageUserInputKey(
                                        inputText = pointsGame, inputKey = inputKey
                                    ) { text ->
                                        pointsGame = text
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP1) {
                                    manageUserInputKey(
                                        inputText = pointsP1, inputKey = inputKey
                                    ) { text ->
                                        pointsP1 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP2 == BOLT) pointsP2 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                                if (isPressedP2) {
                                    manageUserInputKey(
                                        inputText = pointsP2, inputKey = inputKey
                                    ) { text ->
                                        pointsP2 = text
                                        if (inputKey == BOLT) {
                                            if (pointsP1 == BOLT) pointsP1 = ""
                                        }
                                        recomputeHints()
                                    }
                                }
                            }
                        })
                } else {
                    var shouDialog by remember { mutableStateOf(false) }
                    InsertFloatingActionButton(
                        onClick = {
                            if (viewModel.statusGame.value == GameStatus.EXTENDED || viewModel.statusGame.value == GameStatus.EXTENDED_MANDATORY) {
                                viewModel.checkStatusAndScore()
                            } else {
                                shouDialog = true
                            }
                        }, modifier = Modifier.align(Alignment.End).padding(16.dp)
                    )
                    if (shouDialog) {
                        InsertGameDialogBase(
                            onDismissRequest = { shouDialog = false },
                            onClick = { winningPoints -> viewModel.resetGame(winningPoints) },
                            winningPointsViewModel = winningPointsViewModel
                        )
                    }
                }
            }

            is MatchUiState.Error -> {}
            MatchUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }
        }
    }
}


fun Int.toCustomString(): String {
    if (this < 0) {
        return ""
    }
    return this.toString()
}

private fun manageUserInputKey(
    inputText: String, inputKey: String, onInputTextChanged: (String) -> Unit
) {
    if (inputKey == DELETE) {
        if (inputText == MINUS_10) {
            onInputTextChanged("")

        } else {
            onInputTextChanged(inputText.dropLast(1))
        }
    } else if (inputKey == MINUS_10 || inputKey == BOLT || inputKey == ZERO && inputText.isEmpty()) {
        onInputTextChanged(inputKey)
    } else {
        if (inputText.length < 3 || inputText == MINUS_10) {
            val text = if (inputText == ZERO || inputText == BOLT || inputText == MINUS_10) {
                inputKey
            } else {
                inputText + inputKey
            }
            onInputTextChanged(
                text
            )
        }
    }
}

@Composable
fun RowScope.AddIcon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val tint = Color(0xFF00C853)
    val haptic = LocalHapticFeedback.current
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.94f else 1f, tween(90), label = "")
    val elevation by animateDpAsState(if (pressed) 0.dp else 4.dp, tween(90), label = "")
    val bg by animateColorAsState(
        if (pressed) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        else MaterialTheme.colorScheme.surface, tween(120), label = ""
    )
    Card(
        modifier = modifier.padding(4.dp).weight(1f).scale(scale),
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        interactionSource = interaction,
        colors = CardDefaults.cardColors(containerColor = bg),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                maxLines = 1,
                color = tint,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 32.sp, fontWeight = FontWeight.Black
                )
            )
        }
    }
}

@Composable
fun RowScope.BackspaceIcon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val tint = Color(0xFFD32F2F)
    val haptic = LocalHapticFeedback.current
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.94f else 1f, tween(90), label = "")
    val elevation by animateDpAsState(if (pressed) 0.dp else 4.dp, tween(90), label = "")
    val bg by animateColorAsState(
        if (pressed) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        else MaterialTheme.colorScheme.surface, tween(120), label = ""
    )
    Card(
        modifier = modifier.padding(8.dp).weight(1f).scale(scale),
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        interactionSource = interaction,
        colors = CardDefaults.cardColors(containerColor = bg),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = tint
            )
        }
    }
}


@Composable
private fun MatchWrapper(
    modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = modifier.alpha(GLOBAL_ALPHA).padding(8.dp).wrapContentSize().background(
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            shape = MaterialTheme.shapes.large
        ),
    ) {
        content()
    }

}

@Composable
private fun RowScope.PointsTextAtom(
    text: String, isBody: Boolean = false, modifier: Modifier = Modifier
) {

    Text(
        text = text,
        modifier = modifier.padding(8.dp).weight(1F).align(Alignment.CenterVertically),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface,
        style = if (isBody) MaterialTheme.typography.bodyMedium
        else MaterialTheme.typography.titleMedium,
    )
}


@Composable
fun RowScope.TouchableText(
    text: String,
    shaker: TextFieldShaker,
    isPressed: Boolean = false,
    placeholder: String = "",
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showIndicator by remember { mutableStateOf(true) }

    if (isPressed && text.isEmpty()) {
        LaunchedEffect(Unit) {
            while (true) {
                showIndicator = !showIndicator
                delay(500)
            }
        }
    }

    val vibrationOffset = remember { Animatable(0f) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(shaker.shouldShake.value) {
        if (shaker.shouldShake.value) {
            focusRequester.requestFocus()
            onClick()
            repeat(6) {
                vibrationOffset.snapTo(if (it % 2 == 0) 6f else -6f)
                delay(30)
            }
            vibrationOffset.snapTo(0f)
        }
        shaker.reset()
    }

    Box(
        modifier = modifier.padding(1.dp).height(32.dp).weight(1f).focusRequester(focusRequester)
            .offset(x = vibrationOffset.value.dp).background(
                if (isPressed) MaterialTheme.colorScheme.onTertiary
                else MaterialTheme.colorScheme.tertiary, shape = MaterialTheme.shapes.small
            ).clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        when {
            text.isNotEmpty() -> Text(
                text = text.take(3),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
            )

            isPressed && placeholder.isEmpty() -> BlinkingCursor()

            else -> Text(
                text = placeholder,
                style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}


@Composable
fun BlinkingCursor(
    text: String = "|", durationMillis: Int = 500, modifier: Modifier = Modifier

) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.alpha(alpha)
    )
}


@Composable
private fun Keyboard(
    isPressedGames: Boolean = false, onClick: (String) -> Unit, modifier: Modifier = Modifier
) {
    val keyAlpha = if (!isPressedGames) 1f else 0f
    Column(
        modifier = modifier.fillMaxWidth().padding(top = 4.dp).background(
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            shape = MaterialTheme.shapes.large
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            KeyAtom(text = ONE, onClick = onClick)
            KeyAtom(text = TWO, onClick = onClick)
            KeyAtom(text = THREE, onClick = onClick)
            KeyAtom(text = FOUR, onClick = onClick)
            KeyAtom(text = FIVE, onClick = onClick)
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            KeyAtom(text = SIX, onClick = onClick)
            KeyAtom(text = SEVEN, onClick = onClick)
            KeyAtom(text = EIGHT, onClick = onClick)
            KeyAtom(text = NINE, onClick = onClick)
            KeyAtom(text = ZERO, onClick = onClick)
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            KeyAtom(
                text = BOLT,
                onClick = onClick,
                color = Color(0xFFFFEB3B),
                modifier = modifier.graphicsLayer {
                    alpha = keyAlpha
                })
            KeyAtom(
                text = MINUS_10,
                onClick = onClick,
                color = Color(0xFFE57373),
                modifier = modifier.graphicsLayer {
                    alpha = keyAlpha
                })
            KeyAtom(
                text = PLUS, onClick = {
                    onClick(ADD)
                }, style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 32.sp, fontWeight = FontWeight.Black
                ), color = Color(0xFF00C853)
            )

            KeyAtom(
                text = BACK_SPACE, onClick = {
                    onClick(DELETE)
                }, style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 32.sp, fontWeight = FontWeight.Black
                ), color = Color(0xFFD32F2F), imageVector = Icons.AutoMirrored.Filled.ArrowBack
            )
        }
    }
}

private const val ONE = "1"
private const val TWO = "2"
private const val THREE = "3"
private const val FOUR = "4"
private const val FIVE = "5"
private const val SIX = "6"
private const val SEVEN = "7"
private const val EIGHT = "8"
private const val NINE = "9"
private const val ZERO = "0"
const val BOLT = "B"
const val MINUS_10 = "-10"
private const val ADD = "add"
private const val DELETE = "delete"

private const val PLUS = "+"

private const val BACK_SPACE = "<--"

@Composable
fun RowScope.KeyAtom(
    text: String,
    onClick: (String) -> Unit,
    color: Color = Color.Unspecified,
    style: TextStyle = MaterialTheme.typography.displayLarge,
    imageVector: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.94f else 1f, tween(90), label = "")
    val elevation by animateDpAsState(if (pressed) 0.dp else 4.dp, tween(90), label = "")
    val bg by animateColorAsState(
        if (pressed) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surface, tween(120), label = ""
    )
    val tint = if (color == Color.Unspecified) MaterialTheme.colorScheme.primary else color

    Card(
        modifier = modifier.padding(2.dp).weight(1f).scale(scale),
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick(text)
        },
        interactionSource = interaction,
        colors = CardDefaults.cardColors(containerColor = bg),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.Center
        ) {

            if (imageVector == null) {
                Text(
                    text = text,
                    maxLines = 1,
                    style = style,
                    textAlign = TextAlign.Center,
                    color = tint
                )
            } else {
                Icon(
                    imageVector = imageVector, contentDescription = "Backspace", tint = tint
                )
            }
        }
    }
}


@Composable
private fun InfoGameDialog(
    onDismiss: () -> Unit, onConfirm: () -> Unit, infoGame: InfoGame, showFinishMatch: Boolean
) {
    val statusGameText = when (infoGame.status) {
        GameStatus.CONTINUE -> {
            stringResource(Res.string.alert_dialog_playing)
        }

        GameStatus.FINISHED -> {
            stringResource(Res.string.alert_dialog_finished)

        }

        GameStatus.EXTENDED, GameStatus.EXTENDED_MANDATORY -> {
            stringResource(Res.string.alert_dialog_to_extend)
        }

    }
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier.padding(24.dp).fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                val modifierMarginTop = Modifier.padding(top = 8.dp)
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(Res.string.game_status),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = statusGameText,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    textAlign = TextAlign.Center,
                    modifier = modifierMarginTop,
                    text = stringResource(Res.string.winning_points),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = infoGame.winningPoints,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    textAlign = TextAlign.Center,
                    modifier = modifierMarginTop,
                    text = stringResource(Res.string.games_played),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = infoGame.matchPlayed,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (showFinishMatch) {
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(
                            text = stringResource(Res.string.alert_dialog_finish_match),
                            color = Color.White
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun WinnerDialog(
    onDismiss: () -> Unit, onConfirm: () -> Unit, winnerText: String
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier.padding(24.dp).fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                Text(
                    text = stringResource(Res.string.winner_is),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = textColor
                )
                Text(
                    text = winnerText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = textColor
                )
                Button(onClick = {
                    onConfirm()
                }) {
                    Text(text = stringResource(Res.string.ok))
                }
            }

        }
    }
}

@Composable
private fun ExtendedDialog(
    onDismiss: () -> Unit,
    onWin: () -> Unit,
    onExtend: (Short) -> Unit,
    winnerText: String?,
    maxPoints: String
) {
    val shakerWinningPoints by remember { mutableStateOf(TextFieldShaker()) }
    Dialog(onDismissRequest = onDismiss) {
        var newWinningPoints by remember { mutableStateOf("") }

        Box(
            modifier = Modifier.padding(24.dp).fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ShakerTextFieldAtom(
                    value = newWinningPoints, onValueChange = { newText ->
                        if (!newText.startsWith("0") && newText.all { it.isDigit() }) {
                            newWinningPoints = newText
                        }
                    }, placeholder = stringResource(
                        Res.string.dialog_fragment_greater_than, maxPoints
                    ), shaker = shakerWinningPoints, isOnlyDigit = true
                )
                if (winnerText != null) {
                    Text(
                        text = stringResource(Res.string.dialog_fragment_extend_match_info),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(Res.string.dialog_fragment_extend_match_q),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,

                        )
                } else {
                    Text(
                        text = stringResource(Res.string.dialog_fragment_mandatory_extend_match_info1),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = stringResource(Res.string.dialog_fragment_mandatory_extend_match_info2),
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }

                Button(
                    onClick = {
                        val winningPoints = newWinningPoints.toShortCustom()
                        if (winningPoints > maxPoints.toShort()) {
                            onExtend(winningPoints)
                        } else {
                            shakerWinningPoints.shake()
                            newWinningPoints = ""
                        }
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
                ) {
                    Text(
                        text = stringResource(Res.string.dialog_fragment_extend_match),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
                winnerText?.let {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        onClick = {
                            onWin()
                        }) {
                        Text(
                            text = stringResource(Res.string.dialog_fragment_win, it),
                            maxLines = 2,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }


            }
        }
    }

}

@Composable
private fun UpdateStatusGameDialog(
    onDismiss: () -> Unit, onConfirm: () -> Unit, modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier.background(
                MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp)
            ).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.dialog_are_you_sure),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(16.dp))
            AnimatedColorCircle()
            Spacer(Modifier.height(16.dp))
            val modifierMargins = Modifier.padding(8.dp)
            Row {
                Button(onClick = onDismiss, modifier = modifierMargins) {
                    Text(text = stringResource(Res.string.no))
                }
                ConfirmYesButton(onConfirm = onConfirm, modifier = modifierMargins)
            }
        }
    }
}

@Composable
fun ConfirmYesButton(onConfirm: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier,
        onClick = onConfirm,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        Text(
            text = stringResource(Res.string.yes), color = Color.White
        )
    }
}

@Composable
fun AnimatedColorCircle() {
    var startAnimation by remember { mutableStateOf(false) }

    val animatedColor by animateColorAsState(
        targetValue = if (startAnimation) Color.Red else Color.Green,
        animationSpec = tween(durationMillis = 2000),
        label = ""
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Circle(
        modifier = Modifier.size(24.dp).clip(CircleShape).background(animatedColor)
    )
}

data class InfoGame(val status: GameStatus, val winningPoints: String, val matchPlayed: String)


@Preview
@Composable
private fun MatchScreen2Preview() {
    //MatchScreen2(1)
}