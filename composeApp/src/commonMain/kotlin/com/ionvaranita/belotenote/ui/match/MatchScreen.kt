package com.ionvaranita.belotenote.ui.match

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.ui.GamePath

@Composable
fun MatchScreen(appDatabase: AppDatabase, gamePath: GamePath, idGame: Short) {
    when (gamePath) {
        GamePath.TWO -> Match2P(appDatabase = appDatabase, idGame = idGame)
        GamePath.THREE -> Match3P(appDatabase = appDatabase, idGame = idGame)
        GamePath.FOUR -> Match4P(appDatabase = appDatabase, idGame = idGame)
        GamePath.GROUP -> Match2Groups(appDatabase = appDatabase, idGame = idGame)
    }

}

@Composable
private fun Match2P(appDatabase: AppDatabase, idGame: Short){
    Text("This is idGame = $idGame")
}

@Composable
private fun Match3P(appDatabase: AppDatabase, idGame: Short){

}

@Composable
private fun Match4P(appDatabase: AppDatabase, idGame: Short){

}

@Composable
private fun Match2Groups(appDatabase: AppDatabase, idGame: Short){

}