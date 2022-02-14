package br.com.projetoindividual.dto

import java.util.*

data class PersonagemDto(
    var id: UUID,
    var nome: String,
    val genero: String
)
