package com.ionvaranita.belotenote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ionvaranita.belotenote.constants.GameStatus

@Composable
fun Circle(modifier: Modifier) {
    Box(modifier = modifier)
}

@Composable
fun StatusImage(gameStatus: GameStatus?, modifier: Modifier = Modifier.padding(8.dp)){

    when (gameStatus) {
        GameStatus.CONTINUE -> {
            Circle(modifier.size(24.dp).clip(CircleShape).background(Color.Green))
        }
        GameStatus.EXTENDED, GameStatus.EXTENDED_MANDATORY -> {
            Circle(modifier.size(24.dp).clip(CircleShape).background(Color.Yellow))
        }
        GameStatus.FINISHED -> {
            Circle(modifier.size(24.dp).clip(CircleShape).background(Color.Red))
        }
        else -> {
            throw IllegalStateException()
        }
    }
}




