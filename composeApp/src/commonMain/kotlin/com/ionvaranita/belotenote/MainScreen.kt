package com.ionvaranita.belotenote

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(onClick: (AppNavDest) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(modifier = Modifier.fillMaxSize(), painter = painterResource(Res.drawable.compose_multiplatform), contentDescription = "", contentScale = ContentScale.FillBounds)
        Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxSize()) {
            MainButton(gamePath = GamePath.TWO, onClick = {
                onClick(AppNavDest.TWO)
            })
            MainButton(gamePath = GamePath.THREE, onClick ={
                onClick(AppNavDest.THREE)
            })
            MainButton(gamePath = GamePath.FOUR, onClick ={
                onClick(AppNavDest.FOUR)
            })
            MainButton(gamePath = GamePath.GROUP, onClick = {
                onClick(AppNavDest.GROUP)
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
