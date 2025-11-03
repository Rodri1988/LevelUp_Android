package com.example.levelup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.platform.LocalContext
import com.example.levelup.utils.SessionManager


@Composable
fun IndexScreen(navController: NavController, username: String) {
    var password by remember { mutableStateOf("") } // Variable para la contraseña

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.index),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido, $username",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000),
                modifier = Modifier.padding(vertical = 16.dp)
            )


            Spacer(modifier = Modifier.height(20.dp))

            // Barra superior: Logo + búsqueda
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Buscar...", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(25.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.8f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.6f),
                        focusedBorderColor = Color(0xFF39FF14),
                        unfocusedBorderColor = Color.Black,
                        cursorColor = Color(0xFF39FF14)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Barra de navegación superior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 10.dp)
                    .shadow(6.dp, RoundedCornerShape(50))
                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(50)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavTextButton("Home") { navController.navigate(ScreenRoute.Home.route) }
                NavTextButton("Perfil") { navController.navigate(ScreenRoute.Profile.route)}
                NavTextButton("Catálogo") { navController.navigate(ScreenRoute.Products.route) }
                //NavTextButton("Carrito") { navController.navigate(ScreenRoute.Home.route) }
                NavTextButton("Cerrar sesión") { navController.navigate(ScreenRoute.Home.route) }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun NavTextButton(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            fontSize = 13.sp,
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