package com.ionvaranita.belotenote

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.usecase.GetGames2PUseCase
import com.ionvaranita.belotenote.viewmodel.Game2PViewModel
import com.ionvaranita.belotenote.viewmodel.Games2PUiState
import kotlinx.coroutines.launch

@Composable
fun TableScreen(appDatabase: AppDatabase,gamePath: GamePath) {
    when (gamePath) {
        GamePath.TWO -> Tables2P(appDatabase)
        GamePath.THREE -> Tables2P(appDatabase)
        GamePath.FOUR -> Tables2P(appDatabase)
        GamePath.GROUP -> Tables2P(appDatabase)
    }

}

@Composable
private fun Tables2P(appDatabase: AppDatabase) {
    val viewModel = viewModel {Game2PViewModel(appDatabase)}
    val scope = rememberCoroutineScope()
    Column {
        Button(onClick = {
            scope.launch {

                viewModel.getGames()
            }
        }) {
            Text("Fetch DB")
        }
        Button(onClick = {
            scope.launch {

                viewModel.insertGame(game2PUi = Game2PUi(winnerPoints = 101, name1 = "Ion", name2 = "Van"))
            }
        }) {
            Text("InsertGame")
        }
        val gamesUiState = viewModel.uiState.collectAsState()
        when(gamesUiState.value) {
            is Games2PUiState.Success -> {
                println("Data ${gamesUiState.value}")
            }
            is Games2PUiState.Error -> {

            }
        }
    }

}
