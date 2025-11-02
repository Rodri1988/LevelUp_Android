package com.example.levelup.model

data class ProductUIState(
    val products: List<ProductEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)