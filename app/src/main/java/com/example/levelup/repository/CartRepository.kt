package com.example.levelup.repository

import com.example.levelup.model.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    fun getCartItems(userId: String): Flow<List<CartItem>> =
        cartDao.getCartItems(userId)

    fun getCartItemCount(userId: String): Flow<Int> =
        cartDao.getCartItemCount(userId)

    suspend fun addToCart(item: CartItem) {
        val existingItem = cartDao.getCartItemByProduct(item.userId, item.productId)
        if (existingItem != null) {
            // Si ya existe, incrementa la cantidad
            cartDao.updateCartItem(
                existingItem.copy(quantity = existingItem.quantity + 1)
            )
        } else {
            cartDao.addToCart(item)
        }
    }

    suspend fun updateQuantity(item: CartItem, newQuantity: Int) {
        if (newQuantity > 0) {
            cartDao.updateCartItem(item.copy(quantity = newQuantity))
        } else {
            cartDao.removeFromCart(item)
        }
    }

    suspend fun removeItem(item: CartItem) {
        cartDao.removeFromCart(item)
    }

    suspend fun clearCart(userId: String) {
        cartDao.clearCart(userId)
    }
}