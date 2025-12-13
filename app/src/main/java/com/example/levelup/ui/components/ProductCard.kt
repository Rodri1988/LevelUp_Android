package com.example.levelup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.levelup.model.ProductEntity
import java.text.NumberFormat
import java.util.*

@Composable
fun ProductCard(
    product: ProductEntity,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Imagen del producto
            val imageRes = when (product.imageUrl) {
                "product_headset" -> com.example.levelup.R.drawable.product_headset
                "product_keyboard" -> com.example.levelup.R.drawable.product_keyboard
                "product_mouse" -> com.example.levelup.R.drawable.product_mouse
                "product_monitor" -> com.example.levelup.R.drawable.product_monitor
                else -> com.example.levelup.R.drawable.product_headset
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${product.rating}/5",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Stock: ${product.stock}",
                style = MaterialTheme.typography.bodySmall,
                color = if (product.stock > 0) Color(0xFF006400) else Color.Red
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currencyFormat.format(product.price),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                FilledIconButton(
                    onClick = onAddToCart,
                    enabled = product.stock > 0,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Agregar al carrito",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}