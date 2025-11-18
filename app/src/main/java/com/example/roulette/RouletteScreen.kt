package com.example.roulette

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun RouletteScreen() {
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 룰렛 원
        Box(
            modifier = Modifier
                .size(250.dp)
                .graphicsLayer(rotationZ = rotation.value)
                .background(Color.White, shape = CircleShape)
                .border(4.dp, Color.DarkGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val colors = listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue)
                val sweep = 360f / colors.size

                colors.forEachIndexed { index, color ->
                    drawArc(
                        color = color,
                        startAngle = index * sweep,
                        sweepAngle = sweep,
                        useCenter = true
                    )
                }
            }

            Text("ROULETTE", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                scope.launch {
                    val targetRotation = (720..1440).random().toFloat() // 2~4바퀴 랜덤 회전
                    rotation.animateTo(
                        targetValue = rotation.value + targetRotation,
                        animationSpec = tween(
                            durationMillis = 2000,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }
        ) {
            Text("Spin")
        }
    }
}
