package com.example.levelup.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.remote.RetrofitFactInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FactViewModel : ViewModel() {

    private val _fact = MutableStateFlow("Cargando dato curioso...")
    val fact: StateFlow<String> = _fact.asStateFlow()

    init {
        Log.d("FactViewModel", "Buscando dato")
        fetchFact()
    }

    fun fetchFact() {
        viewModelScope.launch {
            try {
                Log.d("FactViewModel", "Buscando dato")

                val response = RetrofitFactInstance.api.getRandomFact()


                Log.d("FactViewModel", "✅ Dato recibido: ${response.text}")
                _fact.value = response.text

            } catch (e: Exception) {
                Log.e("FactViewModel", "❌ Error: ${e.message}", e)
                _fact.value = "No se pudo cargar el dato. Intenta de nuevo."
            }
        }
    }
}