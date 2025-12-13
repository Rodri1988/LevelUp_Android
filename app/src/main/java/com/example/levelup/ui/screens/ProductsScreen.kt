package com.example.levelup.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.levelup.ui.components.ProductCard
import com.example.levelup.view_model.ProductsViewModel
import com.example.levelup.view_model.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    onNavigateBack: () -> Unit,
    onProductClick: (Int) -> Unit,
    onNavigateToCart: () -> Unit,
    viewModel: ProductsViewModel,
    cartViewModel: CartViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val cartItemCount by cartViewModel.cartItemCount.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToCart) {
                        BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge {
                                        Text(cartItemCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, "Carrito")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(uiState.products) { product ->
                ProductCard(
                    product = product,
                    onClick = { onProductClick(product.id) },
                    onAddToCart = {
                        // Agregar al carrito
                        cartViewModel.addToCart(
                            productId = product.id.toString(),
                            productName = product.name,
                            productPrice = product.price,
                            productImage = product.imageUrl
                        )

                        // Mostrar mensaje
                        Toast.makeText(
                            context,
                            "${product.name} agregado al carrito",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}