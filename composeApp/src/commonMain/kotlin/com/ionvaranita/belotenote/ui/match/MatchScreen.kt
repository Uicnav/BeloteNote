package com.ionvaranita.belotenote.ui.match

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
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
import belotenote.composeapp.generated.resources.ic_writting_indicator
import belotenote.composeapp.generated.resources.no
import belotenote.composeapp.generated.resources.ok
import belotenote.composeapp.generated.resources.winner_is
import com.ionvaranita.belotenote.Circle
import com.ionvaranita.belotenote.StatusImage
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.model.Points2PUi
import com.ionvaranita.belotenote.domain.model.Points3PUi
import com.ionvaranita.belotenote.domain.model.Points4PUi
import com.ionvaranita.belotenote.domain.model.toShortCustom
import com.ionvaranita.belotenote.domain.model.toShortCustomCalculated
import com.ionvaranita.belotenote.ui.table.GameCard
import com.ionvaranita.belotenote.ui.table.InsertFloatingActionButton
import com.ionvaranita.belotenote.ui.table.InsertGameDialogBase
import com.ionvaranita.belotenote.ui.table.ShakerTextFieldAtom
import com.ionvaranita.belotenote.ui.table.TextFieldShaker
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MatchScreen2(viewModel: ViewModelBase) {


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
                    if (event.winnerName.isNotEmpty()) {
                        winner = event.winnerName
                    }
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
        WinnerDialog(onDismiss = {
            showWinnerDialog = false
        }, onConfirm = {
            showWinnerDialog = false
        }, winner)
    }

    if (showExtended) {
        ExtendedDialog(onDismiss = {
            showExtended = false
        }, onWin = {
            winnerData?.let {
                viewModel.updateStatusScoreName(it)
            }
            showExtended = false
        }, onExtend = { winningPoints ->
            viewModel.extentGame(winningPoints)
            showExtended = false
        }, winnerText = winnerData?.name, maxPoints = maxPoints)
    }

    if (showInfoGameDialog) {
        InfoGameDialog(
            onDismiss = {
                showInfoGameDialog = false
            }, infoGame = InfoGame(
                status = viewModel.statusGame.value,
                winningPoints = viewModel.winningPoints.toString()
            ), onConfirm = {
                showInfoGameDialog = false
                showUpdateStatusGameDialog = true
            }, showFinishMatch = viewModel.statusGame.value == GameStatus.CONTINUE
        )
    }
    if (showUpdateStatusGameDialog && viewModel.statusGame.value == GameStatus.CONTINUE) {
        UpdateStatusGameDialog(onDismiss = {
            showUpdateStatusGameDialog = false
        }, onConfirm = {
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusImage(
                        gameStatus = GameStatus.fromId(game.statusGame),
                        modifier = Modifier.clickable {
                            showInfoGameDialog = true
                        }.weight(1F)
                    )
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
                var pointsMe by remember { mutableStateOf("") }
                var pointsYouS by remember { mutableStateOf("") }
                var isPressedPoints by remember { mutableStateOf(true) }
                var isPressedMe by remember { mutableStateOf(false) }
                var isPressedYouS by remember { mutableStateOf(false) }
                val pointsListState = rememberLazyListState()
                scope.launch {
                    pointsListState.animateScrollToItem(points.size)
                }

                LazyColumn(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = pointsListState
                ) {

                    itemsIndexed(points) { index: Int, item: Points2PUi ->
                        val isLast = index == points.lastIndex
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isLast && viewModel.statusGame.value == GameStatus.CONTINUE) {
                                GameCard(onDelete = {
                                    scope.launch {
                                        viewModel.deleteLastPoints()
                                    }
                                }, isTable = false) {
                                    PointsTextAtom(text = item.pointsGame, isBody = true)
                                    PointsTextAtom(text = item.pointsP1,isBody = true)
                                    PointsTextAtom(text = item.pointsP2, isBody = true)
                                }
                            } else {
                                PointsTextAtom(text = item.pointsGame,isBody = true)
                                PointsTextAtom(text = item.pointsP1,isBody = true)
                                PointsTextAtom(text = item.pointsP2,isBody = true)
                            }
                        }
                        if ((index + 1) % 2 == 0) {
                            HorizontalDivider(
                                    thickness = 4.dp
                            )
                        }
                    }
                }

                if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                    Row(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    ) {
                        TouchableText(text = pointsGame, isPressed = isPressedPoints, onClick = {

                            isPressedPoints = true
                            isPressedMe = false
                            isPressedYouS = false

                        })

                        TouchableText(text = pointsMe, isPressed = isPressedMe, onClick = {

                            isPressedPoints = false
                            isPressedMe = true
                            isPressedYouS = false

                        })
                        TouchableText(text = pointsYouS, isPressed = isPressedYouS, onClick = {
                            isPressedPoints = false
                            isPressedMe = false
                            isPressedYouS = true
                        })
                    }
                    Keyboard(
                        isPressedGames = isPressedPoints,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { inputKey ->
                            if (inputKey == ADD) {
                                viewModel.insertPoints(
                                    Points2PUi(
                                        pointsP1 = pointsMe,
                                        pointsGame = pointsGame,
                                        pointsP2 = pointsYouS
                                    )
                                )
                            } else {
                                if (isPressedPoints) {

                                    manageUserInputKey(
                                        inputText = pointsGame, inputKey = inputKey
                                    ) { text ->
                                        pointsGame = text
                                    }
                                }
                                if (isPressedMe) {
                                    manageUserInputKey(
                                        inputText = pointsMe, inputKey = inputKey
                                    ) { text ->
                                        pointsMe = text
                                        if (inputKey == MINUS_10 || inputKey == BOLT) {
                                            if (pointsYouS == MINUS_10 || pointsYouS == BOLT
                                            ) {
                                                pointsYouS = ""
                                            }
                                        }
                                        if (pointsGame.isNotEmpty()) {
                                            pointsYouS =
                                                (pointsGame.toShortCustom() - text.toShortCustom()).toCustomString()
                                        }
                                    }
                                }
                                if (isPressedYouS) {
                                    manageUserInputKey(
                                        inputText = pointsYouS, inputKey = inputKey
                                    ) { text ->
                                        pointsYouS = text
                                        if (inputKey.equals(MINUS_10) || inputKey.equals(BOLT)) {
                                            if (pointsMe.equals(MINUS_10) || pointsMe.equals(BOLT)) {
                                                pointsMe = ""
                                            }
                                        }
                                        if (pointsGame.isNotEmpty()) {
                                            pointsMe =
                                                (pointsGame.toShortCustom() - text.toShortCustom()).toCustomString()
                                        }
                                    }

                                }
                            }

                        })
                } else {
                    var shouDialog by remember { mutableStateOf(false) }
                    InsertFloatingActionButton(onClick = {
                        if (viewModel.statusGame.value == GameStatus.EXTENDED || viewModel.statusGame.value == GameStatus.EXTENDED_MANDATORY) {
                            viewModel.checkIsExtended()
                        } else {
                            shouDialog = true
                        }
                    }, modifier = Modifier.align(Alignment.End).padding(16.dp))
                    if (shouDialog) {
                        InsertGameDialogBase(onDismissRequest = {
                            shouDialog = false
                        }, onClick = { winningPoints ->
                            viewModel.resetGame(winningPoints)
                        })
                    }

                }


            }

            is MatchUiState.Error -> {

            }

            MatchUiState.Loading -> {
                CircularProgressIndicator()
            }
        }

    }
}

