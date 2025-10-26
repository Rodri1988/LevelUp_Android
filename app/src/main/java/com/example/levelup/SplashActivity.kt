package com.example.levelup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.levelup.ui.theme.TestWithDBTheme
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestWithDBTheme {
                SplashScreen {
                    // Navegar a MainActivity después de la animación
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }

    // Animación de opacidad (fade in)
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "alpha"
    )

    // Animación de escala (crece)
    val scaleAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.1f,
        animationSpec = tween(durationMillis = 1500),
        label = "scale"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1500) // 5 segundos de duración
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray), // Cambia el color de fondo si lo necesitas
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.imagen2),
            contentDescription = "Logo Level Up",
            modifier = Modifier
                .fillMaxSize()
                .scale(scaleAnim)
                .alpha(alphaAnim)
        )
    }
}