package com.ionvaranita.belotenote.ui.match

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.game
import com.ionvaranita.belotenote.StatusImage
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.ui.LocalAppDatabase
import com.ionvaranita.belotenote.ui.LocalNavHostController
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2GroupsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2PPViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2PUiState
import com.ionvaranita.belotenote.ui.viewmodel.match.MatchGroupsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
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
                    items(points) { item: Points2GroupsUi ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            PointsTextAtom(text = item.pointsGame.toString())
                            PointsTextAtom(text = item.pointsWe.toString())
                            PointsTextAtom(text = item.pointsYouP.toString())
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
                Keyboard(modifier = Modifier.fillMaxWidth(), onClick = { text ->
                    if (text.equals(ADD)) {
                        scope.launch(Dispatchers.IO) {
                            viewModel.insertPoints(
                                Points2GroupsEntity(
                                    idGame = idGame,
                                    pointsWe = pointsWe.toShort(),
                                    pointsGame = pointsGame.toShort(),
                                    pointsYouP = pointsYouP.toShort()
                                )
                            )
                        }
                    } else {
                        if (isPressedByDefault1) {
                            if (text.equals(DELETE)) {
                                pointsGame = pointsGame.dropLast(1)
                            } else if (text.equals(MINUS_10) || text.equals(BOLT)) {

                            } else {
                                pointsGame += text
                            }
                        }
                        if (isPressedByDefault2) {
                            if (text.equals(DELETE)) {
                                pointsWe = pointsWe.dropLast(1)
                            } else if (text.equals(MINUS_10) || text.equals(BOLT)) {
                                pointsWe = text
                            } else {
                                pointsWe += text
                            }
                        }
                        if (isPressedByDefault3) {
                            if (text.equals(DELETE)) {
                                pointsYouP = pointsYouP.dropLast(1)
                            } else if (text.equals(MINUS_10) || text.equals(BOLT)) {
                                pointsYouP = text
                            } else {
                                pointsYouP += text
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
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.weight(1F).clip(shape = RoundedCornerShape(16.dp)).background(if (isPressed) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.tertiary).padding(16.dp)
            .clickable {
                onClick()
            }, textAlign = TextAlign.Center)
}


@Composable
private fun Keyboard(onClick: (String) -> Unit, modifier: Modifier = Modifier) {
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
            KeyAtom(text = BOLT, onClick = onClick)
            KeyAtom(text = MINUS_10, onClick = onClick)
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
private const val ZERO = "9"
private const val BOLT = "B"
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