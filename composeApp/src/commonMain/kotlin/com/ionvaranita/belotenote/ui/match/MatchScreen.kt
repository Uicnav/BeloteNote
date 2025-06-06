package com.ionvaranita.belotenote.ui.match

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ContextualFlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.alert_dialog_finish_match
import belotenote.composeapp.generated.resources.alert_dialog_finished
import belotenote.composeapp.generated.resources.alert_dialog_playing
import belotenote.composeapp.generated.resources.alert_dialog_to_extend
import belotenote.composeapp.generated.resources.game
import belotenote.composeapp.generated.resources.ic_writting_indicator
import com.ionvaranita.belotenote.StatusImage
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.model.Points2PUi
import com.ionvaranita.belotenote.domain.model.Points3PUi
import com.ionvaranita.belotenote.domain.model.toShortCustom
import com.ionvaranita.belotenote.ui.LocalAppDatabase
import com.ionvaranita.belotenote.ui.LocalNavHostController
import com.ionvaranita.belotenote.ui.table.GameCard
import com.ionvaranita.belotenote.ui.table.InsertFloatingActionButton
import com.ionvaranita.belotenote.ui.table.InsertGameDialogBase
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2GroupsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2PPViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2PUiState
import com.ionvaranita.belotenote.ui.viewmodel.match.Match3PPViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match3PUiState
import com.ionvaranita.belotenote.ui.viewmodel.match.MatchGroupsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MatchScreen2(idGame: Int) {
    val appDatabase = LocalAppDatabase.current
    val viewModel = viewModel { Match2PPViewModel(appDatabase, idGame) }
    val matchUiState = viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var showInfoGameDialog by remember { mutableStateOf(false) }
    MatchWrapper {
        when (val matchState = matchUiState.value) {
            is Match2PUiState.Success -> {
                val game = matchState.data.game
                Row {
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StatusImage(
                            gameStatus = GameStatus.fromId(game.statusGame),
                            modifier = Modifier.clickable {
                                showInfoGameDialog = true
                            }
                        )
                        TableTextAtom(text = stringResource(Res.string.game))
                    }
                    if (showInfoGameDialog) {
                        InfoGameDialog(onDismiss = {
                            showInfoGameDialog = false }, infoGame = InfoGame(status = viewModel.statusGame.value, winningPoints =game.winningPoints.toString()), onConfirm = {
                                viewModel.updateOnlyStatus(GameStatus.FINISHED)
                            showInfoGameDialog =false
                        })
                    }
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TableTextAtom(text = game.scoreName1.toString())
                        TableTextAtom(text = game.name1)
                    }

                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TableTextAtom(text = game.scoreName2.toString())
                        TableTextAtom(text = game.name2)
                    }
                }


                val points = matchState.data.points
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
                                }) {
                                    PointsTextAtom(text = item.pointsGame)
                                    PointsTextAtom(text = item.pointsMe)
                                    PointsTextAtom(text = item.pointsYouS)
                                }
                            } else {
                                PointsTextAtom(text = item.pointsGame)
                                PointsTextAtom(text = item.pointsMe)
                                PointsTextAtom(text = item.pointsYouS)
                            }
                        }
                        if (index % 2 == 1) {
                            HorizontalDivider(
                                thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp)
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
                        isPresedGames = isPressedPoints,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { inputKey ->
                            if (inputKey.equals(ADD)) {
                                scope.launch(Dispatchers.IO) {
                                    viewModel.insertPoints(
                                        Points2PUi(
                                            idGame = idGame,
                                            pointsMe = pointsMe,
                                            pointsGame = pointsGame,
                                            pointsYouS = pointsYouS
                                        )
                                    )
                                }
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
                                        if (inputKey.equals(MINUS_10) || inputKey.equals(BOLT)) {
                                            if (pointsYouS.equals(MINUS_10) || pointsYouS.equals(
                                                    BOLT
                                                )
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
                        shouDialog = true
                    }, modifier = Modifier.align(Alignment.End).padding(16.dp))
                    if (shouDialog) {
                        InsertGameDialogBase(onDismissRequest = {
                            shouDialog = false
                        }, onClick = { winningPoints ->
                            viewModel.resetGame(winningPoints)
                        }, appDatabase = appDatabase)
                    }

                }


            }

            is Match2PUiState.Error -> {

            }

            Match2PUiState.Loading -> {
                CircularProgressIndicator()
            }
        }

    }
}