@Composable
internal fun MatchScreen3(viewModel: ViewModelBase) {

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
                    if (event.winnerName.isNotEmpty()) {
                        winner = event.winnerName
                    }
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
        WinnerDialog(onDismiss = {
            showWinnerDialog = false
        }, onConfirm = {
            showWinnerDialog = false
        }, winner)
    }

    if (showExtended) {
        ExtendedDialog(onDismiss = {
            showExtended = false
        }, onWin = {
            winnerData?.let {
                viewModel.updateStatusScoreName(it)
            }
            showExtended = false
        }, onExtend = { winningPoints ->
            viewModel.extentGame(winningPoints)
            showExtended = false
        }, winnerText = winnerData?.name, maxPoints = maxPoints)
    }

    if (showInfoGameDialog) {
        InfoGameDialog(
            onDismiss = {
                showInfoGameDialog = false
            }, infoGame = InfoGame(
                status = viewModel.statusGame.value,
                winningPoints = viewModel.winningPoints.toString()
            ), onConfirm = {
                showInfoGameDialog = false
                showUpdateStatusGameDialog = true
            }, showFinishMatch = viewModel.statusGame.value == GameStatus.CONTINUE
        )
    }
    if (showUpdateStatusGameDialog && viewModel.statusGame.value == GameStatus.CONTINUE) {
        UpdateStatusGameDialog(onDismiss = {
            showUpdateStatusGameDialog = false
        }, onConfirm = {
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
                        modifier = Modifier.clickable {
                            showInfoGameDialog = true
                        }.weight(1F)
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
                var isPressedPoints by remember { mutableStateOf(true) }
                var isPressedP1 by remember { mutableStateOf(false) }
                var isPressedP2 by remember { mutableStateOf(false) }
                var isPressedP3 by remember { mutableStateOf(false) }
                val pointsListState = rememberLazyListState()
                scope.launch {
                    pointsListState.animateScrollToItem(points.size)
                }

                LazyColumn(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = pointsListState
                ) {

                    itemsIndexed(points) { index: Int, item: Points3PUi ->
                        val isLast = index == points.lastIndex
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isLast && viewModel.statusGame.value == GameStatus.CONTINUE) {
                                GameCard(onDelete = {
                                    scope.launch {
                                        viewModel.deleteLastPoints()
                                    }
                                }) {
                                    PointsTextAtom(text = item.pointsGame, isBody = true)
                                    PointsTextAtom(text = item.pointsP1,isBody = true)
                                    PointsTextAtom(text = item.pointsP2,isBody = true)
                                    PointsTextAtom(text = item.pointsP3,isBody = true)
                                }
                            } else {
                                PointsTextAtom(text = item.pointsGame, isBody = true)
                                PointsTextAtom(text = item.pointsP1,isBody = true)
                                PointsTextAtom(text = item.pointsP2,isBody = true)
                                PointsTextAtom(text = item.pointsP3,isBody = true)
                            }
                        }
                        if ((index + 1) % 3 == 0) {
                            HorizontalDivider(
                                thickness = 4.dp
                            )
                        }
                    }
                }
                if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                    Row(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    ) {
                        TouchableText(text = pointsGame, isPressed = isPressedPoints, onClick = {

                            isPressedPoints = true
                            isPressedP1 = false
                            isPressedP2 = false
                            isPressedP3 = false

                        })

                        TouchableText(text = pointsP1, isPressed = isPressedP1, onClick = {

                            isPressedPoints = false
                            isPressedP1 = true
                            isPressedP2 = false
                            isPressedP3 = false

                        })
                        TouchableText(text = pointsP2, isPressed = isPressedP2, onClick = {
                            isPressedPoints = false
                            isPressedP1 = false
                            isPressedP2 = true
                            isPressedP3 = false
                        })
                        TouchableText(text = pointsP3, isPressed = isPressedP3, onClick = {
                            isPressedPoints = false
                            isPressedP1 = false
                            isPressedP2 = false
                            isPressedP3 = true
                        })
                    }
                    Keyboard(
                        isPressedGames = isPressedPoints,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { inputKey ->
                            if (inputKey == ADD) {
                                viewModel.insertPoints(
                                    Points3PUi(
                                        pointsP1 = pointsP1,
                                        pointsGame = pointsGame,
                                        pointsP2 = pointsP2,
                                        pointsP3 = pointsP3
                                    )
                                )
                            } else {
                                if (isPressedPoints) {

                                    manageUserInputKey(
                                        inputText = pointsGame, inputKey = inputKey
                                    ) { text ->
                                        pointsGame = text
                                    }
                                }
                                if (isPressedP1) {
                                    manageUserInputKey(
                                        inputText = pointsP1, inputKey = inputKey
                                    ) { text ->
                                        pointsP1 = text
                                        if (inputKey == MINUS_10 || inputKey == BOLT) {
                                            if (pointsP2 == MINUS_10 || pointsP2 == BOLT) {
                                                pointsP2 = ""
                                            }
                                            if (pointsP3 == MINUS_10 || pointsP3 == BOLT) {
                                                pointsP3 = ""
                                            }
                                        }
                                        if (pointsGame.isNotEmpty()) {
                                            if (pointsP3.isNotEmpty() && pointsP2.isEmpty()) {
                                                pointsP2 =
                                                    (pointsGame.toShortCustom() - pointsP1.toShortCustom() - pointsP3.toShortCustom()).toCustomString()
                                            }
                                            if (pointsP2.isNotEmpty() && pointsP3.isEmpty()) {
                                                pointsP3 =
                                                    (pointsGame.toShortCustom() - pointsP1.toShortCustom() - pointsP2.toShortCustom()).toCustomString()
                                            }
                                        }
                                    }
                                }
                                if (isPressedP2) {
                                    manageUserInputKey(
                                        inputText = pointsP2, inputKey = inputKey
                                    ) { text ->
                                        pointsP2 = text
                                        if (inputKey.equals(MINUS_10) || inputKey.equals(BOLT)) {
                                            if (pointsP1.equals(MINUS_10) || pointsP1.equals(BOLT)) {
                                                pointsP1 = ""
                                            }
                                            if (pointsP3.equals(MINUS_10) || pointsP3.equals(BOLT)) {
                                                pointsP3 = ""
                                            }
                                        }
                                        if (pointsGame.isNotEmpty()) {
                                            if (pointsP1.isNotEmpty() && pointsP3.isEmpty()) {
                                                pointsP3 =
                                                    (pointsGame.toShortCustom() - pointsP2.toShortCustom() - pointsP1.toShortCustom()).toCustomString()
                                            }
                                            if (pointsP3.isNotEmpty() && pointsP1.isEmpty()) {
                                                pointsP1 =
                                                    (pointsGame.toShortCustom() - pointsP2.toShortCustom() - pointsP3.toShortCustom()).toCustomString()
                                            }
                                        }
                                    }

                                }
                                if (isPressedP3) {
                                    manageUserInputKey(
                                        inputText = pointsP3, inputKey = inputKey
                                    ) { text ->
                                        pointsP3 = text
                                        if (inputKey == MINUS_10 || inputKey == BOLT) {
                                            if (pointsP1 == MINUS_10 || pointsP1 == BOLT) {
                                                pointsP1 = ""
                                            }
                                            if (pointsP2 == MINUS_10 || pointsP2.equals(BOLT)) {
                                                pointsP2 = ""
                                            }
                                        }
                                        if (pointsGame.isNotEmpty()) {
                                            if (pointsP1.isNotEmpty() && pointsP2.isEmpty()) {
                                                pointsP2 =
                                                    (pointsGame.toShortCustom() - pointsP3.toShortCustom() - pointsP1.toShortCustom()).toCustomString()
                                            }
                                            if (pointsP2.isNotEmpty() && pointsP1.isEmpty()) {
                                                pointsP1 =
                                                    (pointsGame.toShortCustom() - pointsP3.toShortCustom() - pointsP2.toShortCustom()).toCustomString()
                                            }
                                        }
                                    }

                                }
                            }

                        })
                } else {
                    var shouDialog by remember { mutableStateOf(false) }
                    InsertFloatingActionButton(onClick = {
                        if (viewModel.statusGame.value == GameStatus.EXTENDED || viewModel.statusGame.value == GameStatus.EXTENDED_MANDATORY) {
                            viewModel.checkIsExtended()
                        } else {
                            shouDialog = true
                        }
                    }, modifier = Modifier.align(Alignment.End).padding(16.dp))
                    if (shouDialog) {
                        InsertGameDialogBase(onDismissRequest = {
                            shouDialog = false
                        }, onClick = { winningPoints ->
                            viewModel.resetGame(winningPoints)
                        })
                    }
                }

            }

            is MatchUiState.Error -> {

            }

            MatchUiState.Loading -> {
                CircularProgressIndicator()
            }
        }

    }

}

