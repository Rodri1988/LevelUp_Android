package com.example.levelup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@Composable
fun IndexScreen(navController: NavController, username: String) {
    var searchQuery by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo
        Image(
            painter = painterResource(id = R.drawable.index),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Contenedor principal
        Column(modifier = Modifier.fillMaxSize()) {

            // 🔍 Barra de búsqueda
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(55.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text("Buscar...", color = Color.Gray, fontSize = 18.sp)
                    },
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
                        .height(45.dp)
                )
            }

            // 🧭 Barra de navegación
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(6.dp, RoundedCornerShape(50))
                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(50))
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavTextButton("Home") { navController.navigate(ScreenRoute.Home.route) }
                NavTextButton("Perfil") { navController.navigate(ScreenRoute.Profile.route) }
                NavTextButton("Catálogo") { navController.navigate(ScreenRoute.Products.route) }
                NavTextButton("Cerrar sesión") { navController.navigate(ScreenRoute.Home.route) }
            }

            // 📜 Contenido desplazable
            Column(
                modifier = Modifier
                    .weight(1f) // hace que el contenido ocupe el espacio libre entre las barras
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bienvenido, $username",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                for (i in 1..6) {
                    Text(
                        text = "Contenido de ejemplo $i",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .background(
                                Color.Black.copy(alpha = 0.5f),
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            }

            // 🔽 Barra inferior fija con los botones de sesión 🔽
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(6.dp, RoundedCornerShape(50))
                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(50))
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate(ScreenRoute.Login.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF39FF14)
                    ),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                ) {
                    Text(
                        "Iniciar Sesión",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(
                    onClick = { navController.navigate(ScreenRoute.Register.route) },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF39FF14)
                    ),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                ) {
                    Text(
                        "Registrarse",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

// 🌟 Botón de navegación reutilizable
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