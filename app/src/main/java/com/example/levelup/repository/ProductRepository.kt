package com.example.levelup.repository

import com.example.levelup.model.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: Flow<List<ProductEntity>> = productDao.getAllProducts()

    suspend fun getProductById(id: Int): ProductEntity? {
        return productDao.getProductById(id)
    }

    suspend fun getProductCount(): Int {
        return productDao.getProductCount()
    }

    // Inicializar productos de ejemplo
    suspend fun initializeSampleProducts() {
        if (getProductCount() == 0) {
            val sampleProducts = listOf(
                ProductEntity(
                    name = "Gaming Headset Pro",
                    description = "Audífonos gaming con micrófono profesional, sonido envolvente 7.1 y cancelación de ruido activa",
                    price = 89.99,
                    imageUrl = "https://images.unsplash.com/photo-1599669454699-248893623440?w=400",
                    category = "Audio",
                    stock = 15,
                    rating = 4.5f
                ),
                ProductEntity(
                    name = "Mechanical Keyboard RGB",
                    description = "Teclado mecánico con switches Cherry MX Red, iluminación RGB personalizable y teclas programables",
                    price = 129.99,
                    imageUrl = "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=400",
                    category = "Periféricos",
                    stock = 8,
                    rating = 4.8f
                ),
                ProductEntity(
                    name = "Gaming Mouse Elite",
                    description = "Mouse gaming de alta precisión con sensor óptico de 16000 DPI y 8 botones programables",
                    price = 59.99,
                    imageUrl = "https://images.unsplash.com/photo-1527814050087-3793815479db?w=400",
                    category = "Periféricos",
                    stock = 20,
                    rating = 4.6f
                ),
                ProductEntity(
                    name = "Monitor Gaming 27\"",
                    description = "Monitor curvo gaming de 27 pulgadas, 144Hz, 1ms de respuesta, ideal para gaming competitivo",
                    price = 299.99,
                    imageUrl = "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=400",
                    category = "Monitores",
                    stock = 5,
                    rating = 4.7f
                )
            )
            productDao.insertAllProducts(sampleProducts)
        }
    }
}