package com.ionvaranita.belotenote.ui.table


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.dialog_fragment_insert_manually_winner_points
import belotenote.composeapp.generated.resources.ic_delete_white
import com.ionvaranita.belotenote.Match2Dest
import com.ionvaranita.belotenote.Match3Dest
import com.ionvaranita.belotenote.Match4Dest
import com.ionvaranita.belotenote.MatchGroupsDest
import com.ionvaranita.belotenote.StatusImage
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.model.toShortCustom
import com.ionvaranita.belotenote.ui.LocalNavHostController
import com.ionvaranita.belotenote.ui.viewmodel.game.Game2GroupsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game2PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game3PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game4PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Games2GroupsUiState
import com.ionvaranita.belotenote.ui.viewmodel.game.Games2PUiState
import com.ionvaranita.belotenote.ui.viewmodel.game.Games3PUiState
import com.ionvaranita.belotenote.ui.viewmodel.game.Games4PUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TablesScreen2(
    viewModel: Game2PViewModel
) {
    val navController = LocalNavHostController.current
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame2(onClick = {
                viewModel.insertGame(game = it)

            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (val state = gamesUiState.value) {
            is Games2PUiState.Success -> {
                scope.launch {
                    gameListState.animateScrollToItem(state.data.size)
                }
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    state = gameListState
                ) {
                    items(state.data) { game ->
                        GameCard(onDelete = {
                            viewModel.deleteGame(game.idGame)
                        }, onTap = {
                            val route = Match2Dest(idGame = game.idGame)
                            navController.navigate(route)
                        }) {
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name1)
                                TableTextAtom(game.name2)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games2PUiState.Error -> { // Handle error
            }
        }
    }
}

@Composable
internal fun TablesScreen3(
    viewModel: Game3PViewModel
) {
    val navController = LocalNavHostController.current
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame3(onClick = {
                viewModel.insertGame(game = it)
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (val state = gamesUiState.value) {
            is Games3PUiState.Success -> {
                scope.launch {
                    gameListState.animateScrollToItem(state.data.size)
                }
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    state = gameListState
                ) {
                    items(state.data) { game ->
                        GameCard(onDelete = {
                            viewModel.deleteGame(game.idGame)
                        }, onTap = {
                            val route = Match3Dest(idGame = game.idGame)
                            navController.navigate(route)
                        }) {
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name1)
                                TableTextAtom(game.name2)
                            }
                            TableTextAtom(game.name3, modifier = Modifier.weight(1F))
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games3PUiState.Error -> { // Handle error
            }
        }
    }
}

@Composable
internal fun TablesScreen4(
    game4PViewModel: Game4PViewModel
) {
    val navController = LocalNavHostController.current
    val gamesUiState = game4PViewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame4(onClick = {
                game4PViewModel.insertGame(game = it)
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (val state = gamesUiState.value) {
            is Games4PUiState.Success -> {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    state = gameListState
                ) {
                    scope.launch {
                        gameListState.animateScrollToItem(state.data.size)
                    }
                    items(state.data) { game ->
                        GameCard(onDelete = {
                            game4PViewModel.deleteGame(game.idGame)
                        }, onTap = {
                            val route = Match4Dest(idGame = game.idGame)
                            navController.navigate(route)
                        }) {
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name1)
                                TableTextAtom(game.name2)
                            }
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name3)
                                TableTextAtom(game.name4)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games4PUiState.Error -> { // Handle error
            }
        }
    }
}

@Composable
internal fun TablesScreenGroups(viewModel: Game2GroupsViewModel) {
    val navController = LocalNavHostController.current
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame2Groups(onClick = {
                viewModel.insertGame(game = it)
                val route = MatchGroupsDest(idGame = it.idGame)
                navController.navigate(route)
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (val state = gamesUiState.value) {
            is Games2GroupsUiState.Success -> {
                scope.launch {
                    gameListState.animateScrollToItem(state.data.size)
                }
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    state = gameListState
                ) {
                    items(state.data) { game ->
                        GameCard(onDelete = {
                            viewModel.deleteGame(game.idGame)
                        }, onTap = {
                            val route = MatchGroupsDest(idGame = game.idGame)
                            navController.navigate(route)
                        }) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = game.name1)
                                Text(text = game.name2)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games2GroupsUiState.Error -> { // Handle error
            }
        }
    }
}

@Composable
fun ConfirmDeleteDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss, title = {
        Text(text = "Confirm Delete")
    }, text = {
        Text(text = "Are you sure you want to delete this game?")
    }, confirmButton = {
        Button(onClick = onConfirm) {
            Text("Yes")
        }
    }, dismissButton = {
        Button(onClick = onDismiss) {
            Text("No")
        }
    })
}

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onTap: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val swipeThreshold = -150f

    Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
        Box(
            modifier = Modifier.matchParentSize().clip(shape = CardDefaults.shape)
                .background(Color.Red).padding(end = 16.dp), contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_delete_white),
                contentDescription = null,
                modifier = modifier.width(44.dp)
            )
        }

        Card(modifier = modifier.offset { IntOffset(offsetX.value.toInt(), 0) }.pointerInput(Unit) {
            detectHorizontalDragGestures(onDragEnd = {
                if (offsetX.value <= swipeThreshold) {
                    showDialog = true
                } else {
                    scope.launch {
                        offsetX.animateTo(0f, animationSpec = tween(300))
                    }
                }
            }) { change, dragAmount ->
                change.consume()
                val newOffset = offsetX.value + dragAmount
                scope.launch {
                    offsetX.snapTo(newOffset.coerceIn(-300f, 0f))
                }
            }
        }.pointerInput(Unit) {
            detectTapGestures(onTap = {
                onTap()
            })
        }) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
            }
        }
    }

    if (showDialog) {
        ConfirmDeleteDialog(onConfirm = {
            onDelete()
            showDialog = false
            scope.launch { offsetX.snapTo(0f) }
        }, onDismiss = {
            showDialog = false
            scope.launch { offsetX.snapTo(0f) }
        })
    }
}

