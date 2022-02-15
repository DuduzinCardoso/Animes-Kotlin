package br.com.projetoindividual.dto

import java.util.*

data class EscritorRequestDto(
    val nome: String,
    val nascimento: Date,
    val genero: Char,
)
