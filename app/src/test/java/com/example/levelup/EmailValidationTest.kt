package com.example.levelup

import com.example.levelup.utils.validateEmail
import org.junit.Assert.*
import org.junit.Test

class EmailValidationTest {

    @Test
    fun `email valido retorna null`() {
        // Arrange & Act & Assert
        val email1 = "test@example.com"
        assertNull("El email '$email1' deberia ser valido", validateEmail(email1))

        val email2 = "user.name@domain.co"
        assertNull("El email '$email2' deberia ser valido", validateEmail(email2))

        val email3 = "user+tag@example.com"
        assertNull("El email '$email3' deberia ser valido", validateEmail(email3))

        val email4 = "nombre123@correo.com"
        assertNull("El email '$email4' deberia ser valido", validateEmail(email4))
    }

    @Test
    fun `email invalido retorna mensaje de error`() {
        // Arrange
        val emailsInvalidos = listOf(
            "invalid.email",
            "@example.com",
            "user@",
            "user name@example.com"
        )

        // Act & Assert
        emailsInvalidos.forEach { email ->
            val resultado = validateEmail(email)
            assertNotNull("El email '$email' deberia ser invalido", resultado)
            assertTrue(
                "El error deberia ser sobre formato",
                resultado?.contains("formato") == true
            )
        }
    }

    @Test
    fun `email vacio retorna mensaje especifico`() {
        // Arrange
        val email = ""

        // Act
        val resultado = validateEmail(email)

        // Assert
        assertEquals("El email es requerido", resultado)
    }

    @Test
    fun `email con espacios es invalido`() {
        // Arrange
        val email = "test @example.com"

        // Act
        val resultado = validateEmail(email)

        // Assert
        assertNotNull("Email con espacios deberia ser invalido", resultado)
    }

    @Test
    fun `email sin arroba es invalido`() {
        // Arrange
        val email = "testexample.com"

        // Act
        val resultado = validateEmail(email)

        // Assert
        assertNotNull("Email sin @ deberia ser invalido", resultado)
    }

    @Test
    fun `email sin dominio es invalido`() {
        // Arrange
        val email = "test@"

        // Act
        val resultado = validateEmail(email)

        // Assert
        assertNotNull("Email sin dominio deberia ser invalido", resultado)
    }
}