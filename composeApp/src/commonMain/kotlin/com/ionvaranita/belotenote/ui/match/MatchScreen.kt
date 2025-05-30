package com.ionvaranita.belotenote.ui.match

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.game
import belotenote.composeapp.generated.resources.ic_delete_white
import com.ionvaranita.belotenote.StatusImage
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.ui.LocalAppDatabase
import com.ionvaranita.belotenote.ui.LocalNavHostController
import com.ionvaranita.belotenote.ui.table.GameCard
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2GroupsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2PPViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2PUiState
import com.ionvaranita.belotenote.ui.viewmodel.match.MatchGroupsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MatchScreen2(idGame: Int) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current
    val viewModel = viewModel { Match2PPViewModel(appDatabase) }
    val matchUiState = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getMatchData(idGame)
    }
    MatchWrapper {
        when (val matchState = matchUiState.value) {
            is Match2PUiState.Success -> {
                val game = matchState.data.game
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    StatusImage(
                        gameStatus = GameStatus.fromId(game.statusGame),
                        modifier = Modifier.padding(8.dp)
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

            is Match2PUiState.Error -> {

            }

            Match2PUiState.Loading -> {
                CircularProgressIndicator()
            }
        }

    }
}

@Composable
internal fun MatchScreen2Groups(idGame: Int) {
    val navController = LocalNavHostController.current
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
                var isPressedByDefault1 by remember { mutableStateOf(true) }
                var isPressedByDefault2 by remember { mutableStateOf(false) }
                var isPressedByDefault3 by remember { mutableStateOf(false) }
                LazyColumn(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(points) {index: Int, item: Points2GroupsUi ->
                        val isLast = index == points.lastIndex
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isLast) {
                                GameCard(
                                    onDelete = {
                                        scope.launch {
                                            viewModel.deleteLastPoints()
                                        }
                                    }
                                ) {
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
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                ) {
                    TouchableText(text = pointsGame, isPressed = isPressedByDefault1, onClick = {

                        isPressedByDefault1 = true
                        isPressedByDefault2 = false
                        isPressedByDefault3 = false

                    })

                    TouchableText(text = pointsWe, isPressed = isPressedByDefault2, onClick = {

                        isPressedByDefault1 = false
                        isPressedByDefault2 = true
                        isPressedByDefault3 = false

                    })
                    TouchableText(text = pointsYouP, isPressed = isPressedByDefault3, onClick = {
                        isPressedByDefault1 = false
                        isPressedByDefault2 = false
                        isPressedByDefault3 = true
                    })
                }
                Keyboard(
                    isPresedGames = isPressedByDefault1,
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
                            if (isPressedByDefault1) {

                                manageUserInputKey(
                                    inputText = pointsGame,
                                    inputKey = inputKey
                                ) { text ->
                                    pointsGame = text
                                }
                            }
                            if (isPressedByDefault2) {
                                manageUserInputKey(
                                    inputText = pointsWe,
                                    inputKey = inputKey
                                ) { text ->
                                    pointsWe = text
                                    if (inputKey.equals(MINUS_10) || inputKey.equals(BOLT)) {
                                        if (pointsYouP.equals(MINUS_10) || pointsYouP.equals(BOLT)) {
                                            pointsYouP = ""
                                        }
                                    }
                                }
                            }
                            if (isPressedByDefault3) {
                                manageUserInputKey(
                                    inputText = pointsYouP,
                                    inputKey = inputKey
                                ) { text ->
                                    pointsYouP = text
                                    if (inputKey.equals(MINUS_10) || inputKey.equals(BOLT)) {
                                        if (pointsWe.equals(MINUS_10) || pointsWe.equals(BOLT)) {
                                            pointsWe = ""
                                        }
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

private fun manageUserInputKey(
    inputText: String,
    inputKey: String,
    onInputTextChanged: (String) -> Unit
) {
    if (inputKey.equals(DELETE)) {
        onInputTextChanged(inputText.dropLast(1))
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
fun RowScope.AddIcon(modifier: Modifier = Modifier, tint: Color = Color.Black) {
    Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = "Add Icon",
        modifier = modifier.size(24.dp).align(Alignment.CenterVertically),
        tint = tint
    )
}

@Composable
fun RowScope.BackspaceIcon(modifier: Modifier = Modifier, tint: Color = Color.Black) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Backspace Icon",
        modifier = modifier.size(24.dp).align(Alignment.CenterVertically),
        tint = tint
    )
}

@Composable
private fun MatchWrapper(
    modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = modifier.padding(16.dp).wrapContentSize().clip(shape = RoundedCornerShape(16.dp))
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

    Text(
        text = text.take(3),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.weight(1F).clip(shape = RoundedCornerShape(16.dp))
            .background(if (isPressed) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.tertiary)
            .padding(16.dp).clickable {
                onClick()
            },
        textAlign = TextAlign.Center
    )
}


@Composable
private fun Keyboard(
    isPresedGames: Boolean = false,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyAlpha = if (!isPresedGames) 1f else 0f
    Column(modifier = modifier.fillMaxWidth()) {
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
            AddIcon(
                modifier = Modifier.weight(1F).clickable {
                    onClick(ADD)
                })
            BackspaceIcon(
                modifier = Modifier.weight(1F).clickable {
                    onClick(DELETE)
                })
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
    Text(text = text, maxLines = 1, modifier = modifier.padding(16.dp).weight(1F).clickable {
        onClick(text)
    }, textAlign = TextAlign.Center, style = MaterialTheme.typography.displayLarge)
}

@Composable
internal fun MatchScreen3(idGame: Int) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current

}

@Composable
internal fun MatchScreen4(idGame: Int) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current

}


@Preview
@Composable
private fun MatchScreen2Preview() {
    MatchScreen2(1)
}