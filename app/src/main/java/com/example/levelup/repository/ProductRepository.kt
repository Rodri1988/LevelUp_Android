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
                    price = 89.990,
                    imageUrl = "product_headset",
                    category = "Audio",
                    stock = 15,
                    rating = 4.5f
                ),
                ProductEntity(
                    name = "Mechanical Keyboard RGB",
                    description = "Teclado mecánico con switches Cherry MX Red, iluminación RGB personalizable y teclas programables",
                    price = 129.990,
                    imageUrl = "product_keyboard",
                    category = "Periféricos",
                    stock = 8,
                    rating = 4.8f
                ),
                ProductEntity(
                    name = "Gaming Mouse Elite",
                    description = "Mouse gaming de alta precisión con sensor óptico de 16000 DPI y 8 botones programables",
                    price = 59.990,
                    imageUrl = "product_mouse",
                    category = "Periféricos",
                    stock = 20,
                    rating = 4.6f
                ),
                ProductEntity(
                    name = "Monitor Gaming 27\"",
                    description = "Monitor curvo gaming de 27 pulgadas, 144Hz, 1ms de respuesta, ideal para gaming competitivo",
                    price = 299.990,
                    imageUrl = "product_monitor",
                    category = "Monitores",
                    stock = 5,
                    rating = 4.7f
                ),
                ProductEntity(
                    name = "Webcam HD 1080p",
                    description = "Cámara web Full HD con micrófono integrado y autofocus",
                    price = 79.990,
                    imageUrl = "product_headset",
                    category = "Periféricos",
                    stock = 12,
                    rating = 4.3f
                ),
                ProductEntity(
                    name = "Micrófono USB Condensador",
                    description = "Micrófono profesional con filtro anti-pop y brazo ajustable",
                    price = 89.990,
                    imageUrl = "product_mouse",
                    category = "Audio",
                    stock = 8,
                    rating = 4.6f
                ),
                ProductEntity(
                    name = "Mousepad Gaming XXL",
                    description = "Alfombrilla extendida con superficie de microtextura",
                    price = 29.990,
                    imageUrl = "product_keyboard",
                    category = "Accesorios",
                    stock = 25,
                    rating = 4.4f
                ),
                ProductEntity(
                    name = "Hub USB 3.0",
                    description = "Hub 7 puertos con carga rápida",
                    price = 39.990,
                    imageUrl = "product_monitor",
                    category = "Accesorios",
                    stock = 15,
                    rating = 4.2f
                ),
                ProductEntity(
                    name = "Cable HDMI 2.1",
                    description = "Cable HDMI 4K@120Hz, 3 metros",
                    price = 19.990,
                    imageUrl = "product_headset",
                    category = "Cables",
                    stock = 30,
                    rating = 4.7f
                ),
                ProductEntity(
                    name = "Luces LED RGB",
                    description = "Tira LED RGB 5 metros con control remoto",
                    price = 34.990,
                    imageUrl = "product_mouse",
                    category = "Iluminación",
                    stock = 18,
                    rating = 4.5f
                ),
                ProductEntity(
                    name = "Soporte Monitor Dual",
                    description = "Brazo articulado para 2 monitores hasta 27 pulgadas",
                    price = 89.990,
                    imageUrl = "product_monitor",
                    category = "Accesorios",
                    stock = 7,
                    rating = 4.6f
                )
            )
            productDao.insertAllProducts(sampleProducts)
        }
    }

}