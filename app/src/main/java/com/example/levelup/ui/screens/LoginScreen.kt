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
import com.example.levelup.view_model.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen (
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    viewModel: LoginViewModel = viewModel(),
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
                    text = "Iniciar Sesión",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 32.dp),
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = viewModel::onChangeEmail,
                    supportingText = {
                        if (state.errors.emailError != null) { Text(text = state.errors.emailError!!) } else { Text( text = "" ) }
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
                        if (state.errors.passwordError != null) { Text(text = state.errors.passwordError!!) } else { Text( text = "" ) }
                    },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )

                Button(
                    onClick = {
                        viewModel.onLoginSubmit(
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
                    Text("Iniciar Sesión")
                }
                TextButton(
                    onClick = onNavigateToRegister
                ) {
                    Text("¿No tienes cuenta? Regístrate")
                }
            }
        }
    }
}