package com.example.levelup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup.R
import com.example.levelup.navigation.ScreenRoute

@Composable
fun HomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo con imagen
        Image(
            painter = painterResource(id = R.drawable.imagen1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Botones flotando sobre la imagen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeNavButton("Inicio de Sesión") {
                navController.navigate(ScreenRoute.Login.route)
            }

            Spacer(modifier = Modifier.height(20.dp))

            HomeNavButton("Registro") {
                navController.navigate(ScreenRoute.Register.route)
            }
        }
    }
}

@Composable
fun HomeNavButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, shape = RoundedCornerShape(50))
            .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(50))
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                color = Color(0xFF39FF14),
                shadow = androidx.compose.ui.graphics.Shadow(
                    color = Color(0xFF00FF00),
                    offset = Offset(0f, 0f),
                    blurRadius = 8f
                )
            )
        )
    }
}