package com.ionvaranita.belotenote.ui.match

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.game
import com.ionvaranita.belotenote.StatusImage
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.ui.LocalAppDatabase
import com.ionvaranita.belotenote.ui.LocalNavHostController
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2PPViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2PUiState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MatchScreen2(idGame: Int) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current
    val viewModel = viewModel { Match2PPViewModel(appDatabase) }
    val matchUiState = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getMatchData(idGame)
    }
    Column {
        Row {
            when (val matchState = matchUiState.value) {
                is Match2PUiState.Success -> {
                    val game = matchState.data.game
                    Column(modifier = Modifier.weight(1F)) {
                        StatusImage(gameStatus = GameStatus.fromId(game.statusGame))
                        Text(text = stringResource(Res.string.game))
                    }
                    Column(modifier = Modifier.weight(1F)) {
                        Text(text = game.scoreName1.toString())
                        Text(text = game.name1)
                    }

                    Column(modifier = Modifier.weight(1F)) {
                        Text(text = game.scoreName2.toString())
                        Text(text = game.name2)
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

@Composable
internal fun MatchScreenGroups(idGame: Int) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current

}

@Composable
private fun MatchBase(modifier: Modifier = Modifier, content: @Composable () -> Unit) {

}