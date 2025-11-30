package com.example.levelup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelup.R
import com.example.levelup.model.ProductEntity

@Composable
fun ProductCard(
    product: ProductEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },  // clave para que funcione el click
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            val imageResId = when(product.imageUrl) {
                "product_headset" -> R.drawable.product_headset
                "product_keyboard" -> R.drawable.product_keyboard
                "product_mouse" -> R.drawable.product_mouse
                "product_monitor" -> R.drawable.product_monitor
                else -> R.drawable.product_headset
            }

            Image(
                painter = painterResource(imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 2,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$${product.price.toInt()} CLP",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF39FF14),
                fontWeight = FontWeight.Bold
            )

            product.priceUSD?.let { priceUSD ->
                Text(
                    text = "US$${String.format("%.2f", priceUSD)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            if (product.rating > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "⭐ ${String.format("%.1f", product.rating)}/5",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (product.stock > 0) "Stock: ${product.stock}" else "Sin stock",
                style = MaterialTheme.typography.bodySmall,
                color = if (product.stock > 0) Color(0xFF39FF14) else Color.Red,
                fontSize = 11.sp
            )
        }
    }
}
