package com.example.levelup.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val priceUSD: Double? = null,
    val imageUrl: String,
    val category: String,
    val stock: Int,
    val rating: Float = 0f
)