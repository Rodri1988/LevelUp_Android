package com.example.levelup.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelup.view_model.RegisterViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    viewModel: RegisterViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Registro",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = viewModel::onChangeEmail,
                    supportingText = {
                        if (state.errors.emailError != null) { Text(state.errors.emailError!!) } else { null }
                    },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = viewModel::onChangePassword,
                    supportingText = {
                        if (state.errors.passwordError != null) { Text(state.errors.passwordError!!) } else { null }
                    },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = state.confirmPassword,
                    onValueChange = viewModel::onChangeConfirmPassword,
                    supportingText = {
                        if (state.errors.confirmPasswordError != null) { Text(state.errors.confirmPasswordError!!) } else { null }
                    },
                    label = { Text("Confirmar Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )

                Button(
                    onClick = {
                        viewModel.onRegisterSubmit(
                            onSuccess = {
                                onNavigateToHome()
                            },
                            onError = { errorMessage ->
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = errorMessage,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text("Registrarse")
                }

                TextButton(
                    onClick = onNavigateToLogin
                ) {
                    Text("¿Ya tienes cuenta? Inicia Sesión")
                }
            }
        }
    }
}