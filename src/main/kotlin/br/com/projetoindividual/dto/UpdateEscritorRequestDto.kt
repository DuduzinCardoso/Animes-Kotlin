package br.com.projetoindividual.dto

import java.util.Date
import java.util.UUID

data class UpdateEscritorRequestDto(
    val id: Long,
    val nome: String,
    val nascimento: Date,
    val genero: Char,
)
