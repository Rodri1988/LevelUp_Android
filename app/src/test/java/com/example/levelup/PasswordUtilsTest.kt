package com.example.levelup

import com.example.levelup.utils.PasswordUtils
import org.junit.Test
import org.junit.Assert.*

class PasswordUtilsTest {

    @Test
    fun `hashPassword genera hash diferente al password original`() {
        // Given
        val password = "testPassword123"

        // When
        val hashedPassword = PasswordUtils.hashPassword(password)

        // Then
        assertNotEquals(password, hashedPassword)
        assertTrue(hashedPassword.length > password.length)
    }

    @Test
    fun `hashPassword genera hashes consistentes para el mismo password`() {
        // Given
        val password = "testPassword123"

        // When
        val hash1 = PasswordUtils.hashPassword(password)
        val hash2 = PasswordUtils.hashPassword(password)

        // Then
        assertEquals(hash1, hash2)
    }

    @Test
    fun `hashPassword con diferentes passwords genera diferentes hashes`() {
        // Given
        val password1 = "password123"
        val password2 = "password456"

        // When
        val hash1 = PasswordUtils.hashPassword(password1)
        val hash2 = PasswordUtils.hashPassword(password2)

        // Then
        assertNotEquals(hash1, hash2)
    }

    @Test
    fun `hashPassword no retorna null o vacío`() {
        // Given
        val password = "testPassword"

        // When
        val hashedPassword = PasswordUtils.hashPassword(password)

        // Then
        assertNotNull(hashedPassword)
        assertTrue(hashedPassword.isNotEmpty())
    }
}