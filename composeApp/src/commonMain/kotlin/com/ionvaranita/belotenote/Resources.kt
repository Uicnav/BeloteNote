package com.ionvaranita.belotenote

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.ionvaranita.belotenote.constants.GameStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.hypot

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
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    var pressed by remember { mutableStateOf(false) }
    var touch by remember { mutableStateOf(Offset.Unspecified) }
    val wave = remember { Animatable(0f) }
    val scale by animateFloatAsState(if (pressed) 0.94f else 1f, tween(90), label = "")
    val tint = Color.Unspecified

    Box(modifier = modifier.wrapContentHeight().weight(1f)) {
        val circleModifier =
            Modifier.clickable(enabled = onStatusClick != null) { onStatusClick?.invoke() }
                .align(Alignment.Center).scale(scale)
                .pointerInput(onStatusClick, gameStatus, isInMatch) {
                    detectTapGestures(
                        onPress = {
                            if (onStatusClick == null) return@detectTapGestures
                            pressed = true
                            touch = it
                            wave.snapTo(0f)
                            val job = scope.launch { wave.animateTo(1f, tween(220)) }
                            val released = tryAwaitRelease()
                            pressed = false
                            if (released) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                onStatusClick()
                            } else job.cancel()
                        })
                }.drawBehind {
                    if (touch.isSpecified && wave.value > 0f) {
                        val c = if (tint == Color.Unspecified) Color.Black else tint
                        val r = hypot(size.width.toDouble(), size.height.toDouble()).toFloat()
                        drawCircle(
                            color = c.copy(alpha = 0.24f * (1f - wave.value)),
                            radius = r * wave.value,
                            center = touch
                        )
                    }
                }
        if (shouldBlink) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = circleModifier, visible = visible, enter = fadeIn(), exit = fadeOut()
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
    gameStatus: GameStatus?, modifier: Modifier = Modifier
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
