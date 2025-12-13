package com.example.levelup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.model.CartItem
import com.example.levelup.model.CartSummary
import com.example.levelup.repository.CartRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val currentUserId: String
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    val cartSummary: StateFlow<CartSummary> = _cartItems.map { items ->
        CartSummary.calculate(items)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CartSummary(emptyList(), 0.0, 0.0, 0.0)
    )

    val cartItemCount: StateFlow<Int> = cartRepository
        .getCartItemCount(currentUserId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    init {
        loadCart()
    }

    private fun loadCart() {
        viewModelScope.launch {
            cartRepository.getCartItems(currentUserId).collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun addToCart(
        productId: String,
        productName: String,
        productPrice: Double,
        productImage: String
    ) {
        viewModelScope.launch {
            val item = CartItem(
                productId = productId,
                productName = productName,
                productPrice = productPrice,
                productImage = productImage,
                quantity = 1,
                userId = currentUserId
            )
            cartRepository.addToCart(item)
        }
    }

    fun updateQuantity(item: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(item, newQuantity)
        }
    }

    fun removeItem(item: CartItem) {
        viewModelScope.launch {
            cartRepository.removeItem(item)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart(currentUserId)
        }
    }
}