@Composable
internal fun MatchScreen3(idGame: Int) {

    val appDatabase = LocalAppDatabase.current
    val viewModel = viewModel { Match3PPViewModel(appDatabase, idGame) }
    val matchUiState = viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    MatchWrapper {
        when (val matchState = matchUiState.value) {
            is Match3PUiState.Success -> {
                val game = matchState.data.game
                Row {
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StatusImage(
                            gameStatus = GameStatus.fromId(game.statusGame),
                        )
                        TableTextAtom(text = stringResource(Res.string.game))
                    }
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TableTextAtom(text = game.scoreName1.toString())
                        TableTextAtom(text = game.name1)
                    }

                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TableTextAtom(text = game.scoreName2.toString())
                        TableTextAtom(text = game.name2)
                    }
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TableTextAtom(text = game.scoreName3.toString())
                        TableTextAtom(text = game.name3)
                    }
                }


                val points = matchState.data.points
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
                            if (isLast) {
                                GameCard(onDelete = {
                                    scope.launch {
                                        viewModel.deleteLastPoints()
                                    }
                                }) {
                                    PointsTextAtom(text = item.pointsGame)
                                    PointsTextAtom(text = item.pointsP1)
                                    PointsTextAtom(text = item.pointsP2)
                                    PointsTextAtom(text = item.pointsP3)
                                }
                            } else {
                                PointsTextAtom(text = item.pointsGame)
                                PointsTextAtom(text = item.pointsP1)
                                PointsTextAtom(text = item.pointsP2)
                                PointsTextAtom(text = item.pointsP3)
                            }
                        }
                        if (index % 3 == 2) {
                            HorizontalDivider(
                                thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
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
                    isPresedGames = isPressedPoints,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { inputKey ->
                        if (inputKey.equals(ADD)) {
                            scope.launch(Dispatchers.IO) {
                                viewModel.insertPoints(
                                    Points3PUi(
                                        idGame = idGame,
                                        pointsP1 = pointsP1,
                                        pointsGame = pointsGame,
                                        pointsP2 = pointsP2,
                                        pointsP3 = pointsP3
                                    )
                                )
                            }
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
                                    if (inputKey.equals(MINUS_10) || inputKey.equals(BOLT)) {
                                        if (pointsP2.equals(MINUS_10) || pointsP2.equals(BOLT)) {
                                            pointsP2 = ""
                                        }
                                        if (pointsP3.equals(MINUS_10) || pointsP3.equals(BOLT)) {
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
                                    if (inputKey.equals(MINUS_10) || inputKey.equals(BOLT)) {
                                        if (pointsP1.equals(MINUS_10) || pointsP1.equals(BOLT)) {
                                            pointsP1 = ""
                                        }
                                        if (pointsP2.equals(MINUS_10) || pointsP2.equals(BOLT)) {
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
            }

            is Match3PUiState.Error -> {

            }

            Match3PUiState.Loading -> {
                CircularProgressIndicator()
            }
        }

    }

}

@Composable
internal fun MatchScreen4(idGame: Int) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current

}

@Composable
internal fun MatchScreen2Groups(idGame: Int) {
    val appDatabase = LocalAppDatabase.current
    val viewModel = viewModel { Match2GroupsViewModel(appDatabase, idGame) }
    val matchUiState = viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    MatchWrapper {
        when (val matchState = matchUiState.value) {
            is MatchGroupsUiState.Success -> {
                Row(modifier = Modifier) {
                    val game = matchState.data.game
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StatusImage(
                            gameStatus = GameStatus.fromId(game.statusGame)
                        )
                        TableTextAtom(text = stringResource(Res.string.game))
                    }
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TableTextAtom(text = game.scoreName1.toString())
                        TableTextAtom(text = game.name1)
                    }

                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TableTextAtom(text = game.scoreName2.toString())
                        TableTextAtom(text = game.name2)
                    }

                }
                val points = matchState.data.points
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
                            if (isLast) {
                                GameCard(onDelete = {
                                    scope.launch {
                                        viewModel.deleteLastPoints()
                                    }
                                }) {
                                    PointsTextAtom(text = item.pointsGame)
                                    PointsTextAtom(text = item.pointsWe)
                                    PointsTextAtom(text = item.pointsYouP)
                                }
                            } else {
                                PointsTextAtom(text = item.pointsGame)
                                PointsTextAtom(text = item.pointsWe)
                                PointsTextAtom(text = item.pointsYouP)
                            }
                        }
                        if (index % 2 == 1) {
                            HorizontalDivider(
                                thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
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
                    isPresedGames = isPressedPoints,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { inputKey ->
                        if (inputKey.equals(ADD)) {
                            scope.launch(Dispatchers.IO) {
                                viewModel.insertPoints(
                                    Points2GroupsUi(
                                        idGame = idGame,
                                        pointsWe = pointsWe,
                                        pointsGame = pointsGame,
                                        pointsYouP = pointsYouP
                                    )
                                )
                            }
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
                                    if (inputKey.equals(MINUS_10) || inputKey.equals(BOLT)) {
                                        if (pointsYouP.equals(MINUS_10) || pointsYouP.equals(BOLT)) {
                                            pointsYouP = ""

                                        }
                                    }
                                    if (pointsGame.isNotEmpty()) {
                                        pointsYouP =
                                            (pointsGame.toShortCustom() - text.toShortCustom()).toCustomString()
                                    }
                                }
                            }
                            if (isPressedYouP) {
                                manageUserInputKey(
                                    inputText = pointsYouP, inputKey = inputKey
                                ) { text ->
                                    pointsYouP = text
                                    if (inputKey.equals(MINUS_10) || inputKey.equals(BOLT)) {
                                        if (pointsWe.equals(MINUS_10) || pointsWe.equals(BOLT)) {
                                            pointsWe = ""
                                        }
                                    }
                                    if (pointsGame.isNotEmpty()) {
                                        pointsWe =
                                            (pointsGame.toShortCustom() - text.toShortCustom()).toCustomString()
                                    }
                                }

                            }
                        }

                    })

            }

            is MatchGroupsUiState.Error -> {

            }

            MatchGroupsUiState.Loading -> {
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
    if (inputKey.equals(DELETE)) {
        if (inputText.equals(MINUS_10)) {
            onInputTextChanged("")

        } else {
            onInputTextChanged(inputText.dropLast(1))
        }
    } else if (inputKey.equals(MINUS_10) || inputKey.equals(BOLT)) {
        onInputTextChanged(inputKey)

    } else if (inputKey.equals(ZERO) && inputText.isEmpty()) {
        onInputTextChanged(inputText)
    } else {
        if (!inputText.equals(BOLT) && !inputText.equals(MINUS_10) && inputText.length < 3) onInputTextChanged(
            inputText + inputKey
        )
    }
}

@Composable
fun ColumnScope.AddIcon(modifier: Modifier = Modifier, tint: Color = Color.Black) {
    Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = "Add Icon",
        modifier = modifier.padding(8.dp).size(24.dp).align(Alignment.CenterHorizontally),
        tint = tint
    )
}

