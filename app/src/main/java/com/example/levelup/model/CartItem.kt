package com.example.levelup.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: String,
    val productName: String,
    val productPrice: Double,
    val productImage: String,
    val quantity: Int = 1,
    val userId: String
)

data class CartSummary(
    val items: List<CartItem>,
    val subtotal: Double,
    val tax: Double,
    val total: Double
) {
    companion object {
        fun calculate(items: List<CartItem>): CartSummary {
            val subtotal = items.sumOf { it.productPrice * it.quantity }
            val tax = subtotal * 0.19
            return CartSummary(
                items = items,
                subtotal = subtotal,
                tax = tax,
                total = subtotal + tax
            )
        }
    }
}