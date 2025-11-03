package com.example.levelup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.levelup.R
import com.example.levelup.view_model.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onNavigateBack: () -> Unit,
    viewModel: ProductsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val product = uiState.products.find { it.id == productId }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.imagen3),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Detalle del Producto") },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, "Volver")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color(0xFF39FF14),
                        navigationIconContentColor = Color(0xFF39FF14)
                    )
                )
            }
        ) { padding ->
            if (product == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Producto no encontrado",
                        color = Color.White
                    )
                }
            } else {
                val imageResId = context.resources.getIdentifier(
                    product.imageUrl,
                    "drawable",
                    context.packageName
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Imagen del producto
                    Image(
                        painter = painterResource(
                            id = if (imageResId != 0) imageResId else android.R.drawable.ic_menu_gallery
                        ),
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )

                    // Información del producto
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black.copy(alpha = 0.8f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = product.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Precio formateado con 3 decimales
                                Text(
                                    text = "$${String.format("%.3f", product.price)}",
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color(0xFF39FF14)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "⭐ ${product.rating} | Stock: ${product.stock} unidades",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                HorizontalDivider(color = Color(0xFF39FF14).copy(alpha = 0.3f))

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "Descripción",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color(0xFF39FF14)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = product.description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "Categoría: ${product.category}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}