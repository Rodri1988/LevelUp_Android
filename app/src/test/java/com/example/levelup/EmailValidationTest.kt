package com.example.levelup

import com.example.levelup.utils.validateEmail
import org.junit.Test
import org.junit.Assert.*

class EmailValidationTest {

    @Test
    fun `email válido retorna null`() {
        // Given
        val validEmails = listOf(
            "test@example.com",
            "user.name@example.co.uk",
            "user+tag@example.com"
        )

        // When & Then
        validEmails.forEach { email ->
            assertNull("Email $email debería ser válido", validateEmail(email))
        }
    }

    @Test
    fun `email inválido retorna mensaje de error`() {
        // Given
        val invalidEmails = listOf(
            "invalid",
            "@example.com",
            "test@",
            "test@.com",
            ""
        )

        // When & Then
        invalidEmails.forEach { email ->
            assertNotNull("Email $email debería ser inválido", validateEmail(email))
        }
    }

    @Test
    fun `email vacío retorna mensaje de error`() {
        // Given
        val emptyEmail = ""

        // When
        val result = validateEmail(emptyEmail)

        // Then
        assertNotNull(result)
    }
}