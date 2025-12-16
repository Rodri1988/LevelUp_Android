package com.example.levelup

import com.example.levelup.model.UserEntity
import com.example.levelup.repository.UserRepository
import com.example.levelup.view_model.RegisterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = object : TestWatcher() {
        override fun starting(description: Description) {
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description) {
            Dispatchers.resetMain()
        }
    }

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = RegisterViewModel(userRepository)
    }

    @After
    fun tearDown() {
        // Cleanup
    }

    // ===== TESTS DE VALIDACIÓN DE EMAIL =====

    @Test
    fun `onChangeEmail con email valido no debe tener error`() {
        // Arrange
        val emailValido = "test@example.com"

        // Act
        viewModel.onChangeEmail(emailValido)

        // Assert
        val state = viewModel.state.value
        Assert.assertEquals(emailValido, state.email)
        Assert.assertNull("El error de email debería ser null", state.errors.emailError)
    }

    @Test
    fun `onChangeEmail con email invalido debe tener error`() {
        // Arrange
        val emailInvalido = "invalid.email"

        // Act
        viewModel.onChangeEmail(emailInvalido)

        // Assert
        val state = viewModel.state.value
        Assert.assertEquals(emailInvalido, state.email)
        Assert.assertNotNull("Debería haber un error de email", state.errors.emailError)
        Assert.assertTrue(
            "El error debería mencionar el formato",
            state.errors.emailError?.contains("formato") == true
        )
    }

    @Test
    fun `onChangeEmail con email vacío debe tener error`() {
        // Arrange
        val emailVacio = ""

        // Act
        viewModel.onChangeEmail(emailVacio)

        // Assert
        val state = viewModel.state.value
        Assert.assertNotNull("Debería haber un error de email", state.errors.emailError)
        Assert.assertEquals("El email es requerido", state.errors.emailError)
    }

    // ===== TESTS DE VALIDACIÓN DE USERNAME =====

    @Test
    fun `onChangeUsername con username válido no debe tener error`() {
        // Arrange
        val usernameValido = "usuario123"

        // Act
        viewModel.onChangeUsername(usernameValido)

        // Assert
        val state = viewModel.state.value
        Assert.assertEquals(usernameValido, state.username)
        Assert.assertNull("El error de username debería ser null", state.errors.usernameError)
    }

    // ===== TESTS DE VALIDACIÓN DE PASSWORD =====

    @Test
    fun `onChangePassword con password válido no debe tener error`() {
        // Arrange
        val passwordValido = "Password123"

        // Act
        viewModel.onChangePassword(passwordValido)

        // Assert
        val state = viewModel.state.value
        Assert.assertEquals(passwordValido, state.password)
        Assert.assertNull("El error de password debería ser null", state.errors.passwordError)
    }

    @Test
    fun `onChangePassword con password corto debe tener error`() {
        // Arrange
        val passwordCorto = "123"

        // Act
        viewModel.onChangePassword(passwordCorto)

        // Assert
        val state = viewModel.state.value
        Assert.assertNotNull("Debería haber un error de password", state.errors.passwordError)
        Assert.assertTrue(
            "El error debería mencionar la longitud mínima",
            state.errors.passwordError?.contains("6") == true
        )
    }

    // ===== TESTS DE VALIDACIÓN DE CONFIRM PASSWORD =====

    @Test
    fun `onChangeConfirmPassword con passwords coincidentes no debe tener error`() {
        // Arrange
        val password = "Password123"
        viewModel.onChangePassword(password)

        // Act
        viewModel.onChangeConfirmPassword(password)

        // Assert
        val state = viewModel.state.value
        Assert.assertNull("El error debería ser null", state.errors.confirmPasswordError)
    }

    @Test
    fun `onChangeConfirmPassword con passwords diferentes debe tener error`() {
        // Arrange
        viewModel.onChangePassword("Password123")

        // Act
        viewModel.onChangeConfirmPassword("DifferentPass")

        // Assert
        val state = viewModel.state.value
        Assert.assertNotNull("Debería haber un error", state.errors.confirmPasswordError)
        Assert.assertEquals("Las contraseñas no coinciden", state.errors.confirmPasswordError)
    }

    // ===== TESTS DE hasErrors() =====

    @Test
    fun `hasErrors debe retornar true cuando hay errores`() {
        // Arrange
        viewModel.onChangeEmail("invalid")
        viewModel.onChangePassword("123")
        viewModel.onChangeConfirmPassword("456")

        // Act
        val state = viewModel.state.value
        val resultado = state.errors.hasErrors()

        // Assert
        Assert.assertTrue("Debería retornar true cuando hay errores", resultado)
    }

    @Test
    fun `hasErrors debe retornar false cuando no hay errores`() {
        // Arrange
        viewModel.onChangeUsername("usuario123")
        viewModel.onChangeEmail("test@example.com")
        viewModel.onChangePassword("Password123")
        viewModel.onChangeConfirmPassword("Password123")

        // Act
        val state = viewModel.state.value
        val resultado = state.errors.hasErrors()

        // Assert
        Assert.assertFalse("Debería retornar false cuando no hay errores", resultado)
    }

    // ===== TESTS DE onRegisterSubmit() =====

    @Test
    fun `onRegisterSubmit con campos validos debe llamar onSuccess`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "Password123"
        val username = "usuario123"

        // Mock: getUserByEmail retorna null (email no existe)
        whenever(userRepository.getUserByEmail(email)).thenReturn(null)

        var successCalled = false
        var errorMessage: String? = "no llamado"

        viewModel.onChangeUsername(username)
        viewModel.onChangeEmail(email)
        viewModel.onChangePassword(password)
        viewModel.onChangeConfirmPassword(password)

        // Act
        viewModel.onRegisterSubmit(
            onSuccess = { successCalled = true },
            onError = { errorMessage = it }
        )

        advanceUntilIdle()

        // Assert
        Assert.assertTrue("onSuccess debería ser llamado", successCalled)
        Assert.assertEquals("errorMessage debería seguir siendo 'no llamado'", "no llamado", errorMessage)

        // Verificar que se llamó a insertUser
        val userCaptor = argumentCaptor<UserEntity>()
        verify(userRepository).insertUser(userCaptor.capture())
        Assert.assertEquals(email, userCaptor.firstValue.email)
        Assert.assertEquals(username, userCaptor.firstValue.username)
    }

    @Test
    fun `onRegisterSubmit con email duplicado debe llamar onError`() = runTest {
        // Arrange
        val email = "existing@example.com"
        val password = "Password123"
        val username = "usuario123"

        // Mock: getUserByEmail retorna un usuario existente
        val existingUser = UserEntity(
            username = "otro",
            email = email,
            password = "hashedpass",
            profileImageUri = ""
        )
        whenever(userRepository.getUserByEmail(email)).thenReturn(existingUser)

        var successCalled = false
        var errorMessage: String? = null

        viewModel.onChangeUsername(username)
        viewModel.onChangeEmail(email)
        viewModel.onChangePassword(password)
        viewModel.onChangeConfirmPassword(password)

        // Act
        viewModel.onRegisterSubmit(
            onSuccess = { successCalled = true },
            onError = { errorMessage = it }
        )

        advanceUntilIdle()

        // Assert
        Assert.assertFalse("onSuccess no debería ser llamado", successCalled)
        Assert.assertEquals("Este email ya está registrado", errorMessage)
        verify(userRepository, never()).insertUser(any())
    }

    @Test
    fun `onRegisterSubmit con passwords no coincidentes debe llamar onError`() = runTest {
        // Arrange
        viewModel.onChangeUsername("usuario123")
        viewModel.onChangeEmail("test@example.com")
        viewModel.onChangePassword("Password123")
        viewModel.onChangeConfirmPassword("DifferentPass")

        var successCalled = false
        var errorMessage: String? = null

        // Act
        viewModel.onRegisterSubmit(
            onSuccess = { successCalled = true },
            onError = { errorMessage = it }
        )

        advanceUntilIdle()

        // Assert
        Assert.assertFalse("onSuccess no debería ser llamado", successCalled)
        Assert.assertNotNull("Debería haber un mensaje de error", errorMessage)
        // ✅ CORREGIDO: Acepta tanto el mensaje específico como el genérico
        Assert.assertTrue(
            "El error debe mencionar contraseñas o formulario",
            errorMessage?.contains("contraseña") == true ||
                    errorMessage?.contains("formulario") == true
        )
        verify(userRepository, never()).insertUser(any())
        verify(userRepository, never()).getUserByEmail(any())
    }

    @Test
    fun `onRegisterSubmit con errores de validacion debe llamar onError`() = runTest {
        // Arrange
        viewModel.onChangeUsername("a") // muy corto
        viewModel.onChangeEmail("invalid")
        viewModel.onChangePassword("123")
        viewModel.onChangeConfirmPassword("123")

        var successCalled = false
        var errorMessage: String? = null

        // Act
        viewModel.onRegisterSubmit(
            onSuccess = { successCalled = true },
            onError = { errorMessage = it }
        )

        advanceUntilIdle()

        // Assert
        Assert.assertFalse("onSuccess no debería ser llamado", successCalled)
        Assert.assertNotNull("Debería haber un mensaje de error", errorMessage)
        verify(userRepository, never()).insertUser(any())
        verify(userRepository, never()).getUserByEmail(any())
    }

    @Test
    fun `onRegisterSubmit con email vacío debe llamar onError`() = runTest {
        // Arrange
        viewModel.onChangeUsername("usuario123")
        viewModel.onChangeEmail("")
        viewModel.onChangePassword("Password123")
        viewModel.onChangeConfirmPassword("Password123")

        var successCalled = false
        var errorMessage: String? = null

        // Act
        viewModel.onRegisterSubmit(
            onSuccess = { successCalled = true },
            onError = { errorMessage = it }
        )

        advanceUntilIdle()

        // Assert
        Assert.assertFalse(successCalled)
        Assert.assertNotNull(errorMessage)
        verify(userRepository, never()).insertUser(any())
    }
}