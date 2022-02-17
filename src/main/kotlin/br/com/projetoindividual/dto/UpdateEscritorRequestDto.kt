package br.com.projetoindividual.dto

import java.util.UUID

data class UpdateEscritorRequestDto(
    val idManga: UUID,
    val idEscritor: UUID,
    val nome: String,
)