@Composable
private fun TableTextAtom(text: String, modifier: Modifier = Modifier) {
    Text(text = text, style = MaterialTheme.typography.displayLarge, maxLines = 1)
}

@Composable
private fun TablesBase(
    onInsertGameClick: () -> Unit, content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(floatingActionButton = {
        InsertFloatingActionButton(onClick = {
            onInsertGameClick()
        }, modifier = Modifier)
    }, containerColor = Color.Transparent) { paddingValues ->
        content(paddingValues)
    }

}

@Composable
fun InsertFloatingActionButton(onClick: () -> Unit, modifier: Modifier) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

@Composable
fun InsertGame2(
    onDismissRequest: () -> Unit, onClick: (Game2PEntity) -> Unit
) {
    var p1 by remember { mutableStateOf("") }
    var p2 by remember { mutableStateOf("") }
    val shaker1 = remember { TextFieldShaker() }
    val shaker2 = remember { TextFieldShaker() }

    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        when {
            p1.isEmpty() -> shaker1.shake()
            p2.isEmpty() -> shaker2.shake()
            else -> {
                onClick(Game2PEntity(winningPoints = winningPoints, name1 = p1, name2 = p2))
                onDismissRequest()
            }
        }
    }) {
        Row {
            InsertNamesTextFieldAtom(
                value = p1,
                onValueChange = { p1 = it },
                shaker = shaker1,
                modifier = Modifier.weight(1f)
            )
            InsertNamesTextFieldAtom(
                value = p2,
                onValueChange = { p2 = it },
                shaker = shaker2,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

class TextFieldShaker {
    private val _shouldShake = mutableStateOf(false)
    val shouldShake: State<Boolean> get() = _shouldShake

    fun shake() {
        _shouldShake.value = !_shouldShake.value
    }
}

@Composable
private fun InsertNamesTextFieldAtom(
    value: String,
    onValueChange: (String) -> Unit,
    shaker: TextFieldShaker,
    modifier: Modifier = Modifier
) {
    val vibrationOffset = remember { Animatable(0f) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    LaunchedEffect(shaker.shouldShake.value, isFocused.value) {
        if (value.isEmpty() && isFocused.value) {
            repeat(6) {
                vibrationOffset.snapTo(if (it % 2 == 0) 6f else -6f)
                delay(30) // very quick, like vibration
            }
            vibrationOffset.snapTo(0f) // go back to center
        }
    }



    TextField(
        value = value,
        onValueChange = {
            if (it.length <= 6) onValueChange(it)
        },
        modifier = modifier.offset(x = vibrationOffset.value.dp),
        textStyle = MaterialTheme.typography.displaySmall,
        maxLines = 1,
        interactionSource = interactionSource
    )
}

@Composable
fun InsertGame3(
    onDismissRequest: () -> Unit, onClick: (Game3PEntity) -> Unit
) {
    var p1 by remember { mutableStateOf("") }
    var p2 by remember { mutableStateOf("") }
    var p3 by remember { mutableStateOf("") }
    val shouldVibrateP1 by remember { mutableStateOf(TextFieldShaker()) }
    val shouldVibrateP2 by remember { mutableStateOf(TextFieldShaker()) }
    val shouldVibrateP3 by remember { mutableStateOf(TextFieldShaker()) }


    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        if (p1.isEmpty()) {
            shouldVibrateP1.shake()
        } else if (p2.isEmpty()) {
            shouldVibrateP2.shake()
        } else if (p3.isEmpty()) {
            shouldVibrateP3.shake()
        } else {
            onClick(Game3PEntity(name1 = p1, name2 = p2, name3 = p3, winningPoints = winningPoints))
            onDismissRequest()
        }
    }) {
        Row {
            InsertNamesTextFieldAtom(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F), shaker = shouldVibrateP1)
            InsertNamesTextFieldAtom(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F), shaker = shouldVibrateP2)
            InsertNamesTextFieldAtom(value = p3, onValueChange = {
                p3 = it
            }, modifier = Modifier.weight(1F), shaker = shouldVibrateP3)
        }
    }
}

