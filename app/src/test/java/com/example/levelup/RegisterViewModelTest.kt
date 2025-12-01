package com.example.levelup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.levelup.model.UserEntity
import com.example.levelup.repository.UserRepository
import com.example.levelup.view_model.RegisterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = RegisterViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onChangeUsername actualiza el estado correctamente`() {
        // Given
        val username = "testuser"

        // When
        viewModel.onChangeUsername(username)

        // Then
        assertEquals(username, viewModel.state.value.username)
    }

    @Test
    fun `onChangeUsername con valor vacío muestra error`() {
        // When
        viewModel.onChangeUsername("")

        // Then
        assertNotNull(viewModel.state.value.errors.usernameError)
    }

    @Test
    fun `onChangeEmail con email válido no muestra error`() {
        // Given
        val validEmail = "test@example.com"

        // When
        viewModel.onChangeEmail(validEmail)

        // Then
        assertEquals(validEmail, viewModel.state.value.email)
        assertNull(viewModel.state.value.errors.emailError)
    }

    @Test
    fun `onChangeEmail con email inválido muestra error`() {
        // Given
        val invalidEmail = "invalid-email"

        // When
        viewModel.onChangeEmail(invalidEmail)

        // Then
        assertNotNull(viewModel.state.value.errors.emailError)
    }

    @Test
    fun `onChangePassword con contraseña corta muestra error`() {
        // Given
        val shortPassword = "12345"

        // When
        viewModel.onChangePassword(shortPassword)

        // Then
        assertNotNull(viewModel.state.value.errors.passwordError)
    }

    @Test
    fun `onChangePassword con contraseña válida no muestra error`() {
        // Given
        val validPassword = "123456"

        // When
        viewModel.onChangePassword(validPassword)

        // Then
        assertEquals(validPassword, viewModel.state.value.password)
        assertNull(viewModel.state.value.errors.passwordError)
    }

    @Test
    fun `onChangeConfirmPassword con contraseñas que no coinciden muestra error`() {
        // Given
        viewModel.onChangePassword("password123")

        // When
        viewModel.onChangeConfirmPassword("different")

        // Then
        assertNotNull(viewModel.state.value.errors.confirmPasswordError)
    }

    @Test
    fun `onChangeConfirmPassword con contraseñas que coinciden no muestra error`() {
        // Given
        val password = "password123"
        viewModel.onChangePassword(password)

        // When
        viewModel.onChangeConfirmPassword(password)

        // Then
        assertNull(viewModel.state.value.errors.confirmPasswordError)
    }

    @Test
    fun `onProfileImageSelected actualiza URI correctamente`() {
        // Given
        val imageUri = "content://image.jpg"

        // When
        viewModel.onProfileImageSelected(imageUri)

        // Then
        assertEquals(imageUri, viewModel.state.value.profileImageUri)
    }
}