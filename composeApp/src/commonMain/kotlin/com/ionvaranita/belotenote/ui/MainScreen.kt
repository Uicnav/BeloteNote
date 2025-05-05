package com.ionvaranita.belotenote.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ionvaranita.belotenote.Games2
import com.ionvaranita.belotenote.Games3
import com.ionvaranita.belotenote.Games4
import com.ionvaranita.belotenote.GamesGroups
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(onClick: (Any) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxSize()) {
            MainButton(gamePath = GamePath.TWO, onClick = {
                onClick(Games2)
            })
            MainButton(gamePath = GamePath.THREE, onClick ={
                onClick(Games3)
            })
            MainButton(gamePath = GamePath.FOUR, onClick ={
                onClick(Games4)
            })
            MainButton(gamePath = GamePath.GROUP, onClick = {
                onClick(GamesGroups)
            })
        }
    }
}

@Composable
private fun ColumnScope.MainButton(gamePath: GamePath,onClick: () -> Unit) {
    Button(onClick = {
        onClick()
    }, modifier = Modifier.fillMaxWidth().weight(1F).padding(16.dp).alpha(.93F), shape = RoundedCornerShape(20.dp)) {
        Text(text = gamePath.title, fontSize = 64.sp, fontWeight = FontWeight.Bold)
    }
}


enum class GamePath(val title: String){
    TWO("2"), THREE("3"), FOUR("4"), GROUP("2VS2")
}
