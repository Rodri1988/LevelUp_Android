package com.example.levelup.utils

fun validateEmail(email: String): String? {
    val emailRegex = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$")

    return if (!emailRegex.matches(email)) {
        "Please enter a valid email"
    } else {
        null
    }
}