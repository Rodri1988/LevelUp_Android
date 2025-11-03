package com.example.levelup.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.levelup.R
import com.example.levelup.view_model.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: UserViewModel,
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit
) {
    val profileState by viewModel.profileState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    var isEditingPhoto by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color(0xFF39FF14),
                    navigationIconContentColor = Color(0xFF39FF14)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 🖼️ Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.imagen3),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            when {
                profileState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                profileState.error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            modifier = Modifier.size(64.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = profileState.error ?: "Error desconocido",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadUserProfile() }) {
                            Text("Reintentar")
                        }
                    }
                }

                profileState.user != null -> {
                    val user = profileState.user!!

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start  // Alineado a la izquierda
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))

                        // Foto de perfil con opción de editar - ALINEADA A LA IZQUIERDA
                        if (isEditingPhoto) {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                ProfileImagePicker(
                                    imageUri = user.profileImageUri,
                                    onImageSelected = { newUri ->
                                        viewModel.updateProfileImage(newUri)
                                        isEditingPhoto = false
                                    }
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                TextButton(onClick = { isEditingPhoto = false }) {
                                    Text("Cancelar", color = Color.White)
                                }
                            }
                        } else {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (user.profileImageUri.isNotEmpty()) {
                                        AsyncImage(
                                            model = user.profileImageUri,
                                            contentDescription = "Foto de perfil",
                                            modifier = Modifier
                                                .size(120.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Foto de perfil",
                                            modifier = Modifier.size(80.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                TextButton(onClick = { isEditingPhoto = true }) {
                                    Icon(
                                        imageVector = Icons.Default.PhotoCamera,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Cambiar foto", color = Color.White)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Nombre de usuario
                        Text(
                            text = user.username,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Email
                        Text(
                            text = user.email,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.9f)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Tarjeta de información
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black.copy(alpha = 0.9f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                ProfileInfoRow(
                                    icon = Icons.Default.Badge,
                                    label = "Usuario",
                                    value = user.username,
                                    textColor = Color(0xFF39FF14)
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = Color(0xFF39FF14).copy(alpha = 0.3f)
                                )

                                ProfileInfoRow(
                                    icon = Icons.Default.Email,
                                    label = "Correo",
                                    value = user.email,
                                    textColor = Color(0xFF39FF14)
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = Color(0xFF39FF14).copy(alpha = 0.3f)
                                )

                                ProfileInfoRow(
                                    icon = Icons.Default.Key,
                                    label = "ID de Usuario",
                                    value = "#${user.uid}",
                                    textColor = Color(0xFF39FF14)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón Cerrar Sesión
                        Button(
                            onClick = { showLogoutDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cerrar Sesión")
                        }
                    }
                }
            }
        }
    }

    // Diálogo de confirmación de logout
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null
                )
            },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Estás seguro de que deseas cerrar sesión?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        viewModel.logout(onLogoutSuccess = onLogout)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Cerrar Sesión")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun ProfileInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    textColor: Color = Color(0xFF39FF14)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF39FF14)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = textColor.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}