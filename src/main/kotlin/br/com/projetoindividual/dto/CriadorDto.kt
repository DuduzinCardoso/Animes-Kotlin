package br.com.projetoindividual.dto

import java.util.*

data class CriadorDto(
    var id: Long? = null,
    var nome: String? = null,
    val nascimento: Date? = null,
)
