package br.com.projetoindividual.dto

import java.util.*

data class CriadorRequestDto(
    val idAnime: Long,
    val nome: String,
    val nascimento: Date,
)
