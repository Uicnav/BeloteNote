package com.ionvaranita.belotenote

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ionvaranita.belotenote.constants.GameStatus
import kotlinx.coroutines.delay
import kotlin.invoke

@Composable
fun Circle(modifier: Modifier) {
    Box(modifier = modifier.size(24.dp))
}

@Composable
fun RowScope.StatusImage(
    gameStatus: GameStatus?,
    isInMatch: Boolean = false,
    onStatusClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val shouldBlink = isInMatch && gameStatus == GameStatus.CONTINUE

    var visible by remember(shouldBlink) { mutableStateOf(true) }
    LaunchedEffect(shouldBlink) {
        if (shouldBlink) {
            while (true) {
                visible = false
                delay(500)
                visible = true
                delay(500)
            }
        } else {
            visible = true
        }
    }
    Box(modifier = modifier.wrapContentHeight().weight(1f)) {
        val circleModifier = Modifier.clickable(enabled = onStatusClick != null) { onStatusClick?.invoke() }.align(Alignment.Center)
        if (shouldBlink) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = circleModifier,
                visible = visible, enter = fadeIn(), exit = fadeOut()
            ) {
                GameStatusCircle(
                    gameStatus = gameStatus,
                )
            }
        } else {
            GameStatusCircle(
                gameStatus = gameStatus,
                modifier = circleModifier,
            )
        }
    }
}

@Composable
fun GameStatusCircle(
    gameStatus: GameStatus?,
    modifier: Modifier = Modifier
) {
    val circleBase = modifier.clip(CircleShape)
    when (gameStatus) {
        GameStatus.CONTINUE -> {
            Circle(
                modifier = circleBase.background(Color.Green)
            )
        }

        GameStatus.EXTENDED, GameStatus.EXTENDED_MANDATORY -> {
            Circle(circleBase.background(Color.Yellow))
        }

        GameStatus.FINISHED -> {
            Circle(circleBase.background(Color.Red))
        }

        else -> {
            Circle(circleBase.background(Color.Transparent))
        }
    }
}
