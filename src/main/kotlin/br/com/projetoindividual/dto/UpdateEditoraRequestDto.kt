package br.com.projetoindividual.dto

import java.util.*

data class UpdateEditoraRequestDto(
    val id: Long,
    val nome: String,
    val pais: String,
    val fundada: Date,
)
