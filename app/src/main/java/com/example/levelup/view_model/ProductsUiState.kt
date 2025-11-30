package com.example.levelup.view_model

import com.example.levelup.model.ProductEntity

data class ProductsUiState(
    val products: List<ProductEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)