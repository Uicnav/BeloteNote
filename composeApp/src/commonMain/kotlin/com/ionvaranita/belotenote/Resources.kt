package com.ionvaranita.belotenote

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ionvaranita.belotenote.constants.GameStatus

@Composable
private fun RedCircle(modifier: Modifier) {
    Box(modifier = modifier.size(24.dp).clip(CircleShape).background(Color.Red))
}

@Composable
private fun YellowCircle(modifier: Modifier) {
    Box(modifier = modifier.size(24.dp).clip(CircleShape).background(Color.Yellow))
}

@Composable
private fun GreenCircle(modifier: Modifier) {
    Box(modifier = modifier.size(24.dp).clip(CircleShape).background(Color.Green))
}

@Composable
fun StatusImage(gameStatus: GameStatus?, modifier: Modifier = Modifier.padding(16.dp)){

    when (gameStatus) {
        GameStatus.TO_START, GameStatus.CONTINUE -> {
            GreenCircle(modifier)
        }
        GameStatus.EXTENDED, GameStatus.EXTENDED_MANDATORY -> {
            YellowCircle(modifier)
        }
        GameStatus.FINISHED -> {
            RedCircle(modifier)
        }
        else -> {
            throw IllegalStateException()
        }
    }
}




