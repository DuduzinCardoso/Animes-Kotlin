package br.com.projetoindividual.dto

import java.util.*

data class UpdateEditoraRequestDto(
    val idManga: UUID,
    val idEditora: UUID,
    val nome: String,
)
