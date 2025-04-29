package com.ionvaranita.belotenote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.alert_dialog_winner_points
import belotenote.composeapp.generated.resources.dialog_fragment_insert_manually_winner_points
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.domain.model.Game4PUi
import com.ionvaranita.belotenote.domain.model.WinningPointsUi
import com.ionvaranita.belotenote.viewmodel.Game2GroupsViewModel
import com.ionvaranita.belotenote.viewmodel.Game2PViewModel
import com.ionvaranita.belotenote.viewmodel.Game3PViewModel
import com.ionvaranita.belotenote.viewmodel.Game4PViewModel
import com.ionvaranita.belotenote.viewmodel.Games2GroupsUiState
import com.ionvaranita.belotenote.viewmodel.Games2PUiState
import com.ionvaranita.belotenote.viewmodel.Games3PUiState
import com.ionvaranita.belotenote.viewmodel.Games4PUiState
import com.ionvaranita.belotenote.viewmodel.WinningPointsState
import com.ionvaranita.belotenote.viewmodel.WinningPointsViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun TableScreen(appDatabase: AppDatabase, gamePath: GamePath) {
    when (gamePath) {
        GamePath.TWO -> Tables2P(appDatabase)
        GamePath.THREE -> Tables3P(appDatabase)
        GamePath.FOUR -> Tables4P(appDatabase)
        GamePath.GROUP -> Tables2Groups(appDatabase)
    }

}

