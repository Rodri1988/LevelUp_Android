package com.example.levelup.repository

import androidx.room.*
import com.example.levelup.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    fun getCartItems(userId: String): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(item: CartItem)

    @Update
    suspend fun updateCartItem(item: CartItem)

    @Delete
    suspend fun removeFromCart(item: CartItem)

    @Query("DELETE FROM cart_items WHERE userId = :userId")
    suspend fun clearCart(userId: String)

    @Query("SELECT * FROM cart_items WHERE userId = :userId AND productId = :productId")
    suspend fun getCartItemByProduct(userId: String, productId: String): CartItem?

    @Query("SELECT COUNT(*) FROM cart_items WHERE userId = :userId")
    fun getCartItemCount(userId: String): Flow<Int>
}