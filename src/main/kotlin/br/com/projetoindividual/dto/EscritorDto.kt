package br.com.projetoindividual.dto

import java.util.*

data class EscritorDto(
    var id: UUID,
    var nome: String,
    val nascimento: Date,
    val genero: Char,
)
