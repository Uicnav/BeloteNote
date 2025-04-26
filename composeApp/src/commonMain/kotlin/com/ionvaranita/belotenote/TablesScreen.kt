package com.ionvaranita.belotenote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.domain.model.Game4PUi
import com.ionvaranita.belotenote.viewmodel.Game2GroupsViewModel
import com.ionvaranita.belotenote.viewmodel.Game2PViewModel
import com.ionvaranita.belotenote.viewmodel.Game3PViewModel
import com.ionvaranita.belotenote.viewmodel.Game4PViewModel
import com.ionvaranita.belotenote.viewmodel.Games2GroupsUiState
import com.ionvaranita.belotenote.viewmodel.Games2PUiState
import com.ionvaranita.belotenote.viewmodel.Games3PUiState
import com.ionvaranita.belotenote.viewmodel.Games4PUiState

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
            InsertGame2(onClick = {
                viewModel.insertGame(game2PUi = it)
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (gamesUiState.value) {
            is Games2PUiState.Success -> {
                LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items((gamesUiState.value as Games2PUiState.Success).data) {
                        Row {
                            Text("${it.name1}")
                            Text("${it.name2}")
                            Text("${it.statusGame}")
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
private fun Tables3P(appDatabase: AppDatabase) {
    val viewModel = viewModel { Game3PViewModel(appDatabase) }
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }

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

        when (gamesUiState.value) {
            is Games3PUiState.Success -> {
                LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items((gamesUiState.value as Games3PUiState.Success).data) {
                        Row {
                            Text("${it.name1}")
                            Text("${it.name2}")
                            Text("${it.name3}")
                            Text("${it.statusGame}")
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
            InsertGame4(onClick = {
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
            InsertGame2Groups(onClick = {
                viewModel.insertGame(game = it)
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (gamesUiState.value) {
            is Games2GroupsUiState.Success -> {
                LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items((gamesUiState.value as Games2GroupsUiState.Success).data) {
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
    }) { paddingValues ->
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
fun InsertGame2(onDismissRequest: () -> Unit, onClick: (Game2PUi) -> Unit) {
    InsertGameDialogBase(onDismissRequest = onDismissRequest) {
        var p1 by remember { mutableStateOf("") }
        var p2 by remember { mutableStateOf("") }
        var winningPoints by remember { mutableStateOf("") }
        Row {
            TextField(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F))
            TextField(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F))
        }
        TextField(value = winningPoints, onValueChange = {
            winningPoints = it
        })
        Button(onClick = {
            onClick(Game2PUi(name1 = p1, name2 = p2, winnerPoints = winningPoints.toShort()))
            onDismissRequest()
        }) {
            Text("Insert Game")
        }
    }
}

@Composable
fun InsertGame3(onDismissRequest: () -> Unit, onClick: (Game3PUi) -> Unit) {
    InsertGameDialogBase(onDismissRequest = onDismissRequest) {
        var p1 by remember { mutableStateOf("") }
        var p2 by remember { mutableStateOf("") }
        var p3 by remember { mutableStateOf("") }
        var winningPoints by remember { mutableStateOf("") }
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
        TextField(value = winningPoints, onValueChange = {
            winningPoints = it
        })
        Button(onClick = {
            onClick(Game3PUi(name1 = p1, name2 = p2, name3 = p3, winnerPoints = winningPoints.toShort()))
            onDismissRequest()
        }) {
            Text("Insert Game")
        }
    }
}

@Composable
fun InsertGame4(onDismissRequest: () -> Unit, onClick: (Game4PUi) -> Unit) {
    InsertGameDialogBase(onDismissRequest = onDismissRequest) {
        var p1 by remember { mutableStateOf("") }
        var p2 by remember { mutableStateOf("") }
        var p3 by remember { mutableStateOf("") }
        var p4 by remember { mutableStateOf("") }
        var winningPoints by remember { mutableStateOf("") }
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
        TextField(value = winningPoints, onValueChange = {
            winningPoints = it
        })
        Button(onClick = {
            onClick(Game4PUi(name1 = p1, name2 = p2, name3 = p3, name4 = p4, winnerPoints = winningPoints.toShort()))
            onDismissRequest()
        }) {
            Text("Insert Game")
        }
    }
}

@Composable
fun InsertGame2Groups(onDismissRequest: () -> Unit, onClick: (Game2GroupsUi) -> Unit) {
    InsertGameDialogBase(onDismissRequest = onDismissRequest) {
        var p1 by remember { mutableStateOf("") }
        var p2 by remember { mutableStateOf("") }
        var winningPoints by remember { mutableStateOf("") }
        Row {
            TextField(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F))
            TextField(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F))
        }
        TextField(value = winningPoints, onValueChange = {
            winningPoints = it
        })
        Button(onClick = {
            onClick(Game2GroupsUi(name1 = p1, name2 = p2, winnerPoints = winningPoints.toShort()))
            onDismissRequest()
        }) {
            Text("Insert Game")
        }
    }
}

@Composable
private fun InsertGameDialogBase(onDismissRequest: () -> Unit, content: @Composable () -> Unit) {
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
                content()
            }
        }
    }
}

