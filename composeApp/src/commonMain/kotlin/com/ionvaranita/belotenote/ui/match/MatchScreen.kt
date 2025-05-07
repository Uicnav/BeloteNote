package com.ionvaranita.belotenote.ui.match

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ionvaranita.belotenote.ui.LocalAppDatabase
import com.ionvaranita.belotenote.ui.LocalNavHostController


@Composable
internal fun MatchScreen2(idGame: Int) {
    val navController = LocalNavHostController.current
    val appDatabase = LocalAppDatabase.current
    Text("This is idGame = $idGame")
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