@Composable
internal fun MatchScreen4(viewModel: ViewModelBase) {
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
                    if (event.winnerName.isNotEmpty()) {
                        winner = event.winnerName
                    }
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
        WinnerDialog(onDismiss = {
            showWinnerDialog = false
        }, onConfirm = {
            showWinnerDialog = false
        }, winner)
    }

    if (showExtended) {
        ExtendedDialog(onDismiss = {
            showExtended = false
        }, onWin = {
            winnerData?.let {
                viewModel.updateStatusScoreName(it)
            }
            showExtended = false
        }, onExtend = { winningPoints ->
            viewModel.extentGame(winningPoints)
            showExtended = false
        }, winnerText = winnerData?.name, maxPoints = maxPoints)
    }

    if (showInfoGameDialog) {
        InfoGameDialog(
            onDismiss = {
                showInfoGameDialog = false
            }, infoGame = InfoGame(
                status = viewModel.statusGame.value,
                winningPoints = viewModel.winningPoints.toString()
            ), onConfirm = {
                showInfoGameDialog = false
                showUpdateStatusGameDialog = true
            }, showFinishMatch = viewModel.statusGame.value == GameStatus.CONTINUE
        )
    }
    if (showUpdateStatusGameDialog && viewModel.statusGame.value == GameStatus.CONTINUE) {
        UpdateStatusGameDialog(onDismiss = {
            showUpdateStatusGameDialog = false
        }, onConfirm = {
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
                        modifier = Modifier.clickable {
                            showInfoGameDialog = true
                        }.weight(1F)
                    )
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
                val pointsListState = rememberLazyListState()
                scope.launch {
                    pointsListState.animateScrollToItem(points.size)
                }

                LazyColumn(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = pointsListState
                ) {

                    itemsIndexed(points) { index: Int, item: Points4PUi ->
                        val isLast = index == points.lastIndex
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isLast && viewModel.statusGame.value == GameStatus.CONTINUE) {
                                GameCard(onDelete = {
                                    scope.launch {
                                        viewModel.deleteLastPoints()
                                    }
                                }) {
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
                                thickness = 4.dp
                            )
                        }
                    }
                }
                if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                    var pointsGame by remember { mutableStateOf("") }
                    var pointsP1 by remember { mutableStateOf("") }
                    var pointsP2 by remember { mutableStateOf("") }
                    var pointsP3 by remember { mutableStateOf("") }
                    var pointsP4 by remember { mutableStateOf("") }
                    var isPressedPoints by remember { mutableStateOf(true) }
                    var isPressedP1 by remember { mutableStateOf(false) }
                    var isPressedP2 by remember { mutableStateOf(false) }
                    var isPressedP3 by remember { mutableStateOf(false) }
                    var isPressedP4 by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    ) {
                        TouchableText(text = pointsGame, isPressed = isPressedPoints, onClick = {

                            isPressedPoints = true
                            isPressedP1 = false
                            isPressedP2 = false
                            isPressedP3 = false
                            isPressedP4 = false

                        })

                        TouchableText(text = pointsP1, isPressed = isPressedP1, onClick = {

                            isPressedPoints = false
                            isPressedP1 = true
                            isPressedP2 = false
                            isPressedP3 = false
                            isPressedP4 = false

                        })
                        TouchableText(text = pointsP2, isPressed = isPressedP2, onClick = {
                            isPressedPoints = false
                            isPressedP1 = false
                            isPressedP2 = true
                            isPressedP3 = false
                            isPressedP4 = false
                        })
                        TouchableText(text = pointsP3, isPressed = isPressedP3, onClick = {
                            isPressedPoints = false
                            isPressedP1 = false
                            isPressedP2 = false
                            isPressedP3 = true
                            isPressedP4 = false
                        })
                        TouchableText(text = pointsP4, isPressed = isPressedP4, onClick = {
                            isPressedPoints = false
                            isPressedP1 = false
                            isPressedP2 = false
                            isPressedP3 = false
                            isPressedP4 = true
                        })
                    }
                    Keyboard(
                        isPressedGames = isPressedPoints,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { inputKey ->
                            if (inputKey == ADD) {
                                viewModel.insertPoints(
                                    Points4PUi(
                                        pointsGame = pointsGame,
                                        pointsP1 = pointsP1,
                                        pointsP2 = pointsP2,
                                        pointsP3 = pointsP3,
                                        pointsP4 = pointsP4
                                    )
                                )
                            } else {
                                if (isPressedPoints) {
                                    manageUserInputKey(
                                        inputText = pointsGame, inputKey = inputKey
                                    ) { text ->
                                        pointsGame = text
                                    }
                                }
                                if (isPressedP1) {
                                    manageUserInputKey(
                                        inputText = pointsP1, inputKey = inputKey
                                    ) { text ->
                                        pointsP1 = text
                                        if (inputKey == MINUS_10) {
                                            if (pointsP2 == MINUS_10) {
                                                pointsP2 = ""
                                                }
                                            if (pointsP3 == MINUS_10) {
                                                pointsP3 = ""
                                            }
                                            if (pointsP4 == MINUS_10) {
                                                pointsP4 = ""
                                            }
                                        } else if(inputKey == BOLT) {
                                                if (pointsP2 == BOLT) {
                                                    pointsP2 = ""
                                                }
                                                if (pointsP3 == BOLT) {
                                                    pointsP3 = ""
                                                }
                                                if (pointsP4 == BOLT) {
                                                    pointsP4 = ""
                                                }
                                        }
                                    }
                                }
                                if (isPressedP2) {
                                    manageUserInputKey(
                                        inputText = pointsP2, inputKey = inputKey
                                    ) { text ->
                                        pointsP2 = text
                                        if (inputKey == MINUS_10) {
                                            if (pointsP1 == MINUS_10) {
                                                pointsP1 = ""
                                            }
                                            if (pointsP3 == MINUS_10) {
                                                pointsP3 = ""
                                            }
                                            if (pointsP4 == MINUS_10) {
                                                pointsP4 = ""
                                            }
                                        } else if(inputKey == BOLT) {
                                            if (pointsP1 == BOLT) {
                                                pointsP1 = ""
                                            }
                                            if (pointsP3 == BOLT) {
                                                pointsP3 = ""
                                            }
                                            if (pointsP4 == BOLT) {
                                                pointsP4 = ""
                                            }
                                        }
                                    }

                                }
                                if (isPressedP3) {
                                    manageUserInputKey(
                                        inputText = pointsP3, inputKey = inputKey
                                    ) { text ->
                                        pointsP3 = text
                                        if (inputKey == MINUS_10) {
                                            if (pointsP1 == MINUS_10) {
                                                pointsP1 = ""
                                            }
                                            if (pointsP2 == MINUS_10) {
                                                pointsP2 = ""
                                            }
                                            if (pointsP4 == MINUS_10) {
                                                pointsP4 = ""
                                            }
                                        } else if(inputKey == BOLT) {
                                            if (pointsP1 == BOLT) {
                                                pointsP1 = ""
                                            }
                                            if (pointsP2 == BOLT) {
                                                pointsP2 = ""
                                            }
                                            if (pointsP4 == BOLT) {
                                                pointsP4 = ""
                                            }
                                        }
                                    }

                                }
                                if (isPressedP4) {
                                    manageUserInputKey(
                                        inputText = pointsP4, inputKey = inputKey
                                    ) { text ->
                                        pointsP4 = text
                                        if (inputKey == MINUS_10) {
                                            if (pointsP1 == MINUS_10) {
                                                pointsP1 = ""
                                            }
                                            if (pointsP2 == MINUS_10) {
                                                pointsP2 = ""
                                            }
                                            if (pointsP3 == MINUS_10) {
                                                pointsP3 = ""
                                            }
                                        } else if(inputKey == BOLT) {
                                            if (pointsP1 == BOLT) {
                                                pointsP1 = ""
                                            }
                                            if (pointsP2 == BOLT) {
                                                pointsP2 = ""
                                            }
                                            if (pointsP3 == BOLT) {
                                                pointsP3 = ""
                                            }
                                        }
                                    }

                                }
                            }

                        })
                } else {
                    var shouDialog by remember { mutableStateOf(false) }
                    InsertFloatingActionButton(onClick = {
                        if (viewModel.statusGame.value == GameStatus.EXTENDED || viewModel.statusGame.value == GameStatus.EXTENDED_MANDATORY) {
                            viewModel.checkIsExtended()
                        } else {
                            shouDialog = true
                        }
                    }, modifier = Modifier.align(Alignment.End).padding(16.dp))
                    if (shouDialog) {
                        InsertGameDialogBase(onDismissRequest = {
                            shouDialog = false
                        }, onClick = { winningPoints ->
                            viewModel.resetGame(winningPoints)
                        })
                    }
                }

            }

            is MatchUiState.Error -> {

            }

            MatchUiState.Loading -> {
                CircularProgressIndicator()
            }
        }

    }

}

