package br.com.projetoindividual.dto

import java.util.*

data class EscritorDto(
    var id: Long? = null,
    var nome: String? = null,
    val nascimento: Date? = null,
    val genero: Char? = null,
)