@Composable
fun InsertGame4(
    onDismissRequest: () -> Unit, onClick: (Game4PEntity) -> Unit
) {
    var p1 by remember { mutableStateOf("") }
    var p2 by remember { mutableStateOf("") }
    var p3 by remember { mutableStateOf("") }
    var p4 by remember { mutableStateOf("") }
    val shouldVibrateP1 by remember { mutableStateOf(TextFieldShaker()) }
    val shouldVibrateP2 by remember { mutableStateOf(TextFieldShaker()) }
    val shouldVibrateP3 by remember { mutableStateOf(TextFieldShaker()) }
    val shouldVibrateP4 by remember { mutableStateOf(TextFieldShaker()) }
    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        if (p1.isEmpty()) {
            shouldVibrateP1.shake()
        } else if (p2.isEmpty()) {
            shouldVibrateP2.shake()
        } else if (p3.isEmpty()) {
            shouldVibrateP3.shake()
        } else if (p4.isEmpty()) {
            shouldVibrateP4.shake()
        } else {
            onClick(
                Game4PEntity(
                    name1 = p1,
                    name2 = p2,
                    name3 = p3,
                    name4 = p4,
                    winnerPoints = winningPoints.toShort()
                )
            )
            onDismissRequest()
        }
    }) {
        Row {
            InsertNamesTextFieldAtom(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F), shaker = shouldVibrateP1)
            InsertNamesTextFieldAtom(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F), shaker = shouldVibrateP2)
            InsertNamesTextFieldAtom(value = p3, onValueChange = {
                p3 = it
            }, modifier = Modifier.weight(1F), shaker = shouldVibrateP3)
            InsertNamesTextFieldAtom(value = p4, onValueChange = {
                p4 = it
            }, modifier = Modifier.weight(1F), shaker = shouldVibrateP4)
        }
    }
}

@Composable
fun InsertGame2Groups(
    onDismissRequest: () -> Unit, onClick: (Game2GroupsEntity) -> Unit
) {
    var p1 by remember { mutableStateOf("") }
    var p2 by remember { mutableStateOf("") }
    val shouldVibrateP1 by remember { mutableStateOf(TextFieldShaker()) }
    val shouldVibrateP2 by remember { mutableStateOf(TextFieldShaker()) }
    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        if (p1.isEmpty()) {
            shouldVibrateP1.shake()
        } else if (p2.isEmpty()) {
            shouldVibrateP2.shake()
        } else {
            onClick(Game2GroupsEntity(name1 = p1, name2 = p2, winningPoints = winningPoints))
            onDismissRequest()

        }
    }) {
        Row {
            InsertNamesTextFieldAtom(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F), shaker = shouldVibrateP1)
            InsertNamesTextFieldAtom(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F), shaker = shouldVibrateP2)
        }
    }
}

@Composable
internal fun InsertGameDialogBase(
    onDismissRequest: () -> Unit,
    onClick: (Short) -> Unit,
    content: (@Composable () -> Unit)? = null
) {
    var winningPoints by remember { mutableStateOf("51") }
    var showError by remember { mutableStateOf(false) }
    Dialog(onDismissRequest = { onDismissRequest() }) { // Draw a rectangle shape with rounded corners inside the dialog

        Card(
            modifier = Modifier.fillMaxWidth().height(375.dp).padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content?.invoke()
                var isChecked by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.dialog_fragment_insert_manually_winner_points),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(checked = isChecked, onCheckedChange = { isChecked = it })
                }
                if (!isChecked) {
                    WinningPointsRadioButtons(
                        selectedValue = winningPoints.toShortCustom().toInt(),
                        onValueChange = { winningPoints = it.toString() })
                }

                if (isChecked) {
                    TextField(value = winningPoints, onValueChange = { newText ->
                        if (newText.isEmpty()) {
                            winningPoints = ""
                        } else if (newText.all { it.isDigit() } && !(newText.startsWith("0"))) {
                            winningPoints = newText
                        }
                    })
                }
                Button(onClick = {
                    if (winningPoints.isNotEmpty()) {
                        onClick(winningPoints.toShort())
                    } else {
                        showError = true
                    }
                }) {
                    Text("Insert Game")
                }
                ErrorAlertDialog(showError = showError) {
                    showError = false
                }
            }
        }
    }
}

@Composable
fun WinningPointsRadioButtons(
    selectedValue: Int, onValueChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onValueChange(51) }) {
            RadioButton(
                selected = selectedValue == 51, onClick = { onValueChange(51) })
            Text(text = "51")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onValueChange(101) }) {
            RadioButton(
                selected = selectedValue == 101, onClick = { onValueChange(101) })
            Text(text = "101")
        }
    }
}

@Composable
fun ErrorAlertDialog(showError: Boolean, onDismiss: () -> Unit) {
    if (showError) {
        AlertDialog(onDismissRequest = { onDismiss() }, confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("OK")
            }
        }, icon = {
            Icon(imageVector = Icons.Filled.Notifications, contentDescription = null)
        }, title = {
            Text("Eroare")
        }, text = {
            Text("A apărut o eroare neașteptată. Încearcă din nou.")
        })
    }
}

