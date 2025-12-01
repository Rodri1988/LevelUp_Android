package com.example.levelup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.levelup.model.ProductEntity
import com.example.levelup.repository.ProductRepository
import com.example.levelup.view_model.ProductsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var viewModel: ProductsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel carga productos correctamente`() = runTest {
        // Given
        val mockProducts = listOf(
            ProductEntity(
                id = 1,
                name = "Gaming Mouse",
                description = "High precision mouse",
                price = 59.99,
                imageUrl = "mouse.jpg",
                category = "Peripherals",
                stock = 10,
                rating = 4.5f
            ),
            ProductEntity(
                id = 2,
                name = "Gaming Keyboard",
                description = "Mechanical keyboard",
                price = 129.99,
                imageUrl = "keyboard.jpg",
                category = "Peripherals",
                stock = 5,
                rating = 4.8f
            )
        )

        `when`(productRepository.allProducts).thenReturn(flowOf(mockProducts))

        // When
        viewModel = ProductsViewModel(productRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(2, viewModel.uiState.value.products.size)
        assertEquals("Gaming Mouse", viewModel.uiState.value.products[0].name)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `viewModel muestra estado de carga inicial`() = runTest {
        // Given
        `when`(productRepository.allProducts).thenReturn(flowOf(emptyList()))

        // When
        viewModel = ProductsViewModel(productRepository)

        // Then - Verifica que inicialmente está cargando
        // Luego avanza y verifica que termina
        testDispatcher.scheduler.advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isLoading)
    }
}