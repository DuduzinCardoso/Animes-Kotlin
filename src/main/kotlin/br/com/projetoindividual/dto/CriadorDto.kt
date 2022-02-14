package br.com.projetoindividual.dto

import java.util.*

data class CriadorDto(
    var id: UUID,
    var nome: String,
    val nascimento: Date
)
