package com.ionvaranita.belotenote.ui.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    Column(modifier = Modifier.fillMaxSize().clip(shape = RoundedCornerShape(16.dp)).padding(8.dp).background(Color.Red)) {
        Row(modifier = Modifier.padding(16.dp).wrapContentSize().clip(shape = RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.surfaceContainerHighest),
            horizontalArrangement = Arrangement.Center) {
            when (val matchState = matchUiState.value) {
                is Match2PUiState.Success -> {
                    val game = matchState.data.game
                    Column(modifier = Modifier.weight(1F), horizontalAlignment = Alignment.CenterHorizontally) {
                        StatusImage(gameStatus = GameStatus.fromId(game.statusGame), modifier = Modifier.padding(8.dp))
                        TableTextAtom(text = stringResource(Res.string.game))
                    }
                    Column(modifier = Modifier.weight(1F), horizontalAlignment = Alignment.CenterHorizontally) {
                        TableTextAtom(text = game.scoreName1.toString())
                        TableTextAtom(text = game.name1)
                    }

                    Column(modifier = Modifier.weight(1F), horizontalAlignment = Alignment.CenterHorizontally) {
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

}

@Composable
private fun ColumnScope.TableTextAtom(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier.padding(8.dp))
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

@Preview
@Composable
private fun MatchScreen2Preview() {
    MatchScreen2(1)
}