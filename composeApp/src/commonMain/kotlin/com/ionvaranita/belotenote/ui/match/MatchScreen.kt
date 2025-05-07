package com.ionvaranita.belotenote.ui.match

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ionvaranita.belotenote.ui.GamePath
import com.ionvaranita.belotenote.ui.LocalAppDatabase
import com.ionvaranita.belotenote.ui.LocalNavHostController

@Composable
fun MatchScreen(gamePath: GamePath, idGame: Short) {
    when (gamePath) {
        GamePath.TWO -> Match2P(idGame = idGame)
        GamePath.THREE -> Match3P(idGame = idGame)
        GamePath.FOUR -> Match4P(idGame = idGame)
        GamePath.GROUP -> Match2Groups(idGame = idGame)
    }

}

@Composable
private fun Match2P(idGame: Short) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current
    Text("This is idGame = $idGame")
}

@Composable
private fun Match3P(idGame: Short) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current

}

@Composable
private fun Match4P(idGame: Short) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current

}

@Composable
private fun Match2Groups(idGame: Short) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current

}