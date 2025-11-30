package com.example.levelup.model

import com.google.gson.annotations.SerializedName

data class IndicadoresResponse(
    val version: String?,
    val autor: String?,
    val fecha: String?,
    val uf: IndicadorData?,
    val ivp: IndicadorData?,
    val dolar: IndicadorData?,
    val dolar_intercambio: IndicadorData?,
    val euro: IndicadorData?,
    val ipc: IndicadorData?,
    val utm: IndicadorData?,
    val imacec: IndicadorData?,
    val tpm: IndicadorData?,
    val libra_cobre: IndicadorData?,
    val tasa_desempleo: IndicadorData?,
    val bitcoin: IndicadorData?
)

data class IndicadorData(
    val codigo: String?,
    val nombre: String?,
    val unidad_medida: String?,
    val fecha: String?,
    val valor: Double?
)