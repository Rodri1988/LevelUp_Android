package com.example.levelup.ui.screens

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup.R
import com.example.levelup.navigation.ScreenRoute
import com.example.levelup.view_model.FactViewModel
import com.example.levelup.view_model.CartViewModel
import com.example.levelup.view_model.ViewModelFactory

@Composable
fun IndexScreen(navController: NavController, username: String) {
    var searchQuery by remember { mutableStateOf("") }
    val factViewModel: FactViewModel = viewModel()
    val fact by factViewModel.fact.collectAsState()

    // ✅ Agregar CartViewModel
    val context = LocalContext.current
    val cartViewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(context.applicationContext as Application)
    )
    val cartItemCount by cartViewModel.cartItemCount.collectAsState()

    // ✅ Cambiar Box por Scaffold para agregar FAB
    Scaffold(
        floatingActionButton = {
            // ✅ Botón flotante del carrito
            FloatingActionButton(
                onClick = { navController.navigate(ScreenRoute.Cart.route) },
                containerColor = Color(0xFF39FF14),
                contentColor = Color.Black
            ) {
                BadgedBox(
                    badge = {
                        if (cartItemCount > 0) {
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text(cartItemCount.toString())
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Fondo
            Image(
                painter = painterResource(id = R.drawable.index),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            // Contenedor principal
            Column(modifier = Modifier.fillMaxSize()) {

                // Barra de búsqueda
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

                // Barra de navegación
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

                // CONTENIDO DESPLAZABLE
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // 1. Banner personal con diseño gamer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
                            .border(2.dp, Color(0xFF39FF14), RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = "Bienvenido, $username",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF39FF14)
                            )
                            Text(
                                text = "Explora nuevas ofertas hoy",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    // ===== BLOQUE DEL DATO NERD =====
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, Color(0xFF39FF14), RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Black.copy(alpha = 0.85f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Dato nerd del día:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF39FF14)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = fact,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { factViewModel.fetchFact() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF39FF14)
                                )
                            ) {
                                Text("Otro dato", color = Color.Black)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    // 2. Carrusel de ofertas
                    Text(
                        text = "OFERTAS DE LA SEMANA",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(top = 12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.oferta1),
                            contentDescription = "Oferta 1",
                            modifier = Modifier
                                .width(220.dp)
                                .height(140.dp)
                                .padding(end = 12.dp)
                                .shadow(6.dp, RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Image(
                            painter = painterResource(id = R.drawable.oferta2),
                            contentDescription = "Oferta 2",
                            modifier = Modifier
                                .width(220.dp)
                                .height(140.dp)
                                .padding(end = 12.dp)
                                .shadow(6.dp, RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Image(
                            painter = painterResource(id = R.drawable.oferta3),
                            contentDescription = "Oferta 3",
                            modifier = Modifier
                                .width(220.dp)
                                .height(140.dp)
                                .shadow(6.dp, RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    // 3. Cupones o recompensas
                    Text(
                        text = "Recompensas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    RewardCard("Gana 300 XP por tu primera compra")
                    RewardCard("10% OFF en productos RGB")

                    Spacer(modifier = Modifier.height(25.dp))

                    // 4. Opiniones de clientes
                    Text(
                        text = "Opiniones de clientes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    ReviewCard("Excelente calidad en los periféricos, 10/10.", "Carlos")
                    ReviewCard("Envío rápido y productos originales.", "María")

                    Spacer(modifier = Modifier.height(25.dp))

                    // 5. Eventos
                    Text(
                        text = "Eventos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    EventCard("Torneo online de Valorant – Inscríbete")
                    EventCard("Ofertas especiales durante Black Friday")
                    EventCard("Renovación de catálogo esta semana")
                }
                // ✅ Aquí termina el contenido, SIN barra inferior
            }
        }
    }
}

// COMPONENTES EXTRA

@Composable
fun RewardCard(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = Color.White,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(12.dp)
    )
}

@Composable
fun ReviewCard(review: String, author: String) {
    Column(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(text = review, color = Color.White, fontSize = 15.sp)
        Text(text = "- $author", color = Color(0xFF39FF14), fontSize = 13.sp)
    }
}

@Composable
fun EventCard(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(12.dp)
    )
}

// NAV BUTTON
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