@Composable
internal fun MatchScreen2Groups(viewModel: ViewModelBase) {
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
                    if (event.winnerName.isNotEmpty()) {
                        winner = event.winnerName
                    }
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
        WinnerDialog(onDismiss = {
            showWinnerDialog = false
        }, onConfirm = {
            showWinnerDialog = false
        }, winner)
    }

    if (showExtended) {
        ExtendedDialog(onDismiss = {
            showExtended = false
        }, onWin = {
            winnerData?.let {
                viewModel.updateStatusScoreName(it)
            }
            showExtended = false
        }, onExtend = { winningPoints ->
            viewModel.extentGame(winningPoints)
            showExtended = false
        }, winnerText = winnerData?.name, maxPoints = maxPoints)
    }
    MatchWrapper {
        when (val matchState = matchUiState.value) {
            is MatchUiState.Success<*> -> {
                if (showInfoGameDialog) {
                    InfoGameDialog(
                        onDismiss = {
                            showInfoGameDialog = false
                        }, infoGame = InfoGame(
                            status = viewModel.statusGame.value,
                            winningPoints = viewModel.winningPoints.toString()
                        ), onConfirm = {
                            showInfoGameDialog = false
                            showUpdateStatusGameDialog = true
                        }, showFinishMatch = viewModel.statusGame.value == GameStatus.CONTINUE
                    )
                }
                if (showUpdateStatusGameDialog && viewModel.statusGame.value == GameStatus.CONTINUE) {
                    UpdateStatusGameDialog(onDismiss = {
                        showUpdateStatusGameDialog = false
                    }, onConfirm = {
                        viewModel.updateOnlyStatus(GameStatus.FINISHED)
                        showUpdateStatusGameDialog = false
                    })
                }
                val matchData = ((matchState.data) as MatchData2Groups)
                val game = matchData.game
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusImage(
                        gameStatus = GameStatus.fromId(game.statusGame),
                        modifier = Modifier.clickable {
                            showInfoGameDialog = true
                        }.weight(1F)
                    )
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
                var pointsGame by remember { mutableStateOf("") }
                var pointsWe by remember { mutableStateOf("") }
                var pointsYouP by remember { mutableStateOf("") }
                var isPressedPoints by remember { mutableStateOf(true) }
                var isPressedWe by remember { mutableStateOf(false) }
                var isPressedYouP by remember { mutableStateOf(false) }
                val pointsListState = rememberLazyListState()
                scope.launch {
                    pointsListState.animateScrollToItem(points.size)
                }

                LazyColumn(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = pointsListState
                ) {


                    itemsIndexed(points) { index: Int, item: Points2GroupsUi ->
                        val isLast = index == points.lastIndex
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isLast && viewModel.statusGame.value == GameStatus.CONTINUE) {
                                GameCard(onDelete = {
                                    scope.launch {
                                        viewModel.deleteLastPoints()
                                    }
                                }) {
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
                                thickness = 4.dp
                            )
                        }
                    }
                }
                if (viewModel.statusGame.value == GameStatus.CONTINUE) {
                    Row(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    ) {
                        TouchableText(text = pointsGame, isPressed = isPressedPoints, onClick = {

                            isPressedPoints = true
                            isPressedWe = false
                            isPressedYouP = false

                        })

                        TouchableText(text = pointsWe, isPressed = isPressedWe, onClick = {

                            isPressedPoints = false
                            isPressedWe = true
                            isPressedYouP = false

                        })
                        TouchableText(text = pointsYouP, isPressed = isPressedYouP, onClick = {
                            isPressedPoints = false
                            isPressedWe = false
                            isPressedYouP = true
                        })
                    }
                    Keyboard(
                        isPressedGames = isPressedPoints,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { inputKey ->
                            if (inputKey == ADD) {
                                viewModel.insertPoints(
                                    Points2GroupsUi(
                                        pointsP1 = pointsWe,
                                        pointsGame = pointsGame,
                                        pointsP2 = pointsYouP
                                    )
                                )

                            } else {
                                if (isPressedPoints) {

                                    manageUserInputKey(
                                        inputText = pointsGame, inputKey = inputKey
                                    ) { text ->
                                        pointsGame = text
                                    }
                                }
                                if (isPressedWe) {
                                    manageUserInputKey(
                                        inputText = pointsWe, inputKey = inputKey
                                    ) { text ->
                                        pointsWe = text
                                        if (inputKey == MINUS_10 || inputKey == BOLT) {
                                            if (pointsYouP == MINUS_10 || pointsYouP.equals(
                                                    BOLT
                                                )
                                            ) {
                                                pointsYouP = ""

                                            }
                                        }
                                        if (pointsGame.isNotEmpty()) {
                                            pointsYouP =
                                                (pointsGame.toShort() - text.toShortCustomCalculated()).toCustomString()
                                        }
                                    }
                                }
                                if (isPressedYouP) {
                                    manageUserInputKey(
                                        inputText = pointsYouP, inputKey = inputKey
                                    ) { text ->
                                        pointsYouP = text
                                        if (inputKey == MINUS_10 || inputKey == BOLT) {
                                            if (pointsWe == MINUS_10 || pointsWe == BOLT) {
                                                pointsWe = ""
                                            }
                                        }
                                        if (pointsGame.isNotEmpty()) {
                                            pointsWe =
                                                (pointsGame.toShort() - text.toShortCustomCalculated()).toCustomString()
                                        }
                                    }

                                }
                            }

                        })
                } else {
                    var shouDialog by remember { mutableStateOf(false) }
                    InsertFloatingActionButton(onClick = {
                        if (viewModel.statusGame.value == GameStatus.EXTENDED || viewModel.statusGame.value == GameStatus.EXTENDED_MANDATORY) {
                            viewModel.checkIsExtended()
                        } else {
                            shouDialog = true
                        }
                    }, modifier = Modifier.align(Alignment.End).padding(16.dp))
                    if (shouDialog) {
                        InsertGameDialogBase(onDismissRequest = {
                            shouDialog = false
                        }, onClick = { winningPoints ->
                            viewModel.resetGame(winningPoints)
                        })
                    }
                }


            }

            is MatchUiState.Error -> {

            }

            MatchUiState.Loading -> {
                CircularProgressIndicator()
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
    } else if (inputKey == MINUS_10 || inputKey == BOLT) {
        onInputTextChanged(inputKey)

    } else if (inputKey == ZERO && inputText.isEmpty()) {
        onInputTextChanged(inputText)
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
fun ColumnScope.AddIcon(modifier: Modifier = Modifier) {
    val color = if (isSystemInDarkTheme()) Color.White else Color.Black
    Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = "Add Icon",
        modifier = modifier
            .padding(8.dp)
            .size(24.dp)
            .align(Alignment.CenterHorizontally),
        tint = color
    )
}


@Composable
fun ColumnScope.BackspaceIcon(modifier: Modifier = Modifier) {
    val color = if (isSystemInDarkTheme()) Color.White else Color.Black
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Backspace Icon",
        modifier = modifier
            .padding(8.dp)
            .size(24.dp)
            .align(Alignment.CenterHorizontally),
        tint = color
    )
}


