package br.com.projetoindividual.dto

import java.util.*

data class EditoraDto(
    var id: Long? = null,
    var nome: String? = null,
    var pais: String? = null,
    val fundada: Date? = null,
)