@Composable
fun ColumnScope.BackspaceIcon(modifier: Modifier = Modifier, tint: Color = Color.Black) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Backspace Icon",
        modifier = modifier.padding(8.dp).size(24.dp).align(Alignment.CenterHorizontally),
        tint = tint
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
private fun ColumnScope.TableTextAtom(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier.padding(16.dp).align(Alignment.CenterHorizontally))
}

@Composable
private fun RowScope.PointsTextAtom(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier.padding(8.dp).weight(1F), textAlign = TextAlign.Center)
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
    val transition = rememberInfiniteTransition(label = "pen_move")
    val offsetX by transition.animateFloat(
        initialValue = 0f, targetValue = 10f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "pen_offset"
    )

    Image(
        painter = painterResource(Res.drawable.ic_writting_indicator),
        contentDescription = "Writing Pen",
        modifier = modifier.offset(x = offsetX.dp)
    )
}


@Composable
fun IndicatorIcon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(12.dp).background(Color.Red, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(4.dp).background(Color.White, shape = CircleShape)
        )
    }
}


@Composable
private fun Keyboard(
    isPresedGames: Boolean = false, onClick: (String) -> Unit, modifier: Modifier = Modifier
) {
    val keyAlpha = if (!isPresedGames) 1f else 0f
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
private const val MINUS_10 = "-10"
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
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    infoGame: InfoGame,
) {
    val statusGameText = when(infoGame.status) {
        GameStatus.CONTINUE -> {
            stringResource(Res.string.alert_dialog_playing)
        }
        GameStatus.FINISHED -> {
            stringResource(Res.string.alert_dialog_finished)

        }
        GameStatus.EXTENDED,GameStatus.EXTENDED_MANDATORY -> {
            stringResource(Res.string.alert_dialog_to_extend)
        }

    }
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(text = statusGameText, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = infoGame.winningPoints, fontSize = 16.sp)
                Button(onClick = onConfirm) {
                    Text(text = stringResource(Res.string.alert_dialog_finish_match))
                }
            }
        }
    }
}

data class InfoGame(val status: GameStatus, val winningPoints: String)



@Preview
@Composable
private fun MatchScreen2Preview() {
    MatchScreen2(1)
}