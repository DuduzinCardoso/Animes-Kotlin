package br.com.projetoindividual.dto

import java.util.*

data class UpdateCriadorRequestDto(
    val idAnime: UUID,
    val idCriador: UUID,
    val nome: String,
)
