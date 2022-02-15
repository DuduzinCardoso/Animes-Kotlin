package br.com.projetoindividual.dto

import java.util.*

data class EditoraDto(
    var id: UUID,
    var nome: String,
    var pais: String,
    val fundada: Date,
)
