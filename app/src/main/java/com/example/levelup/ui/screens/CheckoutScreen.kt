package com.example.levelup.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.levelup.model.CartSummary
import com.example.levelup.view_model.CartViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: CartViewModel,
    onNavigateBack: () -> Unit,
    onOrderConfirmed: () -> Unit
) {
    val cartSummary by viewModel.cartSummary.collectAsState()
    val scope = rememberCoroutineScope()

    var cardNumber by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }

    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finalizar Compra") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Resumen del pedido
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Total a pagar",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        currencyFormat.format(cartSummary.total),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                "Información de pago",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Formulario de pago
            OutlinedTextField(
                value = cardNumber,
                onValueChange = { if (it.length <= 16) cardNumber = it },
                label = { Text("Número de tarjeta") },
                placeholder = { Text("1234 5678 9012 3456") },
                leadingIcon = { Icon(Icons.Default.CreditCard, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = cardHolder,
                onValueChange = { cardHolder = it },
                label = { Text("Titular de la tarjeta") },
                placeholder = { Text("NOMBRE APELLIDO") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { if (it.length <= 5) expiryDate = it },
                    label = { Text("Vencimiento") },
                    placeholder = { Text("MM/AA") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { if (it.length <= 3) cvv = it },
                    label = { Text("CVV") },
                    placeholder = { Text("123") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        isProcessing = true
                        // Simular procesamiento de pago
                        delay(2000)
                        viewModel.clearCart()
                        onOrderConfirmed()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isProcessing && cardNumber.isNotBlank() &&
                        cardHolder.isNotBlank() && expiryDate.isNotBlank() && cvv.isNotBlank()
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Procesando...")
                } else {
                    Icon(Icons.Default.CheckCircle, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Confirmar Pago", style = MaterialTheme.typography.titleMedium)
                }
            }

            Text(
                "🔒 Pago seguro protegido",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}