@Composable
private fun Tables2P(appDatabase: AppDatabase) {
    val viewModel = viewModel { Game2PViewModel(appDatabase) }
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }

    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame2(appDatabase = appDatabase, onClick = {
                viewModel.insertGame(game2PUi = it)
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (val state = gamesUiState.value) {
            is Games2PUiState.Success -> {
                LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items(state.data) {game->
                        Card(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1F)) {
                                    TableTextAtom(game.name1)
                                    TableTextAtom(game.name2)
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                StatusImage(GameStatus.fromId(game.statusGame))
                            }
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
private fun TableTextAtom(text: String, modifier: Modifier = Modifier) {
    Text(text = text, style = MaterialTheme.typography.displayLarge, maxLines = 1)
}

@Composable
private fun Tables3P(appDatabase: AppDatabase) {
    val viewModel = viewModel { Game3PViewModel(appDatabase) }
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }

    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame3(appDatabase = appDatabase, onClick = {
                viewModel.insertGame(game = it)
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (gamesUiState.value) {
            is Games3PUiState.Success -> {
                LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items((gamesUiState.value as Games3PUiState.Success).data) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom("${it.name1}")
                                TableTextAtom("${it.name2}")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            TableTextAtom("${it.name2}")
                            Spacer(modifier = Modifier.width(16.dp))
                            TableTextAtom("${it.statusGame}")
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
private fun Tables4P(appDatabase: AppDatabase) {
    val viewModel = viewModel { Game4PViewModel(appDatabase) }
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }

    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame4(appDatabase = appDatabase, onClick = {
                viewModel.insertGame(game = it)
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (gamesUiState.value) {
            is Games4PUiState.Success -> {
                LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items((gamesUiState.value as Games4PUiState.Success).data) {
                        Row {
                            Text("${it.name1}")
                            Text("${it.name2}")
                            Text("${it.name3}")
                            Text("${it.name4}")
                            Text("${it.statusGame}")
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
private fun Tables2Groups(appDatabase: AppDatabase) {
    val viewModel = viewModel { Game2GroupsViewModel(appDatabase) }
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }

    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame2Groups(appDatabase = appDatabase, onClick = {
                viewModel.insertGame(game = it)
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (val state = gamesUiState.value) {
            is Games2GroupsUiState.Success -> {
                LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items(state.data) {
                        Row {
                            Text("${it.name1}")
                            Text("${it.name2}")
                            Text("${it.statusGame}")
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
private fun TablesBase(onInsertGameClick: () -> Unit, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(floatingActionButton = {
        InsertGameFloatingActionButton(onClick = {
            onInsertGameClick()
        }, modifier = Modifier)
    }, containerColor = Color.Transparent) { paddingValues ->
        content(paddingValues)
    }

}

@Composable
fun InsertGameFloatingActionButton(onClick: () -> Unit, modifier: Modifier) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
                        ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

@Composable
fun InsertGame2(appDatabase: AppDatabase, onDismissRequest: () -> Unit, onClick: (Game2PUi) -> Unit) {
    var p1 by remember { mutableStateOf("") }
    var p2 by remember { mutableStateOf("") }
    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        onClick(Game2PUi(winningPoints = winningPoints, name1 = p1, name2 = p2))
    }, appDatabase = appDatabase) {
        Row {
            TextField(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F))
            TextField(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F))
        }
    }
}

@Composable
private fun InsertNames(value: String,
    onValueChange: (String) -> Unit,) {
    TextField(value = value, onValueChange = onValueChange, textStyle = MaterialTheme.typography.displaySmall)
}

@Composable
fun InsertGame3(appDatabase: AppDatabase, onDismissRequest: () -> Unit, onClick: (Game3PUi) -> Unit) {
    var p1 by remember { mutableStateOf("") }
    var p2 by remember { mutableStateOf("") }
    var p3 by remember { mutableStateOf("") }
    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        onClick(Game3PUi(name1 = p1, name2 = p2, name3 = p3, winnerPoints = winningPoints))
    }, appDatabase = appDatabase) {
        Row {
            TextField(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F))
            TextField(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F))
            TextField(value = p3, onValueChange = {
                p3 = it
            }, modifier = Modifier.weight(1F))
        }
    }
}

@Composable
fun InsertGame4(appDatabase: AppDatabase, onDismissRequest: () -> Unit, onClick: (Game4PUi) -> Unit) {
    var p1 by remember { mutableStateOf("") }
    var p2 by remember { mutableStateOf("") }
    var p3 by remember { mutableStateOf("") }
    var p4 by remember { mutableStateOf("") }
    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        onClick(Game4PUi(name1 = p1, name2 = p2, name3 = p3, name4 = p4, winnerPoints = winningPoints.toShort()))
    }, appDatabase = appDatabase) {
        Row {
            TextField(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F))
            TextField(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F))
            TextField(value = p3, onValueChange = {
                p3 = it
            }, modifier = Modifier.weight(1F))
            TextField(value = p4, onValueChange = {
                p4 = it
            }, modifier = Modifier.weight(1F))
        }
    }
}

@Composable
fun InsertGame2Groups(appDatabase: AppDatabase, onDismissRequest: () -> Unit, onClick: (Game2GroupsUi) -> Unit) {
    var p1 by remember { mutableStateOf("") }
    var p2 by remember { mutableStateOf("") }
    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        onClick(Game2GroupsUi(name1 = p1, name2 = p2, winnerPoints = winningPoints))
    }, appDatabase = appDatabase) {
        Row {
            TextField(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F))
            TextField(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F))
        }
    }
}

@Composable
private fun InsertGameDialogBase(onDismissRequest: () -> Unit, onClick: (Short) -> Unit, appDatabase: AppDatabase, content: @Composable () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) { // Draw a rectangle shape with rounded corners inside the dialog
        var winningPoints by remember { mutableStateOf("") }
        val viewModel = viewModel { WinningPointsViewModel(appDatabase) }
        var showError by remember { mutableStateOf(false) }
        Card(
            modifier = Modifier.fillMaxWidth().height(375.dp).padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                  ) {
                content()
                var isChecked by remember { mutableStateOf(false) }

                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(Res.string.dialog_fragment_insert_manually_winner_points), style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(checked = isChecked, onCheckedChange = { isChecked = it })
                }
                val uiState = viewModel.uiState.collectAsState()
                when (val state = uiState.value) {
                    is WinningPointsState.Success -> {
                        if (!isChecked) {
                            winningPoints = state.data[0].winningPoints.toString()
                            WinningPointsSpinner(values = state.data, selectedValue = state.data[0], onValueSelected = {
                                winningPoints = it.winningPoints.toString()
                            })
                        }

                    }
                    is WinningPointsState.Error -> {

                    }
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
                        onDismissRequest()
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

@Composable
fun WinningPointsSpinner(values: List<WinningPointsUi>, selectedValue: WinningPointsUi, onValueSelected: (WinningPointsUi) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedValue.winningPoints.toString()) }

    Column(modifier = Modifier.fillMaxWidth().clickable { expanded = true }) {
        OutlinedTextField(value = selectedText, onValueChange = {
            println("Value change $it")
        }, readOnly = true, label = { Text(stringResource(Res.string.alert_dialog_winner_points), style = MaterialTheme.typography.labelLarge) })
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            values.forEach { value ->
                DropdownMenuItem(text = { Text(value.winningPoints.toString()) }, onClick = {
                    selectedText = value.winningPoints.toString()
                    onValueSelected(value)
                    expanded = false
                })
            }
        }
    }
}

