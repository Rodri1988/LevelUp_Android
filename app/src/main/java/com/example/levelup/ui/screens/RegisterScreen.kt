package com.example.levelup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelup.view_model.RegisterViewModel
import kotlinx.coroutines.launch
import com.example.levelup.R
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 🖼️ Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.imagen3),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // 📋 Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Registro",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileImagePicker(
                imageUri = state.profileImageUri,
                onImageSelected = viewModel::onProfileImageSelected,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // 🎨 Colores personalizados de los campos
            val fieldColors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.6f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.6f),
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                errorContainerColor = Color(0xFFFFCDD2).copy(alpha = 0.6f),
                errorIndicatorColor = Color.Red,
                errorCursorColor = Color.Red
            )

            // ✅ Campo Username con error
            Column {
                OutlinedTextField(
                    value = state.username,
                    onValueChange = { viewModel.onChangeUsername(it) },
                    label = { Text("Nombre de Usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = state.errors.usernameError != null
                )
                if (state.errors.usernameError != null) {
                    Text(
                        text = state.errors.usernameError!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Campo Email con error
            Column {
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.onChangeEmail(it) },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = state.errors.emailError != null
                )
                if (state.errors.emailError != null) {
                    Text(
                        text = state.errors.emailError!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Campo Contraseña con error
            Column {
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { viewModel.onChangePassword(it) },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = state.errors.passwordError != null,
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                                tint = Color.Black
                            )
                        }
                    }
                )
                if (state.errors.passwordError != null) {
                    Text(
                        text = state.errors.passwordError!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Campo Confirmar Contraseña con error
            Column {
                OutlinedTextField(
                    value = state.confirmPassword,
                    onValueChange = { viewModel.onChangeConfirmPassword(it) },
                    label = { Text("Confirmar Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = state.errors.confirmPasswordError != null,
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (confirmPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                                tint = Color.Black
                            )
                        }
                    }
                )
                if (state.errors.confirmPasswordError != null) {
                    Text(
                        text = state.errors.confirmPasswordError!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Botón deshabilitado cuando hay errores
            Button(
                onClick = {
                    viewModel.onRegisterSubmit(
                        onSuccess = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Registro realizado con éxito")
                            }
                        },
                        onError = { error ->
                            scope.launch {
                                snackbarHostState.showSnackbar(error)
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.errors.hasErrors() &&
                        state.username.isNotBlank() &&
                        state.email.isNotBlank() &&
                        state.password.isNotBlank() &&
                        state.confirmPassword.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.8f),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.5f),
                    disabledContentColor = Color.White
                )
            ) {
                Text("Registrarse")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onNavigateBack) {
                    Text("Volver", color = Color.White)
                }

                TextButton(onClick = onNavigateToLogin) {
                    Text("¿Ya tienes cuenta? Inicia sesión", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SnackbarHost(hostState = snackbarHostState)
        }
    }
}