@Composable
private fun MatchWrapper(
    modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = modifier.padding(8.dp).wrapContentSize().clip(shape = RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest),
    ) {
        content()
    }

}

@Composable
private fun RowScope.PointsTextAtom(text: String, isBody: Boolean = false, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier.padding(8.dp).weight(1F).align(Alignment.CenterVertically),
        textAlign = TextAlign.Center,
        style = if (isBody) MaterialTheme.typography.bodyMedium
        else MaterialTheme.typography.titleMedium,
    )
}


@Composable
fun RowScope.TouchableText(
    text: String, isPressed: Boolean = false, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    var showIndicator by remember { mutableStateOf(true) }

    if (isPressed && text.isEmpty()) {
        LaunchedEffect(Unit) {
            while (true) {
                showIndicator = !showIndicator
                delay(500) // blink speed
            }
        }
    }

    Box(
        modifier = modifier.weight(1f).clip(RoundedCornerShape(16.dp))
            .background(if (isPressed) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.tertiary)
            .clickable { onClick() }.padding(8.dp).height(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.widthIn(64.dp, 120.dp).heightIn(16.dp, 32.dp)
        ) {
            Text(
                text = text.take(3),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            if (isPressed && text.isEmpty()) {
                WritingPenIcon()
            }
        }
    }
}

@Composable
fun WritingPenIcon(modifier: Modifier = Modifier) {
    val isDark = isSystemInDarkTheme()
    val transition = rememberInfiniteTransition(label = "")
    val offsetX by transition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    Image(
        painter = painterResource(Res.drawable.ic_writting_indicator),
        contentDescription = null,
        modifier = modifier.offset(x = offsetX.dp),
        colorFilter = ColorFilter.tint(if (isDark) Color.White else Color.Black)
    )
}


@Composable
private fun Keyboard(
    isPressedGames: Boolean = false, onClick: (String) -> Unit, modifier: Modifier = Modifier
) {
    val keyAlpha = if (!isPressedGames) 1f else 0f
    Column(modifier = modifier.fillMaxWidth().padding(top = 4.dp)) {
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
        Row(modifier = Modifier.fillMaxWidth()) {
            KeyAtom(text = BOLT, onClick = onClick, modifier = Modifier.graphicsLayer {
                alpha = keyAlpha
            })
            KeyAtom(text = MINUS_10, onClick = onClick, modifier = Modifier.graphicsLayer {
                alpha = keyAlpha
            })
            Card(modifier = modifier.padding(4.dp).weight(1F).padding(4.dp).clickable {
                onClick(ADD)
            }, elevation = CardDefaults.cardElevation(4.dp)) {
                AddIcon()
            }
            Card(modifier = modifier.padding(4.dp).weight(1F).padding(4.dp).clickable {
                onClick(DELETE)

            }, elevation = CardDefaults.cardElevation(4.dp)) {
                BackspaceIcon()
            }

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

@Composable
private fun RowScope.KeyAtom(
    text: String, onClick: (String) -> Unit, modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(4.dp).weight(1F).clickable {
        onClick(text)
    }, elevation = CardDefaults.cardElevation(4.dp)) {
        Text(
            text = text,
            maxLines = 1,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp)
        )
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
                .background(Color.White, shape = RoundedCornerShape(16.dp)).padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(text = statusGameText, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = infoGame.winningPoints, fontSize = 16.sp)
                if (showFinishMatch) {
                    Button(onClick = onConfirm) {
                        Text(text = stringResource(Res.string.alert_dialog_finish_match))
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
                .background(Color.White, shape = RoundedCornerShape(16.dp)).padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = stringResource(Res.string.winner_is),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = winnerText, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)

                Button(onClick = onConfirm) {
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
                .background(Color.White, shape = RoundedCornerShape(16.dp)).padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ShakerTextFieldAtom(
                    value = newWinningPoints,
                    onValueChange = { newText ->
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
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(Res.string.dialog_fragment_extend_match_q),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                } else {
                    Text(
                        text = stringResource(Res.string.dialog_fragment_mandatory_extend_match_info1),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(Res.string.dialog_fragment_mandatory_extend_match_info2),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Row {
                    Button(onClick = {
                        val winningPoints = newWinningPoints.toShortCustom()
                        if (winningPoints > maxPoints.toShort()) {
                            onExtend(winningPoints)
                        } else {
                            shakerWinningPoints.shake()
                            newWinningPoints = ""
                        }
                    }) {
                        Text(text = stringResource(Res.string.dialog_fragment_extend_match))
                    }
                    winnerText?.let {
                        Button(onClick = {
                            onWin()
                        }) {
                            Text(
                                text = stringResource(Res.string.dialog_fragment_win, it),
                                maxLines = 1
                            )
                        }
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
            modifier = modifier.background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.dialog_are_you_sure),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            AnimatedColorCircle()
            Spacer(Modifier.height(16.dp))
            Row {
                Button(onClick = onDismiss) {
                    Text(text = stringResource(Res.string.no))
                }
                Button(onClick = onConfirm) {
                    Text(text = stringResource(Res.string.alert_dialog_finish_match))
                }
            }
        }
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

data class InfoGame(val status: GameStatus, val winningPoints: String)


@Preview
@Composable
private fun MatchScreen2Preview() {
    //MatchScreen2(1)
}